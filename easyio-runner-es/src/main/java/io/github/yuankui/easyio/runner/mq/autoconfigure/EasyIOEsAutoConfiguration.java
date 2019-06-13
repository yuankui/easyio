package io.github.yuankui.easyio.runner.mq.autoconfigure;

import io.github.yuankui.easyio.runner.mq.EsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EsConfiguration.class)
public class EasyIOEsAutoConfiguration {

}
