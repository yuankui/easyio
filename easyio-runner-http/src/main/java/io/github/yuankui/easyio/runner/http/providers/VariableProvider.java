package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.runner.http.annotation.Variable;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@HttpProvider
public class VariableProvider implements Provider {

    private Map<String, Integer> keyIndexMap = new HashMap<>();
    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
        
    }

    @Provide("variable")
    public Caller<Map<String, String>> provide() {
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            final Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                if (annotation instanceof Variable) {
                    final String key = ((Variable) annotation).value();
                    Integer index = i;
                    keyIndexMap.put(key, index);
                }
            }
        }

        if (CollectionUtils.isEmpty(keyIndexMap)) {
            throw new RuntimeException("no @Variable found");
        }

        return ioContext -> {
            Map<String, String> query = new HashMap<>();
            keyIndexMap.forEach((key, index) -> {
                Object value = ioContext.getArgs()[index];
                if (value != null) {
                    query.put(key, value.toString());
                }
            });
            return query;
        };
    }
}
