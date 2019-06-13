package io.github.yuankui.easyio.runner.mq.providers;


import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.mq.EsProvider;
import org.elasticsearch.action.search.SearchResponse;

import java.util.function.Function;

@EsProvider
public class PojoResponseParserProvider implements Provider {

    @Override
    public void init(MethodAdapter methodAdapter) {
        if (methodAdapter.getMethod().getReturnType() == SearchResponse.class) {
            throw new RuntimeException("return type not SearchResponse");
        }
    }

    @Provide("responseParser")
    public Function<SearchResponse, Object> provide() {
        return a -> a;
    }
}
