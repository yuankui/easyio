package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.generic.filter.ProviderFilter;
import io.github.yuankui.easyio.generic.manager.ResourceManagerImpl;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.context.IOContext;
import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.generic.resource.DependencyPrinter;
import io.github.yuankui.easyio.generic.resource.ResourceProvider;
import io.github.yuankui.easyio.generic.resource.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Prototype
@Slf4j
public class GenericExecutionPlan implements ExecutionPlan {

    private ProviderFilter providerFilter;

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private ObjectFactory<ProviderWrapper> wrapperFactory;
    
    @Autowired
    private ResourceManagerImpl resourceManager;
    private Caller caller;

    @Override
    public void init(Method method) {
        MethodAdapter methodAdapter = new MethodAdapterImpl(method);
        
        List<ProviderWrapper> providers = context.getBeansOfType(Provider.class)
                .values()
                .stream()
                .map(Provider::getClass)
                .filter(providerFilter::filter)
                .map(clazz -> context.getBean(clazz))
                .map(obj -> wrapperFactory.getObject().init(obj, methodAdapter, resourceManager))
                .collect(Collectors.toList());
        
        resourceManager.init(providers);

        List<ResourceProvider> resourceProviders = resourceManager.getResources("result");

        if (CollectionUtils.isEmpty(resourceProviders)) {
            throw new RuntimeException("no provider provides <result> created for method:" + method);
        }

        Optional<ResourceProvider> optional = resourceProviders.stream()
                .filter(r -> r.getStatus() == Status.OK)
                .reduce((o1, o2) -> o2);
        
        // no valid result provide found
        if (!optional.isPresent()) {
            String msg = "no provider provides <result> created for method:" + method;
            log.error(msg);
            for (ResourceProvider provider : resourceProviders) {
                DependencyPrinter.print(provider, log::error);
                DependencyPrinter.print(provider, System.out::println);
            }
            throw new RuntimeException(msg);
        }
        
        // print the plan of the provider
        ResourceProvider provider = optional.get();
        provider.setSelected(true);
        log.info("execution plan for method: {}", method);
        log.info("execution plan for method: {}", method);
        DependencyPrinter.print(provider, log::info);
        DependencyPrinter.print(provider, System.out::println);
        this.caller = provider.getCaller();
    }


    @Override
    public Object execute(IOContext IOContext) throws IOException {
        return caller.call(IOContext);
    }

    public void setProviderFilter(ProviderFilter providerFilter) {
        this.providerFilter = providerFilter;
    }
}
