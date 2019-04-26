package io.github.yuankui.easyio.generic.provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Depend {
    String value();

    boolean many() default false;

    int least() default 0;

    boolean must() default true;
}
