package io.github.yuankui.easyio.runner.mq.providers;

import io.github.yuankui.easyio.runner.mq.HttpProvider;
import io.github.yuankui.easyio.runner.mq.annotation.Get;
import io.github.yuankui.easyio.runner.mq.annotation.Post;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;

import java.lang.reflect.Method;

@HttpProvider
public class PathProvider implements Provider {

    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
    }

    @Provide("path")
    public String provide() {
        Get get = method.getAnnotation(Get.class);
        if (get != null) {
            return get.value();
        }

        Post post = method.getAnnotation(Post.class);
        if (post != null) {
            return post.value();
        }

        throw new RuntimeException("no @Get or @Post annotation");
    }
}
