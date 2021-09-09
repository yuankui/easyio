package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.runner.rxjdbc.HttpRunnerConfiguration;
import io.github.yuankui.easyio.spring.EasyIOScan;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpRunnerConfiguration.class)
@EasyIOScan("io.github.yuankui.easyio.runner.http")
public class ResultProviderTest {
    
    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ResultProviderTest.class);

        V2exService bean = context.getBean(V2exService.class);
        String user = bean.getUser();

        System.out.println("user = " + user);
    }
}