package io.github.yuankui.easyio.runner.es.resource;

import lombok.Data;

@Data
public class Page {
    private int from;
    private int size;

    private Page(int from, int size) {
        this.from = from;
        this.size = size;
    }

    public static Page from(int from, int size) {
        return new Page(from, size);
    }
    
    public static Page page(int page, int size) {
        return new Page(page * size, size);
    }
}
