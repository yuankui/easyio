package io.github.yuankui.easyio.runner.mq.annotation;

import io.github.yuankui.easyio.runner.mq.header.HeaderSupplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface HeaderSupply {
    Class<? extends HeaderSupplier> value();
}
