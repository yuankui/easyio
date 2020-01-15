package io.github.yuankui.easyio.runner.http;

import io.github.yuankui.easyio.runner.http.annotations.Get;
import io.github.yuankui.easyio.spring.RunWith;

@RunWith(HttpRunner.class)
public interface V2exService {
    
    @Get("https://www.v2ex.com/api/nodes/show.json")
    String getUser();
}
