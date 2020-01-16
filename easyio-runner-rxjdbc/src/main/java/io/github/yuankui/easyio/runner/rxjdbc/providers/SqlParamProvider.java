package io.github.yuankui.easyio.runner.rxjdbc.providers;

import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Result;
import io.github.yuankui.easyio.runner.rxjdbc.RxsqlProvider;
import io.github.yuankui.easyio.runner.rxjdbc.annotations.Param;
import io.github.yuankui.easyio.runner.rxjdbc.rx.Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@RxsqlProvider
@Component
public class SqlParamProvider implements Provider<List<Object>> {
    @Override
    public Result<List<Object>> init(Method method, InitContext context) {
        List<Integer> index = Utils.paramAnnotations(method)
                .filter(p -> p.getValue() instanceof Param)
                .map(Pair::getKey)
                .toList()
                .blockingGet();

        if (index.isEmpty()) {
            return Result.fail("missing @Param");
        }

        return Result.success(ioContext -> {
            return index.stream()
                    .map(i -> ioContext.getArgs()[i])
                    .collect(Collectors.toList());
        });
    }

    @Override
    public String resourceName() {
        return "sqlParam";
    }
    
}
