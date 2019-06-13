package io.github.yuankui.easyio.runner.es.resource;

import org.elasticsearch.search.SearchHit;

import java.util.Map;

public class EsHitImpl implements EsHit {
    private SearchHit hit;

    public EsHitImpl(SearchHit hit) {
        this.hit = hit;
    }

    @Override
    public String getIndex() {
        return hit.getIndex();
    }

    @Override
    public Map<String, Object> getSource() {
        return hit.getSourceAsMap();
    }
}
