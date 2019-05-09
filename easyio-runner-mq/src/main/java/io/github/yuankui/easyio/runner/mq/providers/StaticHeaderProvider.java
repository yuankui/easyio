package io.github.yuankui.easyio.runner.mq.providers;

import io.github.yuankui.easyio.runner.mq.HttpProvider;
import io.github.yuankui.easyio.runner.mq.annotation.Header;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.mq.resource.KeyValue;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@HttpProvider
public class StaticHeaderProvider implements Provider {

    private List<KeyValue> headers = new ArrayList<>();
    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
    }
    
    private void addHeaders(Header header) {
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

    @Provide("header")
    public List<KeyValue> provide() {
        Class<?> clazz = method.getDeclaringClass();
        Header header = clazz.getAnnotation(Header.class);
        addHeaders(header);
        header = method.getAnnotation(Header.class);
        addHeaders(header);

        if (CollectionUtils.isEmpty(headers)) {
            throw new RuntimeException("no @Header found");
        }
        
        return headers;
    }
}
