package io.github.yuankui.easyio.runner.es.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.es.EsProvider;
import io.github.yuankui.easyio.runner.es.providers.parser.Parser;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EsProvider
public class SearchResultProvider implements Provider {
    @Autowired
    private ApplicationContext context;
    private Parser parser = null;
    
    @Override
    public void init(MethodAdapter methodAdapter) {
        Type type = methodAdapter.getMethod().getGenericReturnType();
        Map<String, Parser> beansOfType = context.getBeansOfType(Parser.class);
        List<String> messages = new ArrayList<>();
        
        beansOfType.forEach((s, parser) -> {
            try {
                parser.init(SearchResponse.class, type);
                this.parser = parser;
            } catch (Exception e) {
                messages.add(e.getMessage());
            }
        });

        if (this.parser == null) {
            throw new RuntimeException("no proper parser found:" + messages);
        }
    }

    @Provide("result")
    public Caller<Object> provide(@Depend("response") Caller<SearchResponse> responseFuture) {
        return ioContext -> {
            SearchResponse response = responseFuture.call(ioContext);
            return parser.parse(response);
        };
    }
}
