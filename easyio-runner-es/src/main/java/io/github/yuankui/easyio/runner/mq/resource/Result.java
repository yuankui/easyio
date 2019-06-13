package io.github.yuankui.easyio.runner.mq.resource;

import java.util.List;

public interface Result<T> {
    List<T> getData();
}
