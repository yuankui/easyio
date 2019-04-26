package io.github.yuankui.easyio.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
public class EasyIO {

    @Autowired
    private EasyIOInvocationHandler invocationHandler;

    public <T> T create(Class<T> clazz) {
        invocationHandler.register(clazz);
        // 创建代理类
        return (T) Proxy.newProxyInstance(EasyIO.class.getClassLoader(),
                new Class[]{clazz},
                invocationHandler);
    }
}
