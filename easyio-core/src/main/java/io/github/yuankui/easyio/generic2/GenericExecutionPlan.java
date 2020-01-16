package io.github.yuankui.easyio.generic2;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.github.yuankui.easyio.context.IOContext;
import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.core.ProviderContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GenericExecutionPlan implements ExecutionPlan {
    private Callable callable;
    

    @Override
    public void init(Method method, ProviderContext providerContext) {

        List<Provider> providerList = providerContext.getAllProviders();
        // 按照resource分组，并且排好优先级，优先级低的，在前面
        Map<String, PriorityQueue<Provider>> resourceProvidersMap = providerList.stream()
                .collect(Collectors.groupingBy(
                        p -> p.resourceName(),
                        Collectors.toCollection(() -> {
                            return new PriorityQueue<>((o1, o2) -> {
                                return o1.compare(o2).getLevel() - o2.compare(o1).getLevel();
                            });
                        })));


        Multimap<String, Resource> initializedResources = ArrayListMultimap.create();
        Map<Provider, Result> initResult = new LinkedHashMap<>();

        // 循环provider次，就算每次只选出一个，是驴是马，也该得出结论了把？
        for (int i = 0; i < providerList.size(); i++) {
            // 最后一次机会
            final boolean lastChance = i == providerList.size() - 1;
            for (Map.Entry<String, PriorityQueue<Provider>> entry : resourceProvidersMap.entrySet()) {
                String resourceName = entry.getKey();
                PriorityQueue<Provider> list = entry.getValue();

                // 初始化，并且删除成功初始化的
                list.removeIf(provider -> {
                    // 针对一个资源的初始化
                    Result result = provider.init(method, new InitContext() {
                        @Override
                        public <T> void addDependency(Resource<T> provider) {
                            // TODO 记录以来链条
                        }

                        @Override
                        public boolean lastChance() {
                            return lastChance;
                        }

                        @Override
                        public Collection<Resource> getResources(String name) {
                            return initializedResources.get(name);
                        }
                    });

                    if (result.isSuccess()) {
                        initializedResources.put(resourceName, Resource.of(provider, result.getCallable()));
                    }

                    initResult.put(provider, result);
                    return result.isSuccess();
                });
            }
        }
        
        // 好了，都初始化完了，那我们我们现在需要

        Optional<Resource> result = initializedResources.get("result")
                .stream()
                .reduce((o1, o2) -> o2);

        if (result.isPresent()) {
            this.callable = result.get().getCallable();
        } else {
            log.info("not result provider found: {}", initResult);
            System.err.println("initResult:");
            initResult.forEach((provider, result1) -> {
                System.out.println(provider.getClass().getName() + "=>" + result1);
            });
            throw new RuntimeException("no valid callable:" + initResult);
        }
    }

    @Override
    public Object execute(IOContext IOContext) throws IOException {
        return callable.call(IOContext);
    }
}
