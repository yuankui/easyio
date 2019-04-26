package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.context.IOContext;

import java.io.IOException;

/**
 * 数据可以转换
 * 
 * T -> Supplier<T> -> Caller<T>
 * @param <T>
 */
public interface Caller<T> {
    T call(IOContext ioContext) throws IOException;
}
