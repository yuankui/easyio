package io.github.yuankui.easyio.generic2;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Result<T> {
    private boolean success;
    private String msg;
    private Callable<T> callable;


    private Result(boolean success, String msg, Callable<T> callable) {
        this.success = success;
        this.msg = msg;
        this.callable = callable;
    }

    public static <T> Result<T> success(Callable<T> callable) {
        return new Result<>(true, null, callable);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(false, msg, null);
    }
    
}
