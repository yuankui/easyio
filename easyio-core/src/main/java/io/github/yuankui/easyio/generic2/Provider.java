package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;

public interface Provider<T> {
    void init(Method method, InitContext context) throws DependencyMissingException;
    
    T provide(ProcessContext context);
    
    String name();
    
    default double order() {
        return 0;
    }
}
