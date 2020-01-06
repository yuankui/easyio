package io.github.yuankui.easyio.generic2;

public class DependencyMissingException extends Exception {
    public DependencyMissingException() {
    }

    public DependencyMissingException(String message) {
        super(message);
    }

    public DependencyMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
