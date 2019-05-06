package io.github.yuankui.easyio.runner.http.demo.local;

import io.github.yuankui.easyio.runner.http.HttpRunner;
import io.github.yuankui.easyio.runner.http.annotation.*;
import io.github.yuankui.easyio.core.RunWith;


@RunWith(HttpRunner.class)
@Host("http://172.25.163.152:4000")
@Header("Top-Level: OK")
@HeaderSupply(AuthHeaderSupplier.class)
public interface HttpService {
    String path = "/17i4ksc1";

    @Get(path)
    String test1(@Query("username") String userName);

    @Get(path)
    String test2(@Query("username") String userName, @Query("age") int age);

    @Header({
            "Auth: yuankui",
            "Hello: Kitty",
            "Host: www.baidu.com"
    })
    @Get(path)
    String staticHeader(@Query("username") String userName, @Query("age") int age);

    @Get(path)
    @Query({"age: 18", "size: 20"})
    String staticQuery(@Query("username") String userName);

    @Get(path)
    String header(@Header("username") String userName);
    
    @Post(path)
    String post(@Header("username") String userName);
    
    @Get("/${path}")
    String getPath(@Variable("path") String path, @Query("userName") String userName);
}
