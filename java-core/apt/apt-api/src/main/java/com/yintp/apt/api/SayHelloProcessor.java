package com.yintp.apt.api;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * 编译时注解处理器：APT可以用来在编译时扫描和处理注解。通过APT可以获取到注解和被注解对象的相关信息，在拿到这些信息后我们可以根据需求来自动的生成一些代码。
 * 应该场景：
 * - lombok
 * - mapstruct
 * 注解处理器，对于每个标注了此注解的源文件，编译器都会调用process方法
 * 需指定当前处理器能够处理的注解以及源码级别，可通过注解@SupportedAnnotationTypes、@SupportedSourceVersion或重写getSupportedAnnotationTypes()、getSupportedSourceVersion()方法
 *
 * @author yintp
 */
@SupportedAnnotationTypes({"com.yintp.apt.api.SayHello"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SayHelloProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(SayHello.class)) {
            TypeElement typeElem = (TypeElement) element;
            String typeName = typeElem.getQualifiedName().toString();
            Filer filer = processingEnv.getFiler();
            try (Writer sw = filer.createSourceFile(typeName + "Hello").openWriter()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating " + typeName + "Hello source code");
                int lastIndex = typeName.lastIndexOf('.');
                MethodSpec helloMethod = MethodSpec.methodBuilder("sayHello")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addStatement("$T.out.println($S)", System.class, "Hello, " + typeName)
                    .build();
                TypeSpec helloType = TypeSpec.classBuilder(typeName.substring(lastIndex + 1) + "Hello")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(helloMethod)
                    .build();
                JavaFile helloFile = JavaFile.builder(typeName.substring(0, lastIndex), helloType).build();
                helloFile.writeTo(sw);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        return true;
    }
}
