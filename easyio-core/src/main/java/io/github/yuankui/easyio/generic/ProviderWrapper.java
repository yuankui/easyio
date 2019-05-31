package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.generic.manager.ResourceManager;
import io.github.yuankui.easyio.generic.param.ParamInjector;
import io.github.yuankui.easyio.generic.param.ParamInjectorFactory;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.generic.resource.wrapper.ResourceWrapper;
import io.github.yuankui.easyio.generic.resource.wrapper.WrapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Prototype
public class ProviderWrapper {
    @Autowired
    private ParamInjectorFactory paramInjectorFactory;
    @Autowired
    private WrapperFactory wrapperFactory;
    private Provider provider;
    private ResourceManager resourceManager;
    private MethodAdapter methodAdapter;

    // calculated
    private String resourceName;
    private Class<? extends Provider> providerClass;
    private Method providerMethod;

    public ProviderWrapper init(Provider provider, MethodAdapter methodAdapter, ResourceManager resourceManager) {
        this.provider = provider;
        this.resourceManager = resourceManager;
        this.methodAdapter = methodAdapter;
        this.providerClass = provider.getClass();

        this.provider.init(methodAdapter);
        
        // 1. parseProvideMethod();
        parseProvideMethod();

        return this;
    }

    private void parseProvideMethod() {
        List<Method> providerMethods = Arrays.stream(this.providerClass.getDeclaredMethods())
                .filter(m -> m.getAnnotation(Provide.class) != null)
                .collect(Collectors.toList());

        if (providerMethods.size() != 1) {
            throw new RuntimeException("only one provider method is allowed");
        }
        this.providerMethod = providerMethods.get(0);
        Provide provide = this.providerMethod.getAnnotation(Provide.class);
        this.resourceName = provide.value();
    }

    public String getResourceName() {
        return resourceName;
    }
    
    public int compareTo(ProviderWrapper that) {
        return this.provider.race(that.provider) - that.provider.race(this.provider);
    }
    
    public Caller provide() {
        Object[] args = IntStream.range(0, this.providerMethod.getParameterCount())
                .mapToObj(i -> {
                    Optional<Depend> dependOptional = Arrays.stream(this.providerMethod.getParameterAnnotations()[i])
                            .filter(annotation -> annotation instanceof Depend)
                            .map(annotation -> (Depend) annotation)
                            .findFirst();
                    if (!dependOptional.isPresent()) {
                        throw new RuntimeException("method args without @Depend:" + this.providerMethod);
                    }

                    Depend depend = dependOptional.get();
                    Type type = this.providerMethod.getGenericParameterTypes()[i];
                    ParamInjector injector = paramInjectorFactory.create(depend, type);
                    return injector.parseParam(resourceManager);
                })
                .toArray();
        
        this.providerMethod.setAccessible(true);
        try {
            Object ret = this.providerMethod.invoke(this.provider, args);
            ResourceWrapper resourceWrapper = wrapperFactory.create(this.providerMethod.getGenericReturnType());
            return resourceWrapper.wrap(ret);
        } catch (InvocationTargetException e) {
            Throwable target = e.getTargetException();
            if (target instanceof RuntimeException) {
                throw (RuntimeException) target;
            } else {
                throw new RuntimeException("invoke method error", target);
            }
        } catch (IllegalAccessException e) {
            throw new FatalException("invoke method error", e);
        }
    }

    @Override
    public String toString() {
        return "ProviderWrapper{" +
                "providerMethod=" + providerMethod +
                '}';
    }
}
