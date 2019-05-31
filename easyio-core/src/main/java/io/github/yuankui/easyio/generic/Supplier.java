package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.context.IOContext;

/**
 * Supplier is also a resource, which emits the ioContext and directly calls to {@link Supplier#get()}
 * 
 * @param <T>
 */
public interface Supplier<T> extends Caller<T> {
    static <R> Supplier<R> of(R obj) {
        return () -> obj;
    }
    
    default T call(IOContext ioContext) {
        return this.get();
    } 
    
    T get();
}
