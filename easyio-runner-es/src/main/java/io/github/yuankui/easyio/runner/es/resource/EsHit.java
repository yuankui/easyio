package io.github.yuankui.easyio.runner.es.resource;

import java.util.Map;

public interface EsHit {
    String getIndex();
    Map<String, Object> getSource();
}
