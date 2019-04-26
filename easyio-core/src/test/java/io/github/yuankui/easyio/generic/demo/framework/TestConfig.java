package io.github.yuankui.easyio.generic.demo.framework;

import io.github.yuankui.easyio.core.EasyIOScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DemoConfig.class)
@EasyIOScan("io.github.yuankui.easyio.generic4.demo")
public class TestConfig {
}
