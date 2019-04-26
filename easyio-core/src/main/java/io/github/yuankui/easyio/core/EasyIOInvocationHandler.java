package io.github.yuankui.easyio.core;


import io.github.yuankui.easyio.context.IOContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Component
public class EasyIOInvocationHandler implements InvocationHandler {

    private Map<Method, ExecutionPlan> executionPlanCache = new HashMap<>();

    private Map<Class, Runner> executorFactoryMap;
    @Autowired
    private List<Runner> runners;

    @PostConstruct
    public void init() {
        this.executorFactoryMap = runners
                .stream()
                .collect(Collectors.toMap(Runner::getClass, x -> x));
    }

    public void register(Class<?> clazz) {
        final RunWith runWith = clazz.getAnnotation(RunWith.class);
        final Class executorClass = runWith.value();
        final Runner runner = executorFactoryMap.get(executorClass);

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
