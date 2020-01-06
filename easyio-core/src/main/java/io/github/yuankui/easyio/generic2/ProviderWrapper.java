package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ProviderWrapper<T> implements Provider<T>{
    private Provider<T> provider;
    private Type type;
    private Exception e;

    public ProviderWrapper(Provider<T> provider) {
        this.provider = provider;
    }

    @Override
    public void init(Method method, InitContext context) throws DependencyMissingException {
        this.provider.init(method, context);
    }

    @Override
    public T provide(ProcessContext context) {
        return this.provider.provide(context);
    }

    @Override
    public String name() {
        return provider.name();
    }

    public Type type() {
        if (this.type == null) {
            try {
                Method method = this.provider.getClass().getMethod("provide", ProcessContext.class);
                this.type = method.getGenericReturnType();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        
        return type;
    }
    
    public void setException(Exception e) {
        this.e = e;
    }
}
