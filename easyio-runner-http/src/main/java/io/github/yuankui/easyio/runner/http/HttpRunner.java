package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.generic2.GenericRunner;
import io.github.yuankui.easyio.generic2.Provider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class HttpRunner extends GenericRunner {
    @Override
    public List<Provider> getProviderList() {
        return Arrays.asList(
                new ResultProvider(), 
                new UrlProvider()
        );
    }
}
