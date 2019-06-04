package io.github.yuankui.easyio.generic.param;

import io.github.yuankui.easyio.generic.Caller;
import io.github.yuankui.easyio.generic.Prototype;
import io.github.yuankui.easyio.generic.manager.ResourceManager;
import io.github.yuankui.easyio.generic.provider.Depend;
import io.github.yuankui.easyio.generic.resource.ResourceProvider;
import io.github.yuankui.easyio.generic.resource.Status;
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
    public Object parseParam(List<ResourceProvider> resources) {
        Optional<Object> resource = resources.stream()
                .filter(r -> r.getStatus() == Status.OK)
                .reduce((o, o2) -> o2) // 返回最后一个，优先级最高
                .map(r -> {
                    r.setSelected(true);
                    return extractor.extract(r.getCaller());
                }); 

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
