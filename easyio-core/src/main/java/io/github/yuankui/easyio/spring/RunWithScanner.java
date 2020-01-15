package io.github.yuankui.easyio.spring;

import io.github.yuankui.easyio.spring.RunWith;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * not thread-safe
 */
@Slf4j
public class RunWithScanner extends ClassPathBeanDefinitionScanner {

    private final List<Class> runWiths;

    public RunWithScanner(BeanDefinitionRegistry registry) {
        super(registry);
        this.runWiths = new ArrayList<>();
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        String className = metadataReader.getClassMetadata().getClassName();
        try {
            Class<?> beanClass = Class.forName(className);
            if (beanClass.getAnnotation(RunWith.class) != null) {
                this.runWiths.add(beanClass);
            }
        } catch (Throwable e) {
            log.warn("load class failed:{}", className, e);
            return false;
        }

        return super.isCandidateComponent(metadataReader);
    }

    public List<Class> getRunWiths(String path) {
        runWiths.clear();
        this.scan(path);
        return runWiths;
    }
}
