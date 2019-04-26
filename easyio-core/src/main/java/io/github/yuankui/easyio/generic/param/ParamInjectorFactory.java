package io.github.yuankui.easyio.generic.param;

import io.github.yuankui.easyio.generic.provider.Depend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ParamInjectorFactory {
    private static Class<? extends ParamInjector>[] classes = new Class[] {
            DependManyParamInjector.class,
            DependOneParamInjector.class
    };

    @Autowired
    private ApplicationContext context;
    
    public ParamInjector create(Depend depend, Type type) {
        for (Class<? extends ParamInjector> clazz : classes) {
            ParamInjector paramInjector = context.getBean(clazz);
            if (paramInjector.init(depend, type)) {
                return paramInjector;
            }
        }

        throw new RuntimeException("no param injector build for depend:" + depend + ", type:" + type);
    }
}
