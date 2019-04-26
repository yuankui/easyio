package io.github.yuankui.easyio.generic.resource.wrapper;

import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.Supplier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Prototype
public class SupplierResourceWrapper implements ResourceWrapper {
    private Type type;
    private Class wrapperType;
    private Type innerType;
    
    @Override
    public boolean init(Type type) {
        this.type = type;
        if (type instanceof ParameterizedType) {
            if (((ParameterizedType) type).getRawType() == Supplier.class) {
                this.wrapperType = Supplier.class;
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
    public Supplier wrap(Object obj) {
        return (Supplier) obj;
    }
}
