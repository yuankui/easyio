package io.github.yuankui.easyio.runner.rxjdbc.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.atomic.AtomicInteger;

public class MapWithIndex<T> implements ObservableTransformer<T, Pair<Integer, T>> {
    @Override
    public ObservableSource<Pair<Integer, T>> apply(Observable<T> observable) {
        AtomicInteger i = new AtomicInteger();
        return observer -> {
            observable.map(item -> Pair.of(i.getAndIncrement(), item)).subscribe(observer);
        };
    }
}
