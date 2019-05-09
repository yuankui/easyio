package io.github.yuankui.easyio.runner.mq.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.mq.HttpProvider;
import io.github.yuankui.easyio.runner.mq.resource.HttpMethod;
import io.github.yuankui.easyio.runner.mq.resource.HttpRequest;
import io.github.yuankui.easyio.runner.mq.resource.HttpResponse;
import okhttp3.*;

/**
 * TODO 将http行为抽象成一个http engine
 */
@HttpProvider
public class HttpResponseProvider implements Provider {

    @Provide("httpResponse")
    public Caller<HttpResponse> provide(@Depend("httpRequest") Caller<HttpRequest> requestCaller) {
        return ioContext -> {
            HttpRequest request = requestCaller.call(ioContext);
            Request.Builder requestBuilder = new Request.Builder();
            // url
            String url = request.getHost() + request.getPath();
            final HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();

            request.getQuery().forEach(
                    (kv) -> builder.addQueryParameter(kv.getKey(), kv.getValue())
            );
            HttpUrl httpUrl = builder.build();
            requestBuilder.url(httpUrl);

            // method & body
            RequestBody body = null;
            if (request.getHttpMethod() == HttpMethod.POST) {
                body = RequestBody.create(null, request.getBody());
            }

            requestBuilder.method(request.getHttpMethod().name(), body);

            // headers
            request.getHeaders().forEach((kv) -> requestBuilder.addHeader(kv.getKey(), kv.getValue()));

            // execute
            OkHttpClient client = new OkHttpClient();

            HttpResponse httpResponse = new HttpResponse();


            // parse response
            final Response response = client.newCall(requestBuilder.build()).execute();

            // body
            httpResponse.setBody(response.body().bytes());

            // status
            httpResponse.setStatusCode(response.code());

            // parse headers
            response.headers().toMultimap().forEach((k, v) -> httpResponse.getHeaders().put(k, v.get(0)));

            return httpResponse;
        };
        
    }
}
