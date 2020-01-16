package io.github.yuankui.easyio.runner.rxjdbc.rx;

import io.reactivex.Observable;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Utils {
    public static Observable<Pair<Integer, Annotation>> paramAnnotations(Method method) {
        return Observable.fromArray(method.getParameterAnnotations())
                .compose(new MapWithIndex<>())
                .flatMap(p -> Observable
                        .fromArray(p.getValue())
                        .map(a -> Pair.of(p.getKey(), a))
                );
    }
}
