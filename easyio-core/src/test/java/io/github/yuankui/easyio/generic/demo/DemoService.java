package io.github.yuankui.easyio.generic.demo;

import io.github.yuankui.easyio.core.RunWith;
import io.github.yuankui.easyio.generic.demo.framework.DemoRunner;
import io.github.yuankui.easyio.generic.demo.framework.Query;

@RunWith(DemoRunner.class)
public interface DemoService {
    Object get(@Query("name") String name, @Query("age") int age);
}
