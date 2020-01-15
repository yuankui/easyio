package io.github.yuankui.easyio.core;

import java.lang.reflect.Proxy;

public class EasyIO {
    
    public static  <T> T create(Class<T> clazz, Runner runner) {
        EasyIOInvocationHandler invocationHandler = new EasyIOInvocationHandler();
        invocationHandler.init(clazz, runner);
        // 创建代理类
        return (T) Proxy.newProxyInstance(EasyIO.class.getClassLoader(),
                new Class[]{clazz},
                invocationHandler);
    }
}
