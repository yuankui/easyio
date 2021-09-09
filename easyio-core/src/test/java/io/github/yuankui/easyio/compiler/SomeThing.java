package io.github.yuankui.easyio.compiler;

import java.util.concurrent.Callable;

public class SomeThing implements ReturnCallable {
    @Override
    public Callable<String> get() {
        return () -> {
            return "hello";
        };
    }
}
