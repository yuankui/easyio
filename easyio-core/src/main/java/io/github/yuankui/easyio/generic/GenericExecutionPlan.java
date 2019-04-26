package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.generic.filter.ProviderFilter;
import io.github.yuankui.easyio.generic.manager.ResourceManagerImpl;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.context.IOContext;
import io.github.yuankui.easyio.core.ExecutionPlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
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

        List<Caller> callers = resourceManager.getResources("result");

        if (CollectionUtils.isEmpty(callers)) {
            throw new RuntimeException("no provider provides <result> created for method:" + method);
        }

        this.caller = callers.get(callers.size() - 1);
    }


    @Override
    public Object execute(IOContext IOContext) throws IOException {
        return caller.call(IOContext);
    }

    public void setProviderFilter(ProviderFilter providerFilter) {
        this.providerFilter = providerFilter;
    }
}
