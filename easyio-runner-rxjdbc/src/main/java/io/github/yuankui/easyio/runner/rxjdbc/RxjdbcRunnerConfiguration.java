package io.github.yuankui.easyio.runner.rxjdbc;

import io.github.yuankui.easyio.spring.EasyIOConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EasyIOConfiguration.class)
@ComponentScan
public class RxjdbcRunnerConfiguration {
}
