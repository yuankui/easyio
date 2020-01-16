package io.github.yuankui.easyio.runner.rxjdbc.providers;

import com.alibaba.fastjson.JSONObject;
import io.github.yuankui.easyio.generic2.*;
import io.github.yuankui.easyio.runner.rxjdbc.RxjdbcProvider;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

@RxjdbcProvider
@Component
public class FlowableSelectResultProvider implements Provider<Flowable<Object>> {
    @Override
    public Result<Flowable<Object>> init(Method method, InitContext context) {
        // return type
        Type type;
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            if (parameterizedType.getRawType() != Flowable.class) {
                return Result.fail("return type not Flowable");
            }
            type = parameterizedType.getActualTypeArguments()[0];
        } else {
            return Result.fail("return type not Flowable<T>");
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
            return callable.call(ioContext)
                    .map(json -> json.toJavaObject(type));
        });
    }


    @Override
    public String resourceName() {
        return "result";
    }
}
