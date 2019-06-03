package io.github.yuankui.easyio.generic.resource.extractor;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.Prototype;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Prototype
public class CallerResourceExtractor implements ResourceExtractor {
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
    
    /**
     * 所有对象都是caller，有些特殊的是supplier
     * @param obj
     * @return
     */
    @Override
    public Object extract(Object obj) {
        return obj;
    }
}
