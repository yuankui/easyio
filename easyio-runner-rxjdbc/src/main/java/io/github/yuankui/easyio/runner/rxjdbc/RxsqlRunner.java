package io.github.yuankui.easyio.runner.rxjdbc;

import io.github.yuankui.easyio.generic2.GenericRunner;
import io.github.yuankui.easyio.generic2.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RxsqlRunner extends GenericRunner {
    
    @Autowired
    private ApplicationContext context;
    
    @Override
    public List<Provider> getProviderList() {

        List<Provider> providers = context.getBeansWithAnnotation(RxsqlProvider.class)
                .values()
                .stream()
                .filter(i -> i instanceof Provider)
                .map(i -> (Provider) i)
                .collect(Collectors.toList());

        return providers;
    }
}
