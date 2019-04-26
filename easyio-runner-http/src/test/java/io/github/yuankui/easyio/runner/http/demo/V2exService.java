package io.github.yuankui.easyio.runner.http.demo;

import io.github.yuankui.easyio.runner.http.HttpRunner;
import io.github.yuankui.easyio.core.RunWith;
import io.github.yuankui.easyio.runner.http.annotation.Get;
import io.github.yuankui.easyio.runner.http.annotation.Host;
import io.github.yuankui.easyio.runner.http.annotation.Query;


@RunWith(HttpRunner.class)
@Host("https://www.v2ex.com")
public interface V2exService {

    @Get("/api/members/show.json")
    UserInfo getUserWithName(@Query("username") String userName);
}
