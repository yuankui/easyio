package io.github.yuankui.easyio.generic2.example;

import io.github.yuankui.easyio.generic2.CompareResult;
import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;

import java.lang.reflect.Method;

public class ProviderImpl implements Provider<String> {

    @Override
    public Result<String> init(Method method, InitContext context) {
        return null;
    }

    @Override
    public String resourceName() {
        return null;
    }

    @Override
    public CompareResult compare(Provider<String> competitor) {
        return CompareResult.better(99, "我是最优秀的");
    }
}
