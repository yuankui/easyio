package io.github.yuankui.easyio.generic2;

import io.github.yuankui.easyio.context.IOContext;
import lombok.Getter;

@Getter
public class Resource<T> implements Callable<T> {

    private Provider<T> provider;
    private Callable<T> callable;

    private Resource(Provider<T> provider, Callable<T> callable) {
        this.provider = provider;
        this.callable = callable;
    }

    @Override
    public T call(IOContext ioContext) {
        return callable.call(ioContext);
    }

    public static <T> Resource<T> of(Provider<T> provider, Callable<T> callable) {
        return new Resource<>(provider, callable);
    }
}
