package io.github.yuankui.easyio.runner.http.providers;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.FatalException;
import io.github.yuankui.easyio.generic.MethodAdapter;
import io.github.yuankui.easyio.generic.provider.Provide;
import io.github.yuankui.easyio.generic.provider.Provider;
import io.github.yuankui.easyio.runner.http.HttpProvider;
import io.github.yuankui.easyio.runner.http.annotation.HeaderSupply;
import io.github.yuankui.easyio.runner.http.header.HeaderSupplier;
import io.github.yuankui.easyio.runner.http.resource.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 由HeaderSupplier提供公用的header，比如需要权限验证的一些header，cookie等
 * 
 * {@link io.github.yuankui.easyio.runner.http.header.HeaderSupplier} 
 * {@link io.github.yuankui.easyio.runner.http.annotation.HeaderSupply}
 */
@HttpProvider
public class HeaderSupplierProvider implements Provider {

    private MethodAdapter methodAdapter;
    
    @Autowired
    private ApplicationContext context;
    private HeaderSupplier supplier;

    @Override
    public void init(MethodAdapter methodAdapter) {
        this.methodAdapter = methodAdapter;
    }

    @Provide("header")
    public Caller<List<KeyValue>> provide() {
        HeaderSupply headerSupply = methodAdapter.getAnnotation(HeaderSupply.class);
        if (headerSupply == null) {
            throw new RuntimeException("no @HeaderSupply found in method & class signature");
        }

        Class<? extends HeaderSupplier> supplierClass = headerSupply.value();
        try {
            this.supplier = context.getBean(supplierClass);
        } catch (Exception e) {
            throw new FatalException("could not find supplier bean of type:" + supplierClass.getClass(), e);
        }
        return ioContext -> supplier.supply();
    }
}
