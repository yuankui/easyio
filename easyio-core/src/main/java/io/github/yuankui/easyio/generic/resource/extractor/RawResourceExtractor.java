package io.github.yuankui.easyio.generic.resource.extractor;

import io.github.yuankui.easyio.generic.FatalException;
import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.Supplier;

import java.lang.reflect.Type;

@Prototype
public class RawResourceExtractor implements ResourceExtractor {
    private Type type;
    private Class wrapperType;
    private Type innerType;
    
    @Override
    public boolean init(Type type) {
        this.type = type;
        this.wrapperType = Supplier.class;
        this.innerType = type;
        return true;
    }
    
    /**
     * 所有对象都是caller，有些特殊的是supplier
     * @param obj
     * @return
     */
    @Override
    public Object extract(Object obj) {
        if (obj instanceof Supplier) {
            return ((Supplier) obj).get();
        }
        throw new FatalException("expected supplier, got:" + obj.getClass());
    }
}
