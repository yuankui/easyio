package io.github.yuankui.easyio.generic2;

import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.core.Runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class GenericRunner implements Runner {
    private List<Provider> providerList = new ArrayList<>();
    
    public List<Provider> getProviderList() {
        return this.providerList;
    }
    @Override
    public ExecutionPlan create(Method method) {
        GenericExecutionPlan plan = new GenericExecutionPlan();
        plan.init(method, this::getProviderList);
        return plan;
    }
}
