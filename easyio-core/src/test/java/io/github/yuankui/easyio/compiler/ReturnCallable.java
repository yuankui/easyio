package io.github.yuankui.easyio.compiler;

import java.util.concurrent.Callable;

public interface ReturnCallable {
    Callable<String> get();
}
