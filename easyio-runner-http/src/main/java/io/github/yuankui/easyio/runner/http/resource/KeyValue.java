package io.github.yuankui.easyio.runner.http.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue {
    private String key;
    private String value;
}
