package io.github.yuankui.easyio.core;


import io.github.yuankui.easyio.context.IOContextImpl;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EasyIOInvocationHandler implements InvocationHandler {

    private Map<Method, ExecutionPlan> executionPlanCache = new HashMap<>();

    public void init(Class<?> clazz, Runner runner) {
        for (Method method : clazz.getMethods()) {
            try {
                ExecutionPlan executionPlan = runner.create(method);
                executionPlanCache.put(method, executionPlan);
            } catch (RuntimeException e) {
                throw new RuntimeException("create ExecutionPlan for method failed: " + method, e);
            }
        }
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IOException {
        if (method.getName().equals("toString")) {
            return "instance of " + proxy.getClass().getName();
        }
        ExecutionPlan executionPlan = executionPlanCache.get(method);
        IOContextImpl IOContext = new IOContextImpl(method, args);
        return executionPlan.execute(IOContext);
    }
}
