package io.github.yuankui.easyio.generic2;

import java.util.Collection;

public interface InitContext {
    <T> void addDependency(Resource<T> provider);
    <T> Collection<Resource<T>> getResources(String name);
    boolean lastChance();
}
