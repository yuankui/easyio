package io.github.yuankui.easyio.generic2;

import lombok.Getter;

public class DependencyMissingException extends Exception {
    @Getter
    private String name;
    public DependencyMissingException(String name) {
        this.name = name;
    }
}
