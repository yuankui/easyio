package io.github.yuankui.easyio.generic.resource.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class WrapperFactory {
    @Autowired
    private ApplicationContext context;

    private static Class<? extends ResourceWrapper>[] extractorClasses = new Class[]{
            CallerResourceWrapper.class,
            SupplierResourceWrapper.class,
            RawResourceWrapper.class
    };
    
    public ResourceWrapper create(Type type) {
        for (Class<? extends ResourceWrapper> extractorClass : extractorClasses) {
            ResourceWrapper extractor = context.getBean(extractorClass);
            boolean success = extractor.init(type);
            if (success) {
                return extractor;
            }
        }
        
        throw new RuntimeException("cannot create wrapper of type:" + type);
    }
}
