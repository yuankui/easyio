package io.github.yuankui.easyio.runner.es.demo;

import io.github.yuankui.easyio.core.EasyIOScan;
import io.github.yuankui.easyio.runner.es.EsConfiguration;
import io.github.yuankui.easyio.runner.es.resource.Result;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import(EsConfiguration.class)
@EasyIOScan("io.github.yuankui.easyio.runner.es.demo")
public class EsDemoServiceTest {

    private static EsDemoService esDemoService;

    @BeforeClass
    public static void init() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(EsDemoServiceTest.class);

        esDemoService = context.getBean(EsDemoService.class);
    }
    @Test
    public void findByName() {
        Result<Person> persons = esDemoService.findByName("yuankui");

        List<Person> people = persons.getData();

        System.out.println("people = " + people);
    }
}