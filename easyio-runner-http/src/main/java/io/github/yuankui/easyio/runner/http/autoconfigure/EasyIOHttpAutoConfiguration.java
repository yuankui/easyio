package io.github.yuankui.easyio.runner.http.autoconfigure;

import io.github.yuankui.easyio.runner.http.HttpConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpConfiguration.class)
public class EasyIOHttpAutoConfiguration {

}
