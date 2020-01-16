package io.github.yuankui.easyio.runner.rxjdbc.providers;

import io.github.yuankui.easyio.generic2.*;
import io.github.yuankui.easyio.runner.rxjdbc.RxjdbcProvider;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.lang.reflect.Method;
import java.util.Collection;

@RxjdbcProvider
@Component
public class FluxSelectResultProvider implements Provider<Object> {
    @Override
    public Result<Object> init(Method method, InitContext context) {
        if (method.getReturnType() != Flux.class) {
            return Result.fail("return type not Flux");
        }
        
        Collection<Resource<Flowable>> r = context.getResources("result");
        if (r.isEmpty()) {
            return Result.fail("need another result");
        }

        Callable<Flowable> callable = r.stream()
                .reduce((o1, o2) -> o2)
                .get()
                .getCallable();

        return Result.success(ioContext -> Flux.from(callable.call(ioContext)));
    }

    @Override
    public CompareResult compare(Provider<Object> competitor) {
        return CompareResult.better(1, "I use his");
    }

    @Override
    public String resourceName() {
        return "result";
    }
}
