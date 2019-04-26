package io.github.yuankui.easyio.generic.manager;

import io.github.yuankui.easyio.generic.Caller;

import java.util.List;

public interface ResourceManager {
    List<Caller> getResources(String name);
}
