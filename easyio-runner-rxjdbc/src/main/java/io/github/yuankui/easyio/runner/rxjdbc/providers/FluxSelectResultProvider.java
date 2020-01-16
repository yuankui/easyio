package io.github.yuankui.easyio.runner.rxjdbc.providers;

import com.alibaba.fastjson.JSONObject;
import io.github.yuankui.easyio.generic2.*;
import io.github.yuankui.easyio.runner.rxjdbc.RxjdbcProvider;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

@RxjdbcProvider
@Component
public class FluxSelectResultProvider implements Provider<Flux<Object>> {
    @Override
    public Result<Flux<Object>> init(Method method, InitContext context) {
        // return type
        Type type;
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            if (parameterizedType.getRawType() != Flux.class) {
                return Result.fail("return type not Flux");
            }
            type = parameterizedType.getActualTypeArguments()[0];
        } else {
            return Result.fail("return type not Flux<T>");
        }

        Collection<Resource<Flowable<JSONObject>>> selectResult = context.getResources("selectResult");
        if (selectResult.isEmpty()) {
            return Result.fail("missing @R-selectResult");
        }

        Callable<Flowable<JSONObject>> callable = selectResult.stream()
                .reduce((o1, o2) -> o2)
                .get()
                .getCallable();

        return Result.success(ioContext -> {
            Flowable<Object> flowable = callable.call(ioContext)
                    .map(json -> json.toJavaObject(type));
            return Flux.from(flowable);
        });
    }


    @Override
    public String resourceName() {
        return "result";
    }
}
