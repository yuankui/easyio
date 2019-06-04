package io.github.yuankui.easyio.generic.manager;

import io.github.yuankui.easyio.generic.resource.ResourceProvider;

import java.util.List;

public interface ResourceManager {
    List<ResourceProvider> getResources(String name);
}
