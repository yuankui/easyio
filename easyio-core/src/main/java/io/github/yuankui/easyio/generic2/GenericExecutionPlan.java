package io.github.yuankui.easyio.generic2;

import io.github.yuankui.easyio.context.IOContext;
import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.core.ProviderContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class GenericExecutionPlan implements ExecutionPlan {
    private final List<Provider<?>> providers;

    public GenericExecutionPlan(List<Provider<?>> providerList) {
        this.providers = providerList;
    }

    @Override
    public void init(Method method, ProviderContext providerContext) {

        Map<String, PriorityQueue<ProviderWrapper>> providers = this.providers.stream()
                .map(ProviderWrapper::new)
                .collect(Collectors.groupingBy(ProviderWrapper::name, Collectors.toCollection(() -> {
                    return new PriorityQueue<ProviderWrapper>(Comparator.comparingDouble(Provider::order));
                })));

        Map<String, List<ProviderWrapper>> initialized = new LinkedHashMap<>();
        
        for (int i = 0; i < this.providers.size(); i++) {
            for (Map.Entry<String, PriorityQueue<ProviderWrapper>> entry : providers.entrySet()) {
                String name = entry.getKey();
                PriorityQueue<ProviderWrapper> list = entry.getValue();
                for (ProviderWrapper provider : list) {
                    try {

                        List<String> missing = new ArrayList<>();
                        provider.init(method, new InitContext() {
                            @Override
                            public <T> void addDependency(String name, TypeHint<T> resourceType) {
                                if (!initialized.containsKey(name)) {
                                    missing.add(name);
                                }
    
                                boolean typeMatch = initialized.get(name).stream()
                                        .allMatch(i -> i.type() == resourceType.getType());
                                
                                if (!typeMatch) {
                                    throw new RuntimeException("type not match," + resourceType + " vs " + initialized.get(name));
                                }
                            }
                        });

                        List<ProviderWrapper> l = initialized.get(name);
                        if (l == null) {
                            l = new ArrayList<>();
                        }
                        l.add(provider);
                        initialized.put(name, l);
                    } catch (DependencyMissingException e) {
                        provider.setException(e);
                    }
                }
            }
        }
    }

    @Override
    public Object execute(IOContext IOContext) throws IOException {
        return null;
    }
}
