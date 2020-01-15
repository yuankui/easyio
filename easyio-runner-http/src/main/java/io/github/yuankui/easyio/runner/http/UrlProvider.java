package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import io.github.yuankui.easyio.runner.http.annotations.Get;

import java.lang.reflect.Method;

public class UrlProvider implements Provider<String> {
    @Override
    public Result<String> init(Method method, InitContext context) {
        Get get = method.getAnnotation(Get.class);
        if (get == null) {
            return Result.fail("no @Get presented");
        }
        String url = get.value();
        return Result.success(ioContext -> url);
    }

    @Override
    public String resourceName() {
        return "url";
    }
}
