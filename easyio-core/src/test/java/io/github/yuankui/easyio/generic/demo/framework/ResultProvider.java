package io.github.yuankui.easyio.generic.demo.framework;

import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.Prototype;

import java.util.Map;
import java.util.stream.Collectors;

@Prototype
public class ResultProvider implements DemoProvider {

    @Provide("result")
    public Caller<Map<String, String>> provide(
            @Depend(value = "query") Caller<Map<String, String>> caller) {
        return ioContext -> caller.call(ioContext).entrySet().stream()
                .collect(Collectors.toMap(
                        kv -> kv.getKey(),
                        kv -> kv.getValue()
                ));
    }
}
