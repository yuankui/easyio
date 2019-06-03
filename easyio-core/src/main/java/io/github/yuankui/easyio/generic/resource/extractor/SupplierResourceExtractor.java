package io.github.yuankui.easyio.generic.resource.extractor;

import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.Supplier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Prototype
public class SupplierResourceExtractor implements ResourceExtractor {
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
    /**
     * 所有对象都是caller，有些特殊的是supplier
     * @param obj
     * @return
     */
    @Override
    public Object extract(Object obj) {
        if (obj instanceof Supplier) {
            return obj;
        }
        throw new RuntimeException("expected supplier, got:" + obj.getClass());
    }
}
