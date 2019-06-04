package io.github.yuankui.easyio.generic.param;

import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.resource.ResourceProvider;

import java.lang.reflect.Type;
import java.util.List;

public interface ParamInjector {
    boolean init(Depend depend, Type type);

    Object parseParam(List<ResourceProvider> resources);
}
