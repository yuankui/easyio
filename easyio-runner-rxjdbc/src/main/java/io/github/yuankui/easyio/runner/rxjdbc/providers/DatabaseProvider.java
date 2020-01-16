package io.github.yuankui.easyio.runner.rxjdbc.providers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import io.github.yuankui.easyio.runner.rxjdbc.RxsqlProvider;
import org.davidmoten.rx.jdbc.Database;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Method;

@RxsqlProvider
@Component
public class DatabaseProvider implements Provider<Database> {
    @Override
    public Result<Database> init(Method method, InitContext context) {
        InputStream stream = DatabaseProvider.class.getResourceAsStream("/easyio/rxjdbc/database.yml");
        try {
            JSONObject json = JSON.parseObject(stream, JSONObject.class);
            return Result.success(ioContext -> Database.from(
                    json.getString("url"), 
                    json.getInteger("maxPoolSize")
            ));
        } catch (Exception e) {
            return Result.fail("json parse error, check classpath:/easyio/rxjdbc/database.yml");
        }
    }

    @Override
    public String resourceName() {
        return "database";
    }
}
