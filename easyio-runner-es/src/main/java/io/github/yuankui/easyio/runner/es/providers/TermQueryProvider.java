package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import io.github.yuankui.easyio.runner.es.annotation.Term;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@EsProvider
public class TermQueryProvider implements Provider {

    private List<MethodAdapter.Param> params;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.params = methodAdapter.params()
                .stream()
                .filter(param -> !CollectionUtils.isEmpty(param.getAnnotations(Term.class)))
                .collect(Collectors.toList());
    }

    @Provide("query")
    public Caller<QueryBuilder> provide() {
        return ioContext -> {
            BoolQueryBuilder bool = QueryBuilders.boolQuery();
            for (MethodAdapter.Param param : params) {
                List<Term> terms = param.getAnnotations(Term.class);
                for (Term term : terms) {
                    TermQueryBuilder t = QueryBuilders.termQuery(term.value(), ioContext.getArgs()[param.getIndex()]);
                    bool.must(t);
                }
            }
            return bool;
        };
    }
}
