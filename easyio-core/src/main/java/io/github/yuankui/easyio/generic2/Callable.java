package io.github.yuankui.easyio.generic2;

import io.github.yuankui.easyio.context.IOContext;

public interface Callable<T> {
    T call(IOContext ioContext);
}
