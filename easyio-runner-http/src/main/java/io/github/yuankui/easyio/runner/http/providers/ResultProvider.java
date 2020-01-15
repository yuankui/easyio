package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.generic2.*;
import io.github.yuankui.easyio.runner.http.HttpProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

@HttpProvider
@Component
public class ResultProvider implements Provider<Object> {
    @Override
    public Result<Object> init(Method method, InitContext context) {
        Collection<Resource<String>> urls = context.getResources("url");
        Optional<Callable<String>> first = urls.stream()
                .map(r -> r.getCallable())
                .findFirst();

        if (first.isPresent()) {
            return Result.success(ioContext -> {
                OkHttpClient client = new OkHttpClient();
                String url = first.get().call(ioContext);
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    return response.body().string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return Result.fail("no url provided");
    }

    @Override
    public String resourceName() {
        return "result";
    }
}
