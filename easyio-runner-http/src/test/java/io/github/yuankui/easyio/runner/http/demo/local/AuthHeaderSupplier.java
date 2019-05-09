package io.github.yuankui.easyio.runner.http.demo.local;

import io.github.yuankui.easyio.runner.http.header.HeaderSupplier;
import io.github.yuankui.easyio.runner.http.resource.KeyValue;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class AuthHeaderSupplier implements HeaderSupplier {
    @Override
    public List<KeyValue> supply() {
        return Arrays.asList(new KeyValue("Authorization", new Date().toString()));
    }
}
