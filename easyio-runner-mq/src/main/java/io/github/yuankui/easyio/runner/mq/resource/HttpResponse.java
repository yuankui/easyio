package io.github.yuankui.easyio.runner.mq.resource;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class HttpResponse {
    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body = new byte[0];
}
