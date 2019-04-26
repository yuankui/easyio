package io.github.yuankui.easyio.generic;


import io.github.yuankui.easyio.core.EasyIOConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(EasyIOConfiguration.class)
public class GenericConfiguration {

}
