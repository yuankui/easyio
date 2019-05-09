package io.github.yuankui.easyio.generic;

/**
 * 插件抛出的异常，当是本异常的时候，表示插件发生了严重异常，极有可能是用户配置错误，如果不处理将引起不预期的结果。
 * 
 * 例如用户明明配置了 HeaderSupplier，但是根据提供的Supplier名找不到对应的spring bean，这种异常应该及时报给用户，而不是藏着。
 */
public class FatalException extends RuntimeException {
    public FatalException(String message) {
        super(message);
    }

    public FatalException(String message, Throwable cause) {
        super(message, cause);
    }
}
