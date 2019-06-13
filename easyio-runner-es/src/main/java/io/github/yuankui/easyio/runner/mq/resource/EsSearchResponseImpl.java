package io.github.yuankui.easyio.runner.mq.resource;

import org.elasticsearch.action.search.SearchResponse;

import java.util.List;

public class EsSearchResponseImpl implements EsSearchResponse {
    private SearchResponse response;

    public EsSearchResponseImpl(SearchResponse response) {
        this.response = response;
    }

    @Override
    public List<EsHit> getHits() {
        return new EsHits(response.getHits());
    }
}
