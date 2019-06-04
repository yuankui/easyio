package io.github.yuankui.easyio.generic.manager;

import com.google.common.collect.ArrayListMultimap;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.FatalException;
import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.ProviderWrapper;
import io.github.yuankui.easyio.generic.resource.ResourceProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * method一个实例
 */
@Slf4j
@Prototype
public class ResourceManagerImpl implements ResourceManager {

    private Map<String, List<ProviderWrapper>> resourceProvidersMap;
    
    private ArrayListMultimap<String, ResourceProvider> readyMap = ArrayListMultimap.create();
    private Set<String> readySet = new HashSet<>();
    private Set<String> doingSet = new HashSet<>();
    
    // 记录从根资源开始，正在创建的资源
    private Deque<String> stack = new ArrayDeque<>();

    public void init(List<ProviderWrapper> providers) {
        
        // 1. group providers
        Map<String, List<ProviderWrapper>> resourceProvidersMap = providers.stream()
                .collect(Collectors.groupingBy(
                        ProviderWrapper::getResourceName,
                        Collectors.toList()
                ));

        // 2. sort providers
        resourceProvidersMap.forEach((k, v) -> v.sort(ProviderWrapper::compareTo));

        this.resourceProvidersMap = resourceProvidersMap;
    }

    @Override
    public List<ResourceProvider> getResources(String name) {
        List<ResourceProvider> resources = getInnerResources(name);
        return resources.stream()
                .map(r -> new ResourceProvider(
                        r.getName(),
                        r.getProvider(),
                        r.getStatus(),
                        r.getMsg(),
                        false, // default not selected
                        r.getCaller(),
                        r.getDependencies()
                ))
                .collect(Collectors.toList());
    }
    
    public List<ResourceProvider> getInnerResources(String name) {
        // 如果对应的资源已经初始化过，那么就直接返回
        if (readySet.contains(name)) {
            return readyMap.get(name);
        }
        
        // 如果当前申请的资源，刚刚等于正在初始化的资源，那么就返回已经初始化的
        // 可以依赖自己，根据优先级依次初始化，高优先级依赖低优先级
        if (Objects.equals(stack.peek(), name)) {
            return readyMap.get(name);
        }

        // 资源已经在创建，目前又在申请该资源
        if (doingSet.contains(name)) {
            throw new RuntimeException("circular dependency, stack:" + stack + ", current:" + name);
        }
        
        // 否则就初始化
        doingSet.add(name);
        stack.push(name);
        
        try {
            List<ProviderWrapper> providers = resourceProvidersMap.get(name);
            for (ProviderWrapper provider : providers) {
                try {
                    ResourceProvider ret = provider.provide();
                    readyMap.put(name, ret);
                } catch (Exception e) {
                    if (e instanceof FatalException) {
                        throw (FatalException) e;
                    }
                    log.info("invoke provider method error:" + provider, e);
                }
            }
        } finally {
            doingSet.remove(name);
            stack.pop();
            readySet.add(name);
        }
        
        return readyMap.get(name);
    }
}
