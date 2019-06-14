package io.github.yuankui.easyio.runner.es.providers.parser;

import com.alibaba.fastjson.JSON;
import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.runner.es.resource.Result;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;

@Prototype
public class ResultParser implements Parser {
    private Type argType;

    @Override
    public void init(Type srcType, Type targetType) throws Exception {
        if (srcType != SearchResponse.class) {
            throw new RuntimeException("srcType not SearchResponse");
        }
        if (!(targetType instanceof ParameterizedType)) {
            throw new RuntimeException("return type not generic");
        }

        if (((ParameterizedType) targetType).getRawType() != Result.class) {
            throw new RuntimeException("return type not Result<T>");
        }

        this.argType = ((ParameterizedType) targetType).getActualTypeArguments()[0];
    }

    @Override
    public Object parse(Object arg) {
        SearchResponse response = (SearchResponse) arg;
        AbstractList<Object> resultList = new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                SearchHit hit = response.getHits().getAt(index);
                String json = hit.getSourceAsString();
                return JSON.parseObject(json, argType);
            }

            @Override
            public int size() {
                return response.getHits().getHits().length;
            }
        };
        return (Result) () -> resultList;
    }
}
