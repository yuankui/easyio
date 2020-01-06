package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;

public class ProviderWrapper<T> implements Provider<T>{
    private Provider<T> provider;

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
}
