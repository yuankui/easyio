package io.github.yuankui.easyio.context;

import java.lang.reflect.Method;

public class IOContextImpl implements IOContext {

    private Method method;
    private Object[] args;

    public IOContextImpl(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }
}
