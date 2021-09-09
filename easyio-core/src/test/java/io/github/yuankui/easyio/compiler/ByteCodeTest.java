package io.github.yuankui.easyio.compiler;

import io.github.yuankui.easyio.context.IOContextImpl;
import io.github.yuankui.easyio.generic2.Callable;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.junit.Test;

public class ByteCodeTest {
    @Test
    public void test() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.makeClass("MyCall");
        clazz.addInterface(pool.getCtClass(Callable.class.getName()));

        clazz.addMethod(CtMethod.make("public Object call(io.github.yuankui.easyio.context.IOContext ioContext) {\n" +
            "\treturn \"hello kitty\";\n" +
            "}", clazz));

        Callable callable = (Callable) clazz.toClass().newInstance();
        System.out.println("callable.call(null) = " + callable.call(null));
    }

    @Test
    public void test2() throws NotFoundException, CannotCompileException {

        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.getCtClass("io.github.yuankui.easyio.compiler.SayHello");
        CtMethod say = clazz.getDeclaredMethod("say");


        say.setBody("System.out.println(111);");

        clazz.toClass();
        SayHello sayHello = new SayHello();

        sayHello.say(); //print 111, not hello

        System.out.println("say = " + say);
    }
}
