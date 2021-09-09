package io.github.yuankui.easyio.compiler;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class JavaFileObjectFromString extends SimpleJavaFileObject {
    //Uri uri; //uri为源码的资源定位符, 在父类中定义

    /**
     * Java源文件的内容
     */
    final String code;

    /**
     * @param className 类的完整名称
     * @param code
     */
    public JavaFileObjectFromString(String className, String code) {
        //uri为类的资源定位符号, 如: com/stone/generate/Hello.java
        super(URI.create(className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        //uri值为com.stone.generate.Hello时, 下面注释的代码导致异常 => com.stone.generate.Hello:1: 错误: 类Hello是公共的, 应在名为 Hello.java 的文件中声明
//        super(URI.create(className), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}