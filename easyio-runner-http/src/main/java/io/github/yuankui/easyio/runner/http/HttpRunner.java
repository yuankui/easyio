package io.github.yuankui.easyio.runner.http;


import io.github.yuankui.easyio.generic.GenericRunner;
import io.github.yuankui.easyio.generic.filter.AnnotationProviderFilter;
import io.github.yuankui.easyio.generic.filter.ProviderFilter;
import org.springframework.stereotype.Component;

@Component
public class HttpRunner extends GenericRunner {

    @Override
    public ProviderFilter getProviderFilter() {
        return new AnnotationProviderFilter(HttpProvider.class);
    }
}
