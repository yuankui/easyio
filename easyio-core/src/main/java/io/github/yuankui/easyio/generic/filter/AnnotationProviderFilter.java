package io.github.yuankui.easyio.generic.filter;

import java.lang.annotation.Annotation;

public class AnnotationProviderFilter implements ProviderFilter {
    private Class<? extends Annotation> annotationClass;

    public AnnotationProviderFilter(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public boolean filter(Class clazz) {
        Annotation annotation = clazz.getAnnotation(this.annotationClass);
        return annotation != null;
    }
}
