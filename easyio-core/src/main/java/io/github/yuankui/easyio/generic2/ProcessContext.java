package io.github.yuankui.easyio.generic2;

import java.util.List;

public interface ProcessContext {
    <T extends ResourceType> T getResource(String name, Class<T> type);
    <T extends ResourceType> List<T> getResources(String name, Class<T> type);
}
