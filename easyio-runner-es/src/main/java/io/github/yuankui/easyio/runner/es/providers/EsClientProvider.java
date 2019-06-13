package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import io.github.yuankui.easyio.runner.es.annotation.Host;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

@EsProvider
public class EsClientProvider implements Provider {

    private RestHighLevelClient client;

    @Override
    public void init(MethodAdapter methodAdapter) {
        Host host = methodAdapter.getAnnotation(Host.class);
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host.host(), host.port(), "http")));
        this.client = client;
    }

    @Provide("client")
    public RestHighLevelClient provide() {
        return client;
    }
}
