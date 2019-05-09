package io.github.yuankui.easyio.runner.mq;

import io.github.yuankui.easyio.runner.mq.resource.HttpRequest;

public interface RequestInterceptor {
    void intercept(HttpRequest httpRequest);
}
