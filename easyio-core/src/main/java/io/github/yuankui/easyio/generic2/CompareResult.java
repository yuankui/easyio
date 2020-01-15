package io.github.yuankui.easyio.generic2;

import lombok.Getter;

@Getter
public class CompareResult {
    private int level;
    private String msg;

    private CompareResult(int level, String msg) {
        this.level = level;
        this.msg = msg;
    }
    
    public static CompareResult same() {
        return new CompareResult(0, "");
    }

    public static CompareResult better(int level, String msg) {
        return new CompareResult(level, msg);
    }

    public static CompareResult worse(int level, String msg) {
        return new CompareResult(level, msg);
    }
}
