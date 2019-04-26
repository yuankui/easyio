package io.github.yuankui.easyio.runner.http.providers;

import com.alibaba.fastjson.JSON;
import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.runner.http.resource.HttpResponse;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;

import java.lang.reflect.Type;

@HttpProvider
public class JsonResultProvider implements Provider {

    private Type returnType;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.returnType = methodAdapter.getMethod().getGenericReturnType();
    }

    @Provide("result")
    public Caller<Object> provide(@Depend("httpResponse") Caller<HttpResponse> httpResponse) {
        return ioContext -> {
            byte[] body = httpResponse.call(ioContext).getBody();
            if (returnType == String.class) {
                return new String(body);
            }
            return JSON.parseObject(body, this.returnType);
        };
    }
}
