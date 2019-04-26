package io.github.yuankui.easyio.generic.resource.extractor;

import java.lang.reflect.Type;

public interface ResourceExtractor {
    boolean init(Type type);
    Type getInnerType();

    /**
     * Wrapper || Caller
     * @return
     */
    Class getWrapperType();
    
    Object extract(Object obj);
}
