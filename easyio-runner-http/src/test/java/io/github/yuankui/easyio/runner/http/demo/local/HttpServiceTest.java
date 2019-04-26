package io.github.yuankui.easyio.runner.http.demo.local;

import io.github.yuankui.easyio.runner.http.HttpConfiguration;
import io.github.yuankui.easyio.core.EasyIOScan;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpConfiguration.class)
@EasyIOScan("io.github.yuankui..easyio.runner.http.demo")
public class HttpServiceTest {

    private static HttpService httpService;

    @BeforeClass
    public static void init() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(HttpServiceTest.class);

        httpService = context.getBean(HttpService.class);
    }
    
    @Test
    public void query() {
        String result = httpService.test1("yuankui");
        System.out.println("res = " + result);
    }
    
    @Test
    public void test2() {
        String res = httpService.test2("yuankui", 18);
        System.out.println("res = " + res);
    }    
    
    @Test
    public void staticHeader() {
        String res = httpService.staticHeader("yuankui", 18);
        System.out.println("res = " + res);
    }    
    
    @Test
    public void staticQuery() {
        String res = httpService.staticQuery("yuankui");
        System.out.println("res = " + res);
    }

    @Test
    public void header() {
        String res = httpService.header("yuankui");
        System.out.println("res = " + res);
    }  
    
    @Test
    public void post() {
        String res = httpService.post("yuankui");
        System.out.println("res = " + res);
    }

    @Test
    public void getPath() {
        String res = httpService.getPath("1ibp1rs1", "yuankui");
        System.out.println("res = " + res);
    }
}