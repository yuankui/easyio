package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.generic.tuple.Tuple3;
import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.runner.http.annotation.Header;
import io.github.yuankui.easyio.runner.http.resource.KeyValue;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@HttpProvider
public class HeaderProvider implements Provider {

    private Map<String, Integer> keyIndexMap = new HashMap<>();
    private MethodAdapter methodAdapter;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.methodAdapter = methodAdapter;
    }

    @Provide("header")
    public Caller<List<KeyValue>> provide() {
        this.keyIndexMap = methodAdapter.params().stream()
                .flatMap(param -> param
                        .getAnnotations(Header.class)
                        .stream().map(query -> {
                            final String[] key = query.value();
                            if (key.length != 1) {
                                throw new RuntimeException("@Query on parameter must only be size(1)");
                            }
                            return new Tuple3<>(param.getIndex(), param.getReturnType(), key[0]);
                        })
                )
                .collect(Collectors.toMap(
                        Tuple3::getE3,
                        Tuple3::getE1
                ));

        if (CollectionUtils.isEmpty(keyIndexMap)) {
            throw new RuntimeException("no @Header found");
        }

        return ioContext -> {
            List<KeyValue> header = new ArrayList<>();
            keyIndexMap.forEach((key, index) -> {
                Object value = ioContext.getArgs()[index];
                if (value != null) {
                    header.add(new KeyValue(key, value.toString()));
                }
            });
            return header;
        };
    }
}
