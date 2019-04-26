package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@HttpProvider
public class VariablePathProvider implements Provider {

    @Provide("path")
    public Caller<String> provide(@Depend(value = "variable", many = true) List<Caller<Map<String, String>>> variableList,
                                  @Depend("path") String path) {

        return ioContext -> {
            // 获取variable
            Map<String, String> data = new HashMap<>();
            for (Caller<Map<String, String>> variableMap : variableList) {
                data.putAll(variableMap.call(ioContext));
            }

            // 开始替换
            StrSubstitutor substitutor = new StrSubstitutor(data);
            return substitutor.replace(path);
        };
    }

    @Override
    public int race(Provider other) {
        if (other instanceof PathProvider) {
            return 1;
        }
        return 0;
    }
}
