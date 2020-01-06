package io.github.yuankui.easyio.generic2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class TypeHint<T> {
    public Type getType() {
        Class<?> clazz = this.getClass();
        
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types.length != 1) {
                throw new RuntimeException("invalid param nums:" + Arrays.asList(types) + ", class:" + clazz);
            }
            return types[0];
        }

        throw new RuntimeException("no param types in class:" + clazz);
    }
}
