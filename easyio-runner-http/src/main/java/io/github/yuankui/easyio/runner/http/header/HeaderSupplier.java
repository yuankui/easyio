package io.github.yuankui.easyio.runner.http.header;

import io.github.yuankui.easyio.runner.http.resource.KeyValue;

import java.util.List;

public interface HeaderSupplier {
    List<KeyValue> supply();
}
