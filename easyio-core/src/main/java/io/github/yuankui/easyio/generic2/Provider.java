package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;

public interface Provider<T> {
    void init(Method method, InitContext context);
    
    T provide(ProcessContext context);
}
