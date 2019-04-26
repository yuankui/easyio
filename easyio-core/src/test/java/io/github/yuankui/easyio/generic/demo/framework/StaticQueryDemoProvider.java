package io.github.yuankui.easyio.generic.demo.framework;

import com.google.common.collect.ImmutableMap;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.Prototype;

import java.util.Map;

@Prototype
public class StaticQueryDemoProvider implements DemoProvider {
    @Provide("query")
    public Map<String, String> provide() {
        return ImmutableMap.of("static", "static-value");
    }
}
