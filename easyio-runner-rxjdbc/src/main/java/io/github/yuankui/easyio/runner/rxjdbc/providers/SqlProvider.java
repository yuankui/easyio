package io.github.yuankui.easyio.runner.rxjdbc.providers;

import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import io.github.yuankui.easyio.runner.rxjdbc.RxsqlProvider;
import io.github.yuankui.easyio.runner.rxjdbc.annotations.Sql;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@RxsqlProvider
@Component
public class SqlProvider implements Provider<String> {
    @Override
    public Result<String> init(Method method, InitContext context) {
        Sql sql = method.getAnnotation(Sql.class);
        if (sql == null) {
            return Result.fail("missing @Sql");
        }

        String value = sql.value();

        return Result.success(ioContext -> value);
    }

    @Override
    public String resourceName() {
        return "sql";
    }
}
