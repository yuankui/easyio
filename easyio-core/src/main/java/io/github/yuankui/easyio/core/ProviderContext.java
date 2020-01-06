package io.github.yuankui.easyio.core;

import io.github.yuankui.easyio.generic2.Provider;

import java.util.List;

public interface ProviderContext {
    <T> Provider<T> getProvider();
    
    List<Provider> getAllProviders();
}
