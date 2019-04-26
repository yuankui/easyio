package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.PrototypeScopeMetadataResolver;
import io.github.yuankui.easyio.generic.GenericConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(HttpProvider.class),
}, scopeResolver = PrototypeScopeMetadataResolver.class)
@Import(GenericConfiguration.class)
public class HttpConfiguration {

}
