package io.github.yuankui.easyio.runner.rxjdbc;

import io.github.yuankui.easyio.runner.rxjdbc.annotations.Get;
import io.github.yuankui.easyio.spring.RunWith;

@RunWith(HttpRunner.class)
public interface V2exService {
    
    @Get("https://www.v2ex.com/api/nodes/show.json")
    String getUser();
}
