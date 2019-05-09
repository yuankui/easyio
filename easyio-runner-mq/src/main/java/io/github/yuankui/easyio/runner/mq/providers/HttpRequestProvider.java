package io.github.yuankui.easyio.runner.mq.providers;

import io.github.yuankui.easyio.runner.mq.resource.HttpRequest;
import io.github.yuankui.easyio.runner.mq.resource.KeyValue;
import io.github.yuankui.easyio.runner.mq.HttpProvider;
import io.github.yuankui.easyio.runner.mq.resource.HttpMethod;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;

import java.util.ArrayList;
import java.util.List;

@HttpProvider
public class HttpRequestProvider implements Provider {

    @Provide("httpRequest")
    public Caller<HttpRequest> provide(@Depend("method") HttpMethod method,
                                       @Depend("path") Caller<String> path,
                                       @Depend("host") Caller<String> host,
                                       @Depend(value = "body", must = false) Caller<byte[]> body,
                                       @Depend(value = "header", many = true) List<Caller<List<KeyValue>>> headerList,
                                       @Depend(value = "query", many = true) List<Caller<List<KeyValue>>> queryList) {

        return ioContext -> {
            HttpRequest httpRequest = new HttpRequest();

            // method
            httpRequest.setHttpMethod(method);

            // path
            httpRequest.setPath(path.call(ioContext));

            // query
            List<KeyValue> fullQuery = new ArrayList<>();
            for (Caller<List<KeyValue>> query : queryList) {
                fullQuery.addAll(query.call(ioContext));
            }
            httpRequest.setQuery(fullQuery);

            // host
            httpRequest.setHost(host.call(ioContext));

            // body
            if (body != null) {
                httpRequest.setBody(body.call(ioContext));
            }

            // header
            List<KeyValue> fullHeaders = new ArrayList<>();
            for (Caller<List<KeyValue>> header : headerList) {
                fullHeaders.addAll(header.call(ioContext));
            }
            httpRequest.setHeaders(fullHeaders);

            return httpRequest;
        };
    }
}
