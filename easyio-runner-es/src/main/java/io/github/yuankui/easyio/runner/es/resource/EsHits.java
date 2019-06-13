package io.github.yuankui.easyio.runner.es.resource;

import org.elasticsearch.search.SearchHits;

import java.util.AbstractList;
import java.util.List;

public class EsHits extends AbstractList<EsHit> implements List<EsHit>  {
    private SearchHits hits;

    public EsHits(SearchHits hits) {
        this.hits = hits;
    }

    @Override
    public EsHit get(int index) {
        return new EsHitImpl(hits.getAt(index));
    }

    @Override
    public int size() {
        return hits.getHits().length;
    }
}
