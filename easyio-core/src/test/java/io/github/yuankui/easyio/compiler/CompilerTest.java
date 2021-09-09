package io.github.yuankui.easyio.compiler;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 参考博客
 * https://www.soulmachine.me/blog/2015/07/22/compile-and-run-java-source-code-in-memory/
 */
public class CompilerTest {
    private String getClassPath() {
        return System.getProperty("java.class.path");
    }

    @Test
    public void test() throws Throwable {
        Class a = ReturnCallable.class;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null,null,null);

        //language=JAVA
        String code = "package test;\n" +
                "import java.util.concurrent.Callable;\n" +
                "import io.github.yuankui.easyio.compiler.ReturnCallable;\n" +
                "\n" +
                "public class DynamicCallable implements ReturnCallable {\n" +
                "    @Override\n" +
                "    public Callable<String> get() {\n" +
                "        return () -> {\n" +
                "            return \"hello1\";\n" +
                "        };\n" +
                "    }\n" +
                "}\n";


        MemoryJavaFileManager memoryJavaFileManager = new MemoryJavaFileManager(fileManager);

        List<String> args = Arrays.asList("-classpath", getClassPath());
        JavaCompiler.CompilationTask task = compiler.getTask(null, memoryJavaFileManager, null, args, null,
                Arrays.asList(new JavaFileObjectFromString("DynamicCallable", code)));

        Boolean res = task.call();

        URLClassLoader classLoader = new MemoryClassLoader(memoryJavaFileManager.getClassBytes());

        System.out.println("res = " + res);
        Class<?> clazz = classLoader.loadClass("test.DynamicCallable");
        System.out.println("clazz = " + clazz);

        ReturnCallable o = (ReturnCallable) clazz.newInstance();

        Callable<String> stringCallable = o.get();



        System.out.println("stringCallable = " + stringCallable.call());
    }
}
