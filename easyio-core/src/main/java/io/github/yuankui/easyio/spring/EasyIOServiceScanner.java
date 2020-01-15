package io.github.yuankui.easyio.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Component
public class EasyIOServiceScanner extends ApplicationObjectSupport
        implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry)
            throws BeansException {

        Map<String, Object> beansWithAnnotation =
                getApplicationContext().getBeansWithAnnotation(EasyIOScan.class);

        beansWithAnnotation.values().stream()
                .map(Object::getClass)
                .map(c -> c.getAnnotation(EasyIOScan.class))
                .flatMap(easyIOScan -> Arrays.stream(easyIOScan.value()))
                .map(path -> new RunWithScanner(beanDefinitionRegistry).getRunWiths(path))
                .flatMap(Collection::stream)
                .forEach(clazz -> {
                    AbstractBeanDefinition definition = BeanDefinitionBuilder
                            .rootBeanDefinition(ServiceFactory.class)
                            .setScope("singleton")
                            .addPropertyValue("objectType", clazz)
                            .getBeanDefinition();
                    beanDefinitionRegistry.registerBeanDefinition(clazz.getName(), definition);
                });
    }

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
