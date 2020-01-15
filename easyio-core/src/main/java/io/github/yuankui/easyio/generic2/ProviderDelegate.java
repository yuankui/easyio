package io.github.yuankui.easyio.generic2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ProviderDelegate<T> implements Provider<T>{
    private Provider<T> provider;
    private Type type;
    private Exception e;

    public ProviderDelegate(Provider<T> provider) {
        this.provider = provider;
    }
    

    public Type type() {
        if (this.type == null) {
            try {
                Method method = this.provider.getClass().getMethod("provide", ProcessContext.class);
                this.type = method.getGenericReturnType();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        
        return type;
    }
    
    public void setException(Exception e) {
        this.e = e;
    }
    
    @Override
    public Result<T> init(Method method, InitContext context) {
        return provider.init(method, context);
    }

    @Override
    public String resourceName() {
        return null;
    }
}
