package io.github.yuankui.easyio.generic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public interface MethodAdapter {
    interface Param {
        Type getReturnType();
        List<Annotation> getAnnotations();
        <T> List<T> getAnnotations(Class<T> annotationClass);
        int getIndex();
    }

    Method getMethod();

    /**
     * 遍历annotation
     */
    List<Param> params();
    
    <T extends Annotation> T getAnnotation(Class<T> clazz);
}
