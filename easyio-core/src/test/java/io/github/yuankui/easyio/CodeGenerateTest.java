package io.github.yuankui.easyio;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.yuankui.easyio.generic2.Callable;
import io.github.yuankui.easyio.generic2.InitContext;
import io.github.yuankui.easyio.generic2.Provider;
import io.github.yuankui.easyio.generic2.Resource;
import io.github.yuankui.easyio.generic2.Result;
import org.junit.Test;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

/**
 * 生成ResultProvider
 */
public class CodeGenerateTest {
    @Test
    public void test() throws IOException {

        MethodSpec resourceNameMethod = MethodSpec.methodBuilder("resourceName")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(String.class)
            .addStatement("return $S", "result")
            .build();

        CodeBlock ifBlock = CodeBlock.builder().beginControlFlow("if (first.isPresent())")
            .endControlFlow()
            .build();

        MethodSpec initMethod = MethodSpec.methodBuilder("init")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(Method.class, "method")
            .addParameter(InitContext.class, "context")
            .returns(ParameterizedTypeImpl.make(Result.class, new Type[]{String.class}, null))
            .addStatement("$T<$T<String>> urls = context.getResources($S)", Collection.class, Resource.class, "url")
            .addStatement("$T<$T<String>> first = urls.stream()\n" +
                "            .map(r -> r.getCallable())\n" +
                "            .findFirst()", Optional.class, Callable.class)
            .addStatement(ifBlock)
            .addStatement("return $T.fail(\"no url provided\")", Result.class)
            .build();

        TypeSpec clazz = TypeSpec.classBuilder("ResultProvider")
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ParameterizedTypeImpl.make(Provider.class, new Type[]{String.class}, null))
            .addAnnotation(ClassName.get("io.github.yuankui.easyio.runner.rxjdbc", "HttpProvider"))
            .addAnnotation(Component.class)
            .addMethod(initMethod)
            .addMethod(resourceNameMethod)
            .build();

        JavaFile file = JavaFile.builder("test", clazz)
            .build();

        file.writeTo(System.out);
    }
}
