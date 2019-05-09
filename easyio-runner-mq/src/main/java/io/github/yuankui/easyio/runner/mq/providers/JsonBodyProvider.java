package io.github.yuankui.easyio.runner.mq.providers;

import com.alibaba.fastjson.JSON;
import io.github.yuankui.easyio.runner.mq.HttpProvider;
import io.github.yuankui.easyio.runner.mq.annotation.Body;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@HttpProvider
public class JsonBodyProvider implements Provider {

    private Integer bodyParamIndex = null;
    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
    }

    @Provide("body")
    public Caller<byte[]> provide() {
        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            Annotation[] annotations = method.getParameterAnnotations()[i];
            for (Annotation annotation : annotations) {
                if (annotation instanceof Body) {
                    if (bodyParamIndex != null) {
                        throw new RuntimeException("multi param annotated with @Body:" + bodyParamIndex + "," + i);
                    }
                    bodyParamIndex = i;
                }
            }
        }
        if (bodyParamIndex == null) {
            throw new RuntimeException("no @Body found");
        }

        return ioContext -> {
            Object body = ioContext.getArgs()[bodyParamIndex];
            if (body == null) {
                return null;
            }
            return JSON.toJSONBytes(body);
        };
        
    }
}
