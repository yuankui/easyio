package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import io.github.yuankui.easyio.runner.es.annotation.Term;
import io.github.yuankui.easyio.runner.es.resource.Page;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@EsProvider
public class RawPageProvider implements Provider {

    private int index;

    @Override
    public void init(MethodAdapter methodAdapter) {
        List<MethodAdapter.Param> params = methodAdapter.params()
                .stream()
                .filter(param -> param.getReturnType() == Page.class)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("no Page param found");
        }

        if (params.size() > 1) {
            throw new RuntimeException("multiple Page param found");
        }

        this.index = params.get(0).getIndex();
    }

    @Provide("page")
    public Caller<Page> provide() {
        return ioContext -> (Page) ioContext.getArgs()[index];
    }
}
