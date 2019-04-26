# EasyIO

> performing IO the easier way.

## Get Started

> io.github.yuankui.easyio.runner.http.demo.V2exServiceTest#query

### define your interface
```java
@RunWith(HttpRunner.class)
@Host("https://www.v2ex.com")
public interface V2exService {

    @Get("/api/members/show.json")
    UserInfo getUserWithName(@Query("username") String userName);
}
```

### get the instance
```java
@Configuration
@Import(HttpConfiguration.class)
@EasyIOScan("io.github.yuankui.easyio.runner.http.demo")
public class V2exServiceTest {

    @Test
    public void query() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(V2exServiceTest.class);

        V2exService v2exService = context.getBean(V2exService.class);
        UserInfo result = v2exService.getUserWithName("yuankui");
        System.out.println("result = " + result);
    }
}
```

## Supported Runners

- [HttpRunner](https://github.com/yuankui/easyio/tree/master/easyio-runner-http)

## TODO
- RedisRunner
- HBaseRunner
- EsRunner

## Inspired By

- MyBatis
- Retrofit
- Jpa