package io.github.yuankui.easyio.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodAdapterImpl implements MethodAdapter {
    private Method method;

    public MethodAdapterImpl(Method method) {
        this.method = method;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Getter
    @AllArgsConstructor
    static class ParamImpl implements Param {
        private int index;
        private Type returnType;
        private List<Annotation> annotations;

        @Override
        public <T> List<T> getAnnotations(Class<T> annotationClass) {
            return this.annotations.
                    stream()
                    .filter(annotation -> annotationClass.isAssignableFrom(annotation.getClass()))
                    .map(annotation -> (T) annotation)
                    .collect(Collectors.toList());
        }
    }
    
    @Override
    public List<Param> params() {
        List<Param> params = new ArrayList<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            Type returnType = method.getGenericParameterTypes()[i];
            Annotation[] annotations = method.getParameterAnnotations()[i];

            List<Annotation> annotationList = Arrays.stream(annotations).collect(Collectors.toList());
            Param param = new ParamImpl(i, returnType, annotationList);
            params.add(param);
        }
        return params;
    }
    
}
