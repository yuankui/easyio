package io.github.yuankui.easyio.generic.provider;

import io.github.yuankui.easyio.generic.MethodAdapter;

/**
 * 资源提供者接口
 *
 * 实现类需要再实现一个 {@link Provide} 方法
 * 方法的参数需要通过 {@link Depend} 依赖
 */
public interface Provider {
    /**
     * 组件竞争，来排个序
     * @param other
     * @return
     */
    default int race(Provider other) {
        return 0;
    }

    default void init(MethodAdapter methodAdapter) {
        // do nothing
    }
    
    // provide() with Annotation
}
