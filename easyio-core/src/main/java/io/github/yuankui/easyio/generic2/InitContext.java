package io.github.yuankui.easyio.generic2;

public interface InitContext {
    void addDependency(String name, Class<? extends ResourceType> resourceType);
}
