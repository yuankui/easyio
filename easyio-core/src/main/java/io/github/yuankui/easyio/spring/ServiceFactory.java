package io.github.yuankui.easyio.spring;

import io.github.yuankui.easyio.core.EasyIO;
import io.github.yuankui.easyio.core.Runner;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ServiceFactory implements FactoryBean, ApplicationContextAware {

    @Setter
    private Class<?> objectType;
    private ApplicationContext context;

    @Override
    public Object getObject() throws Exception {
        RunWith runWith = objectType.getAnnotation(RunWith.class);
        Runner runner = context.getBean(runWith.value());
        return EasyIO.create(this.objectType, runner);
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
