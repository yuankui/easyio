package io.github.yuankui.easyio.runner.mq.providers;

import io.github.yuankui.easyio.runner.mq.HttpProvider;
import io.github.yuankui.easyio.runner.mq.annotation.Get;
import io.github.yuankui.easyio.runner.mq.annotation.Post;
import io.github.yuankui.easyio.runner.mq.resource.HttpMethod;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;

import java.lang.reflect.Method;

@HttpProvider
public class MethodProvider implements Provider {

    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
    }

    @Provide("method")
    public HttpMethod provide() {
        if (method.getAnnotation(Get.class) != null) {
            return HttpMethod.GET;
        }

        if (method.getAnnotation(Post.class) != null) {
            return HttpMethod.POST;
        }

        throw new RuntimeException("no @Get or @Post annotation");
    }
}
