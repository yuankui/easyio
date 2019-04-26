package io.github.yuankui.easyio.generic.demo;

import io.github.yuankui.easyio.generic.demo.framework.TestConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class DemoProviderTest {
    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

        DemoService service = context.getBean(DemoService.class);
        Object ret = service.get("yuankui", 12);
        System.out.println("ret = " + ret);
    }
}