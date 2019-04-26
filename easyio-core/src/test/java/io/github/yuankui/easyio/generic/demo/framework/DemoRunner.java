package io.github.yuankui.easyio.generic.demo.framework;

import io.github.yuankui.easyio.generic.filter.InterfaceProviderFilter;
import io.github.yuankui.easyio.generic.filter.ProviderFilter;
import io.github.yuankui.easyio.generic.GenericRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner extends GenericRunner {
    @Override
    public ProviderFilter getProviderFilter() {
        return new InterfaceProviderFilter(DemoProvider.class);
    }
}
