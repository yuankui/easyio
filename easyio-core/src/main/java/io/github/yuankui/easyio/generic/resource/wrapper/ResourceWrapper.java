package io.github.yuankui.easyio.generic.resource.wrapper;

import io.github.yuankui.easyio.generic.Caller;

import java.lang.reflect.Type;

public interface ResourceWrapper {
    boolean init(Type type);
    Type getInnerType();

    /**
     * Wrapper || Caller
     * @return
     */
    Class getWrapperType();
    
    Caller wrap(Object obj);
}
