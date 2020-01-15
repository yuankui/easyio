package io.github.yuankui.easyio.spring;

import io.github.yuankui.easyio.core.Runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RunWith {

    Class<? extends Runner> value();
}
