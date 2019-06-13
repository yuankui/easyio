package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

@EsProvider
public class SearchResponseProvider implements Provider {
    private RestHighLevelClient client;
    
    @Override
    public void init(MethodAdapter methodAdapter) {
        
    }

    @Provide("result")
    public Caller<SearchResponse> provide(@Depend("request") Caller<SearchRequest> request,
                                          @Depend("client") RestHighLevelClient client) {
        return ioContext -> {
            RequestOptions options = RequestOptions.DEFAULT;
            return client.search(request.call(ioContext), options);
        };
    }
}
