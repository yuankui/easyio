package io.github.yuankui.easyio.runner.es.providers;


import com.alibaba.fastjson.JSON;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import io.github.yuankui.easyio.runner.es.resource.Result;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.function.Function;

@EsProvider
public class PojoResponseParserProvider implements Provider {

    private Type argType;

    @Override
    public void init(MethodAdapter methodAdapter) {
        Type type = methodAdapter.getMethod().getGenericReturnType();
        if (!(type instanceof ParameterizedType)) {
            throw new RuntimeException("return type not generic");
        }

        if (((ParameterizedType) type).getRawType() == Result.class) {
            throw new RuntimeException("return type not Result<T>");
        }

        this.argType = ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    @Provide("responseParser")
    public Function<SearchResponse, Object> provide() {
        return response -> (Result<Object>) () -> new AbstractList<Object>() {
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
    }
}
