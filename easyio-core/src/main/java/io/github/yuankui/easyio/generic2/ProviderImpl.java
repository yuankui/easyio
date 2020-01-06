package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;

public class ProviderImpl implements Provider<String> {
    @Override
    public void init(Method method, InitContext context) {
    }

    @Override
    public String provide(ProcessContext context) {
        String result = context.getResource("result", new TypeHint<String>() {});
        System.out.println("result = " + result);
        return result;
    }

    @Override
    public String name() {
        return "result";
    }
}
