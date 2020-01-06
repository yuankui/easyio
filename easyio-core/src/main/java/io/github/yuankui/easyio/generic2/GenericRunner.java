package io.github.yuankui.easyio.generic2;

import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.core.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GenericRunner implements Runner {
    private List<Provider<?>> providerList = new ArrayList<>();
    
    @Autowired
    private Map<String, Provider<?>> providerMap;
    
    @PostConstruct
    public void init() {
        if (providerMap != null) {
            providerList.addAll(providerMap.values());
        }
    }
    
    public void addProvider(Provider<?> provider) {
        this.providerList.add(provider);
    }
    
    @Override
    public ExecutionPlan create(Method method) {
        return new GenericExecutionPlan(this.providerList);
    }
}
