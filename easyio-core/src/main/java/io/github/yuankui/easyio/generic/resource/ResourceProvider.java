package io.github.yuankui.easyio.generic.resource;

import io.github.yuankui.easyio.generic.Caller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 描述一个资源提供者的状态
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResourceProvider {
    /**
     * 资源名
     */
    private String name;

    /**
     * 提供者名
     */
    private String provider;

    /**
     * 资源状态
     */
    private Status status;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 是否被选择
     */
    private boolean selected = false;

    /**
     * 资源
     */
    private Caller caller;

    /**
     * 依赖
     */
    private Map<String, List<ResourceProvider>> dependencies;

    @Override
    public String toString() {
        return "ResourceProvider{" +
                "provider='" + provider + '\'' +
                ", status=" + status +
                ", selected=" + selected +
                '}';
    }
}
