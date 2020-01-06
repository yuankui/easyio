package io.github.yuankui.easyio.generic2;

public interface InitContext {
    <T> void addDependency(String name, TypeHint<T> resourceType) throws DependencyMissingException;
}
