package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import org.elasticsearch.action.search.SearchResponse;

import java.util.function.Function;

@EsProvider
public class SearchResultProvider implements Provider {
    @Provide("result")
    public Caller<Object> provide(@Depend("response") Caller<SearchResponse> responseFuture,
                                  @Depend("responseParser") Function<SearchResponse, Object> parser) {
        return ioContext -> {
            SearchResponse response = responseFuture.call(ioContext);
            return parser.apply(response);
        };
    }
}
