package io.github.yuankui.easyio.generic.param;

import io.github.yuankui.easyio.generic.resource.extractor.ExtractorFactory;
import io.github.yuankui.easyio.generic.resource.extractor.ResourceExtractor;
import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.manager.ResourceManager;
import io.github.yuankui.easyio.generic.provider.Depend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 支持形如List<T>的参数
 */
@Slf4j
@Prototype
public class DependManyParamInjector implements ParamInjector {
    private Depend depend;

    @Autowired
    private ExtractorFactory extractorFactory;
    private ResourceExtractor extractor;
    private Type type;
    private Type innerType;
    
    @Override
    public boolean init(Depend depend, Type type) {
        if (!depend.many()) {
            return false;
        }
        
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        
        if (((ParameterizedType) type).getRawType() != List.class) {
            // 仅支持List
            return false;
        }

        this.innerType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.extractor = extractorFactory.create(innerType);
        this.depend = depend;
        this.type = type;
        return true;
    }

    @Override
    public Object parseParam(ResourceManager resourceManager) {
        List<Caller> resources = resourceManager.getResources(depend.value());

        List<Object> extractResources = resources.stream()
                .map(r -> {
                    try {
                        return extractor.extract(r);
                    } catch (Exception e) {
                        log.info("extract failed", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (extractResources.size() < depend.least()) {
            throw new RuntimeException("too little resource, depend:" + depend + ", type:" + type);
        }

        return extractResources;
    }
}
