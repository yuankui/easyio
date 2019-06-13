package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@EsProvider
public class RawSearchRequestProvider implements Provider {

    private int index;

    @Override
    public void init(MethodAdapter methodAdapter) {
        List<MethodAdapter.Param> params = methodAdapter.params()
                .stream()
                .filter(param -> param.getReturnType() == SearchRequest.class)
                .collect(Collectors.toList());

        if (params.size() > 1) {
            throw new RuntimeException("multiple SearchRequest in params");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("no SearchRequest in params");
        }

        this.index = params.get(0).getIndex();
    }

    @Provide("request")
    public Caller<SearchRequest> provide() {
        return ioContext -> (SearchRequest) ioContext.getArgs()[index];
    }
}
