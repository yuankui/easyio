package io.github.yuankui.easyio.generic.demo.framework;

import io.github.yuankui.easyio.generic.GenericConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GenericConfiguration.class)
@ComponentScan
public class DemoConfig {
}
