package io.github.yuankui.easyio.runner.es.autoconfigure;

import io.github.yuankui.easyio.runner.es.EsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EsConfiguration.class)
public class EasyIOEsAutoConfiguration {

}
