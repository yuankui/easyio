package io.github.yuankui.easyio.generic.demo.framework;

import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.Prototype;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Prototype
public class DynamicQueryProvider implements DemoProvider {
    private Method method;
    private Map<Integer, String> indexNameMap = new HashMap<>();

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
    }

    @Provide("query")
    public Caller<Map<String, String>> provide(@Depend(value = "query") Map<String, String> map) {
        for (int i = 0; i < method.getParameterCount(); i++) {
            Optional<Query> queryOptional = Arrays.stream(method.getParameterAnnotations()[i])
                    .filter(annotation -> annotation instanceof Query)
                    .map(annotation -> (Query) annotation)
                    .findFirst();
            if (!queryOptional.isPresent()) {
                continue;
            }

            Query query = queryOptional.get();
            indexNameMap.put(i, query.value());
        }

        return ioContext -> {
            Map<String, String> sum = new HashMap<>();
            sum.putAll(map);
            Map<String, String> self = indexNameMap.entrySet()
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    kv -> kv.getValue(),
                                    kv -> ioContext.getArgs()[kv.getKey()].toString()
                            )
                    );

            sum.putAll(self);
            return sum;
        };
    }

    @Override
    public int race(Provider other) {
        return 1;
    }
}
