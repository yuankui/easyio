package io.github.yuankui.easyio.context;

import java.lang.reflect.Method;

/**
 * RPC调用的上下文，是大框架级的上下文信息
 */
public interface IOContext {
    Method getMethod();

    Object[] getArgs();
}
