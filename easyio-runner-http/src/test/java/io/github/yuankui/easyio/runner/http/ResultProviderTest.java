package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.core.EasyIO;
import io.github.yuankui.easyio.generic2.GenericRunner;
import org.junit.Test;

public class ResultProviderTest {
    @Test
    public void test() {
        GenericRunner runner = new GenericRunner();

        runner.addProvider(new ResultProvider());
        runner.addProvider(new UrlProvider());
        V2exService v2exService = new EasyIO().create(V2exService.class, runner);

        String user = v2exService.getUser();

        System.out.println("user = " + user);
    }
}