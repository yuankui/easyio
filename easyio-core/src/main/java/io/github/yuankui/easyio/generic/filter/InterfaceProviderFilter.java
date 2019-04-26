package io.github.yuankui.easyio.generic.filter;

public class InterfaceProviderFilter implements ProviderFilter {
    private Class intf;

    public InterfaceProviderFilter(Class intf) {
        this.intf = intf;
    }

    @Override
    public boolean filter(Class clazz) {
        return this.intf.isAssignableFrom(clazz);
    }
}
