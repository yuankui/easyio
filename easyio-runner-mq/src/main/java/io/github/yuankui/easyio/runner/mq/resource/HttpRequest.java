package io.github.yuankui.easyio.runner.mq.resource;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HttpRequest {

    private HttpMethod httpMethod;
    private String host;
    private String path;
    private List<KeyValue> query = new ArrayList<>();
    private List<KeyValue> headers = new ArrayList<>();
    private byte[] body = new byte[0];
}
