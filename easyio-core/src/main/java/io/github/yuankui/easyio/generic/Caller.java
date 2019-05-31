package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.context.IOContext;

import java.io.IOException;

/**
 *
 * 
 * This is the resource. But the resource is not concrete objects but callable's, with which you
 * can build real resource with ioContext.
 *
 * the resources can be transform:
 * 
 * T -> Supplier<T> -> Caller<T>
 * @param <T>
 */
public interface Caller<T> {
    T call(IOContext ioContext) throws IOException;
}
