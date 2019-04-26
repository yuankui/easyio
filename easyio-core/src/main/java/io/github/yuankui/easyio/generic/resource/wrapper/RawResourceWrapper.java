package io.github.yuankui.easyio.generic.resource.wrapper;

import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.Supplier;

import java.lang.reflect.Type;

@Prototype
public class RawResourceWrapper implements ResourceWrapper {
    private Type type;
    private Class wrapperType;
    private Type innerType;
    
    @Override
    public boolean init(Type type) {
        this.type = type;
        this.innerType = type;
        this.wrapperType = Supplier.class;
        return true;
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
    public Supplier wrap(Object obj) {
        return Supplier.of(obj);
    }
}
