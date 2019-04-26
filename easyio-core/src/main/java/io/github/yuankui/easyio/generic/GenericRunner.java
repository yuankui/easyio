package io.github.yuankui.easyio.generic;

import io.github.yuankui.easyio.generic.filter.ProviderFilter;
import io.github.yuankui.easyio.core.ExecutionPlan;
import io.github.yuankui.easyio.core.Runner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * TODO
 *   1. 类型校验
 *   2. 依赖树打印
 */
@Component
public abstract class GenericRunner implements Runner {
    @Resource
    private ApplicationContext context;

    @Override
    public ExecutionPlan create(Method method) {
        GenericExecutionPlan plan = context.getBean(GenericExecutionPlan.class);
        plan.setProviderFilter(getProviderFilter());
        plan.init(method);
        return plan;
    }

    public abstract ProviderFilter getProviderFilter();
}
