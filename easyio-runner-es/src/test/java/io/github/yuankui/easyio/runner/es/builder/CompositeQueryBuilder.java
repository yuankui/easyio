package io.github.yuankui.easyio.runner.es.builder;

import java.util.ArrayList;
import java.util.List;

public interface CompositeQueryBuilder {
    List<String> names = new ArrayList<>();
    default void and(String name, int age) {
        names.add(name + ":" + age);
    }
}
