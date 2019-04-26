package io.github.yuankui.easyio.generic.resource.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ExtractorFactory {
    @Autowired
    private ApplicationContext context;

    private static Class<? extends ResourceExtractor>[] extractorClasses = new Class[]{
            CallerResourceExtractor.class,
            SupplierResourceExtractor.class,
            RawResourceExtractor.class
    };
    
    public ResourceExtractor create(Type type) {
        for (Class<? extends ResourceExtractor> extractorClass : extractorClasses) {
            ResourceExtractor extractor = context.getBean(extractorClass);
            boolean success = extractor.init(type);
            if (success) {
                return extractor;
            }
        }
        
        throw new RuntimeException("cannot create extractor of type:" + type);
    }
}
