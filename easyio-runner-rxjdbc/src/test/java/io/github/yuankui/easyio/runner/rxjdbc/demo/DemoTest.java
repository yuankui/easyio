package io.github.yuankui.easyio.runner.rxjdbc.demo;

import io.github.yuankui.easyio.runner.rxjdbc.RxsqlRunnerConfiguration;
import io.github.yuankui.easyio.spring.EasyIOScan;
import io.reactivex.Flowable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoTest.class)
@Configuration
@ComponentScan
@Import(RxsqlRunnerConfiguration.class)
@EasyIOScan("io.github.yuankui.easyio.runner.rxjdbc.demo")
public class DemoTest {
    
    @Autowired
    private SomeService someService;
    
    @Test
    public void test() {
        Flowable<Label> labels = someService.getLabels(1);
        
        labels.blockingForEach(l -> System.out.println("l = " + l));
    }
}
