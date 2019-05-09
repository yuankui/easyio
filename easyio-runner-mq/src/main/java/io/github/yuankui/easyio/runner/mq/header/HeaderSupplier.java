package io.github.yuankui.easyio.runner.mq.header;

import io.github.yuankui.easyio.runner.mq.resource.KeyValue;

import java.util.List;

public interface HeaderSupplier {
    List<KeyValue> supply();
}
