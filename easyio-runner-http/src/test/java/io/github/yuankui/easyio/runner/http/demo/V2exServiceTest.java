package io.github.yuankui.easyio.runner.http.demo;

import io.github.yuankui.easyio.core.EasyIOScan;
import io.github.yuankui.easyio.runner.http.HttpConfiguration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpConfiguration.class)
@EasyIOScan("io.github.yuankui.easyio.runner.http.demo")
public class V2exServiceTest {

    @Test
    public void query() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(V2exServiceTest.class);

        V2exService v2exService = context.getBean(V2exService.class);
        UserInfo result = v2exService.getUserWithName("livid");
        System.out.println("result = " + result);
    }
}