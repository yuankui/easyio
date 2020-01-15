package io.github.yuankui.easyio.runner.rxjdbc.providers;

import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import io.github.yuankui.easyio.runner.rxjdbc.HttpProvider;
import io.github.yuankui.easyio.runner.rxjdbc.annotations.Get;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@HttpProvider
@Component
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
