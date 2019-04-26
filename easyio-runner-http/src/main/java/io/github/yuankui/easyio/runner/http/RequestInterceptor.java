package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.runner.http.resource.HttpRequest;

public interface RequestInterceptor {
    void intercept(HttpRequest httpRequest);
}
