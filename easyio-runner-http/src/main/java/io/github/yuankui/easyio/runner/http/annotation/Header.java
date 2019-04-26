package io.github.yuankui.easyio.runner.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface Header {

    /**
     * 1. 当作用于class和method时，是k:v结构 -> Cookie: AAAABBBBCCCCDDDD
     * TODO 2. 当作用于method参数时，是k结构，value由参数具体值构成
     *
     * @return
     */
    String[] value();
}
