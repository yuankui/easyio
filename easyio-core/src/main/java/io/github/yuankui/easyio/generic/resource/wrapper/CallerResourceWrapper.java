package io.github.yuankui.easyio.generic.resource.wrapper;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.Prototype;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Prototype
public class CallerResourceWrapper implements ResourceWrapper {
    private Type type;
    private Class wrapperType;
    private Type innerType;
    
    @Override
    public boolean init(Type type) {
        this.type = type;
        if (type instanceof ParameterizedType) {
            if (((ParameterizedType) type).getRawType() == Caller.class) {
                this.wrapperType = Caller.class;
                this.innerType = ((ParameterizedType) type).getActualTypeArguments()[0];
                return true;
            }
        }
        return false;
    }

    @Override
    public Type getInnerType() {
        return innerType;
    }

    @Override
    public Class getWrapperType() {
        return wrapperType;
    }

    @Override
    public Caller wrap(Object obj) {
        return (Caller) obj;
    }
}
