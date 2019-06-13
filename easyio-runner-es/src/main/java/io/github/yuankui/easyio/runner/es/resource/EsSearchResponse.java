package io.github.yuankui.easyio.runner.es.resource;

import java.util.List;

public interface EsSearchResponse {
    List<EsHit> getHits();
}
