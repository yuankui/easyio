package io.github.yuankui.easyio.core;


import io.github.yuankui.easyio.context.IOContext;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 执行计划封装了一个Method的行为
 */
public interface ExecutionPlan {

    void init(Method method, ProviderContext providerContext);

    Object execute(IOContext IOContext) throws IOException;
}
