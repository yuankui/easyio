package io.github.yuankui.easyio.runner.mq.autoconfigure;

import io.github.yuankui.easyio.runner.mq.HttpConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpConfiguration.class)
public class EasyIOHttpAutoConfiguration {

}
