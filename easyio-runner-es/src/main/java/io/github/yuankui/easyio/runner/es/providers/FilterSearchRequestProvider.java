package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EsProvider
public class FilterSearchRequestProvider implements Provider {

    @Provide("request")
    public Caller<SearchRequest> provide(@Depend("query") List<Caller<QueryBuilder>> queries) {
        return ioContext -> {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder bool = QueryBuilders.boolQuery();
            for (Caller<QueryBuilder> query : queries) {
                QueryBuilder queryBuilder = query.call(ioContext);
                bool.must(queryBuilder);
            }
            sourceBuilder.query(bool);
            
            // TODO add page customized
            sourceBuilder.from(0);
            sourceBuilder.size(5);
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            request.source(sourceBuilder);
            return request;
        };
    }
}
