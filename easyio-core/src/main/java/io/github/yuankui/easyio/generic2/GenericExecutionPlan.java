package io.github.yuankui.easyio.generic2;

import io.github.yuankui.easyio.context.IOContext;
import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.core.ProviderContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class GenericExecutionPlan implements ExecutionPlan {
    private final List<Provider<?>> providers;

    public GenericExecutionPlan(List<Provider<?>> providerList) {
        this.providers = providerList;
    }

    @Override
    public void init(Method method, ProviderContext providerContext) {

        int n = providers.size();
        for (int i = 0; i < n; i++) {
            for (Provider<?> provider : providers) {
                try {
                    provider.init(method, new InitContext() {
                        @Override
                        public <T> void addDependency(String name, TypeHint<T> resourceType) {
                            
                        }
                    });
                } catch (DependencyMissingException e) {
                    
                }
            }
        }
    }

    @Override
    public Object execute(IOContext IOContext) throws IOException {
        return null;
    }
}
