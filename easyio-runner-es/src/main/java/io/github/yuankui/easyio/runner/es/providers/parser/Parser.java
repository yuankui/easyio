package io.github.yuankui.easyio.runner.es.providers.parser;

import java.lang.reflect.Type;

public interface Parser {
    void init(Type srcType, Type targetType) throws Exception;
    
    Object parse(Object parse);
}
