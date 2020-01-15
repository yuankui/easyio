package io.github.yuankui.easyio.runner.rxjdbc.providers;

import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class SqlProvider implements Provider<String> {
    @Override
    public Result<String> init(Method method, InitContext context) {
        return null;
    }

    @Override
    public String resourceName() {
        return "sql";
    }
}
