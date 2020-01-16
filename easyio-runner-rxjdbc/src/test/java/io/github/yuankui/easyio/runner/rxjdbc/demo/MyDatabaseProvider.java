package io.github.yuankui.easyio.runner.rxjdbc.demo;

import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import io.github.yuankui.easyio.runner.rxjdbc.RxsqlProvider;
import org.davidmoten.rx.jdbc.Database;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@RxsqlProvider
@Component
public class MyDatabaseProvider implements Provider<Database> {
    @Override
    public Result<Database> init(Method method, InitContext context) {
        String url = "jdbc:mysql://10.24.76.171:5002/waimai_d_profileplatform" +
                "?characterEncoding=UTF8" +
                "&socketTimeout=60000" +
                "&allowMultiQueries=true" +
                "&user=waimai_d_profileplatform" +
                "&password=291%23QSNtel";
        
        try {
            return Result.success(ioContext -> Database.from(url, 4));
        } catch (Exception e) {
            return Result.fail("json parse error, check classpath:/easyio/rxjdbc/database.yml");
        }
    }

    @Override
    public String resourceName() {
        return "database";
    }
}
