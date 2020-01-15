package io.github.yuankui.easyio.generic2;

import java.util.List;

public interface ProcessContext {
    <T> T getResource(Class<? extends Resource<T>> resourceType);
    <T> List<T> getResources(Class<? extends Resource<T>> resourceType);
}
