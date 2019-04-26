package io.github.yuankui.easyio.runner.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD})
public @interface Query {

    /**
     * 两种用法
     *  1. 用在静态query中，需要注解于方法或者类上面，格式为 value = ["k1: v1", "k2: v2"]
     *  2. 用在动态query中，需要注解于参数上面，value = "key"，query值会动态从参数中获取填充
     *  
     */
    String[] value();
}
