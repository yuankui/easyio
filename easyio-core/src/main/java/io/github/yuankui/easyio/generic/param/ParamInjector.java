package io.github.yuankui.easyio.generic.param;

import io.github.yuankui.easyio.generic.manager.ResourceManager;
import io.github.yuankui.easyio.generic.provider.Depend;

import java.lang.reflect.Type;

public interface ParamInjector {
    boolean init(Depend depend, Type type);

    Object parseParam(ResourceManager resourceManager);
}
