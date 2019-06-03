package io.github.yuankui.easyio.generic.param;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.manager.ResourceManager;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.resource.extractor.ExtractorFactory;
import io.github.yuankui.easyio.generic.resource.extractor.ResourceExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Prototype
public class DependOneParamInjector implements ParamInjector {
    private Depend depend;

    @Autowired
    private ExtractorFactory extractorFactory;
    private ResourceExtractor extractor;
    private Type type;

    @Override
    public boolean init(Depend depend, Type type) {
        if (depend.many()) {
            return false;
        }

        this.extractor = extractorFactory.create(type);
        this.depend = depend;
        this.type = type;
        return true;
    }

    @Override
    public Object parseParam(ResourceManager resourceManager) {
        List<Caller> resources = resourceManager.getResources(depend.value());
        Optional<Object> resource = resources.stream()
                .map(r -> {
                    try {
                        return extractor.extract(r);
                    } catch (Exception e) {
                        log.info("extract failed", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .reduce((o, o2) -> o2); // 返回最后一个，优先级最高

        if (resource.isPresent()) {
            return resource.get();
        } else {
            if (depend.must()) {
                throw new RuntimeException("no depend found for resource:"
                        + depend.toString() + ", type:" + type);
            }
            return null;
        }
    }
}
