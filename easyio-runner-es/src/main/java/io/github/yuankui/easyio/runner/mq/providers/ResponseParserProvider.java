package io.github.yuankui.easyio.runner.mq.providers;


import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.mq.EsProvider;
import io.github.yuankui.easyio.runner.mq.resource.EsHit;

import java.util.function.Function;

@EsProvider
public class ResponseParserProvider implements Provider {
    
    @Provide("responseParser")
    public Function<EsHit, Object> provide() {
        return a -> null;
    }
}
