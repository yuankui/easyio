package io.github.yuankui.easyio.runner.mq;

import io.github.yuankui.easyio.PrototypeScopeMetadataResolver;
import io.github.yuankui.easyio.generic.GenericConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(EsProvider.class),
}, scopeResolver = PrototypeScopeMetadataResolver.class)
@Import(GenericConfiguration.class)
public class EsConfiguration {

}
