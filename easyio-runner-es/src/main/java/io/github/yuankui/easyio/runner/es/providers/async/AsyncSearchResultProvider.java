package io.github.yuankui.easyio.runner.es.providers.async;

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
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EsProvider
public class AsyncSearchResultProvider implements Provider {
    @Autowired
    private ApplicationContext context;
    private Parser parser = null;
    
    @Override
    public void init(MethodAdapter methodAdapter) {
        Type type = methodAdapter.getMethod().getGenericReturnType();
        if (!(type instanceof ParameterizedType)) {
            throw new RuntimeException("return type not generic");
        }
        
        if (((ParameterizedType) type).getRawType() != Mono.class) {
            throw new RuntimeException("return type not Mono<T>");
        }

        Type innerType = ((ParameterizedType) type).getActualTypeArguments()[0];
        Map<String, Parser> beansOfType = context.getBeansOfType(Parser.class);
        List<String> messages = new ArrayList<>();
        
        beansOfType.forEach((s, parser) -> {
            try {
                parser.init(SearchResponse.class, innerType);
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
    public Caller<Mono<Object>> provide(@Depend("asyncResponse") Caller<Mono<SearchResponse>> caller) {
        return ioContext -> {
            Mono<SearchResponse> monoResponse = caller.call(ioContext);
            return monoResponse.map(response -> parser.parse(response));
        };
    }

    @Override
    public int race(Provider other) {
        // 比默认的SearchResultProvider优先级要高一些
        return 1;
    }
}
