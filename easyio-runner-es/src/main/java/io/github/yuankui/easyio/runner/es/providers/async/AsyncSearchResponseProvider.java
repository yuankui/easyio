package io.github.yuankui.easyio.runner.es.providers.async;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@EsProvider
public class AsyncSearchResponseProvider implements Provider {
    private RestHighLevelClient client;
    
    @Override
    public void init(MethodAdapter methodAdapter) {
        
    }

    @Provide("asyncResponse")
    public Caller<Mono<SearchResponse>> provide(@Depend("request") Caller<SearchRequest> request,
                                                @Depend("client") RestHighLevelClient client) {
        return ioContext -> {
            RequestOptions options = RequestOptions.DEFAULT;
            return Mono.create(sink -> {
                SearchRequest req = null;
                try {
                    req = request.call(ioContext);
                } catch (IOException e) {
                    sink.error(e);
                }
                client.searchAsync(req, options, new ActionListener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse searchResponse) {
                        sink.success(searchResponse);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        sink.error(e);
                    }
                });
            });
        };
    }
}
