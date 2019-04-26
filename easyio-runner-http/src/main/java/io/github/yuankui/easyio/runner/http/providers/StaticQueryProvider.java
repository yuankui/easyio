package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.runner.http.annotation.Query;
import io.github.yuankui.easyio.runner.http.resource.KeyValue;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@HttpProvider
public class StaticQueryProvider implements Provider {

    private List<KeyValue> headers = new ArrayList<>();
    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
    }
    
    private void addHeaders(Query header) {
        if (header == null) {
            return;
        }
        String[] kvs = header.value();
        for (String kv : kvs) {
            String[] split = kv.split(":", 2);
            if (split.length != 2) {
                continue;
            }
            String k = split[0];
            String v = split[1];
            this.headers.add(new KeyValue(k.trim(), v.trim()));
        }
    }

    @Provide("query")
    public List<KeyValue> provide() {
        Class<?> clazz = method.getDeclaringClass();
        Query header = clazz.getAnnotation(Query.class);
        addHeaders(header);
        header = method.getAnnotation(Query.class);
        addHeaders(header);

        if (CollectionUtils.isEmpty(headers)) {
            throw new RuntimeException("no @Query found");
        }
        
        return headers;
    }
}
