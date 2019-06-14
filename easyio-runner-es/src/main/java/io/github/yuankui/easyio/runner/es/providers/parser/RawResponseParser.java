package io.github.yuankui.easyio.runner.es.providers.parser;

import io.github.yuankui.easyio.generic.Prototype;
import org.elasticsearch.action.search.SearchResponse;

import java.lang.reflect.Type;

@Prototype
public class RawResponseParser implements Parser {
    @Override
    public void init(Type srcType, Type targetType) {
        if (srcType != SearchResponse.class) {
            throw new RuntimeException("srcType type not SearchResponse");
        }

        if (targetType != SearchResponse.class) {
            throw new RuntimeException("target type not SearchResponse");
        }
    }

    @Override
    public Object parse(Object response) {
        return response;
    }
}
