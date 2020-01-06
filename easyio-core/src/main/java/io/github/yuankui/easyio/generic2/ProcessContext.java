package io.github.yuankui.easyio.generic2;

import java.util.List;

public interface ProcessContext {
    <T> T getResource(String name, Class<T> type);
    <T> List<T> getResources(String name, Class<T> type);
    
    <T> T getResource(String name, TypeHint<T> type);
    <T> List<T> getResources(String name, TypeHint<T> type);
    
}
