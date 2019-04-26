package io.github.yuankui.easyio.runner.http.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String username;
    private String website;
    private Long created;
}
