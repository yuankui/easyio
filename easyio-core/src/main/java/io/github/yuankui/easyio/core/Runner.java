package io.github.yuankui.easyio.core;

import java.lang.reflect.Method;

/**
 * 策略模式
 * <p>
 * 必须是单例
 */
public interface Runner {

    ExecutionPlan create(Method method);
}
