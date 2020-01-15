package io.github.yuankui.easyio;

import io.github.yuankui.easyio.core.EasyIO;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.Setter;

public class ServiceFactory implements FactoryBean, ApplicationContextAware {

    @Setter
    private Class<?> objectType;
    private ApplicationContext context;

    @Override
    public Object getObject() throws Exception {
        EasyIO easyIO = context.getBean(EasyIO.class);
        return easyIO.create(this.objectType, null);
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
