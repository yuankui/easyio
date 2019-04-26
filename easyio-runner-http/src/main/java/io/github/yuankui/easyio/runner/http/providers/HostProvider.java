package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.runner.http.annotation.Host;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;

import java.lang.reflect.Method;

@HttpProvider
public class HostProvider implements Provider {

    private String host;
    private Method method;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.method = methodAdapter.getMethod();
        
    }

    @Provide("host")
    public String provide() {
        Host host;

        // class级别的
        Class<?> clazz = method.getDeclaringClass();
        host = clazz.getAnnotation(Host.class);
        if (host != null) {
            this.host = host.value();
        }

        // method级别的
        host = method.getAnnotation(Host.class);
        if (host != null) {
            this.host = host.value();
        }

        if (this.host == null) {
            throw new RuntimeException("no @Host found");
        }
        return this.host;
    }
}
