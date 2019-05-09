# easyio-runner-runner

## Get started

refer to `io.github.yuankui.easyio.runner.http.demo.local.HttpService`

```java
@RunWith(HttpRunner.class)
@Host("http://localhost:4000")
@Header("Top-Level: OK")
public interface HttpService {

    @Get("/1ibp1rs1")
    String test1(@Query("username") String userName);

    @Get("/1ibp1rs1")
    String test2(@Query("username") String userName, @Query("age") int age);

    @Header({
            "Auth: yuankui",
            "Hello: Kitty",
            "Host: www.baidu.com"
    })
    @Get("/1ibp1rs1")
    String staticHeader(@Query("username") String userName, @Query("age") int age);

    @Get("/1ibp1rs1")
    @Query({"age: 18", "size: 20"})
    String staticQuery(@Query("username") String userName);

    @Get("/1ibp1rs1")
    String header(@Header("username") String userName);
    
    @Post("/1ibp1rs1")
    String post(@Header("username") String userName);
}

```
## features

- `@Query`(static & dynamic)
- `@Header`(static & dynamic)
- `@Host`
- `@Body`
- `@Get` & `@Post`
- `@Variable`: used in path placeholder
- `@HeaderSupply`: common used header provider, e.g. `Authorization`

## TODO

- ProxyProvider(dynamic, static)
- Async

## 扩展用法

- http注册中心支持
  - 在微服务架构中，根据服务名，然后联系注册中心，动态生成host, path, method，完成客户端的负载均衡，由于provider可以支持返回caller，所以这些都可以是动态的。