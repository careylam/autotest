package com.moniaa.blog.builder.v3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.code.Symbol;

@SupportedAnnotationTypes("com.moniaa.blog.builder.v3.Builder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.stream().forEach(element -> writeBuilderFile(element));
        }
        return true;
    }

    private void writeBuilderFile(Element builderType) {
        List<Symbol.VarSymbol> properties = new ArrayList<>();
        builderType.getEnclosedElements().forEach(
                enclosed -> {
                    if (enclosed instanceof Symbol.VarSymbol) {
                        properties.add((Symbol.VarSymbol) enclosed);
                    }
                });
        try {
            writeBuilderFile(((Symbol.ClassSymbol) builderType).className(), properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBuilderFile(String className, List<Symbol.VarSymbol> properties)  throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(simpleClassName);
        try (PrintWriter out = new PrintWriter(System.out)) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            declareClass(out, simpleClassName);
            outputProperties(out, properties, 1);
            outputContructor(out, simpleClassName, properties);
            outputNewBuilderMethod(out);
            declassBuilderImplClass(out, simpleClassName);
            declareBuilder(out, simpleClassName, properties);
            closeClass(out, 0);
        }
    }

    private void declareClass(PrintWriter out, String simpleClassName) {
        out.print("public class ");
        out.print(simpleClassName);
        out.println(" {");
        out.println();
    }

    private void outputProperties(PrintWriter out, List<Symbol.VarSymbol> properties, int indentation) {
        String indent = indent(indentation);
        for(Symbol.VarSymbol property : properties) {
            out.println(String.format("%sprivate %s %s;",
                    indent, property.asType(), property.getQualifiedName().toString()));
        }
        out.println();
    }

    private void outputContructor(PrintWriter out, String simpleClassName, List<Symbol.VarSymbol> properties) {
        out.println(String.format("    protected %s(Builder<?, ?> builder) {", simpleClassName));
        for(Symbol.VarSymbol property : properties) {
            String propertyName = property.getQualifiedName().toString();
            out.println(String.format(
                    "        this.%s = builder.%s;", propertyName, propertyName));
        }
        out.println("    }\n");
    }

    private void outputNewBuilderMethod(PrintWriter out) {
        out.println("    public static Builder<?, ?> newBuilder() {");
        out.println("        return new BuilderImpl();");
        out.println("    }\n");
    }

    private void declassBuilderImplClass(PrintWriter out, String simpleClassName) {
        out.println(String.format(
                "    private static final class BuilderImpl extends Builder<%s, BuilderImpl>",
                simpleClassName));
        out.println("        private BuilderImpl() {");
        out.println("        }");
        out.println();
        out.println("        protected BuilderImpl self() {");
        out.println("            return this;");
        out.println("        }");
        out.println();
        out.println(String.format(
                "        public %s build() {", simpleClassName));
        out.println(String.format(
                "            return new %s(this);", simpleClassName));
        out.println("        }");
        out.println("    }\n");
    }

    private void declareBuilder(PrintWriter out, String simpleClassName, List<Symbol.VarSymbol> properties) {
        out.println(String.format(
                "    public abstract static class Builder<T extends %s, B extends Builder<T, B>> {",
                simpleClassName));
        outputProperties(out, properties, 2);
        out.println("        protected Builder() {");
        out.println("        }\n");
        out.println("        protected abstract B self();\n");
        out.println("        public abstract T build();\n");
        outputSetters(out, properties);
        closeClass(out, 1);
    }

    private void outputSetters(PrintWriter out, List<Symbol.VarSymbol> properties) {
        for(Symbol.VarSymbol property : properties) {
            String propertyName = property.getQualifiedName().toString();
            out.println(String.format(
                    "        public B %s(String %s) {", propertyName, propertyName));
            out.println(String.format(
                    "            this.%s = %s;", propertyName, propertyName));
            out.println("            return this.self();");
            out.println("        }\n");
        }
    }

    private void closeClass(PrintWriter out, int indentation) {
        out.println(String.format("%s}", indent(indentation)));
    }

    private String indent(int indentation) {
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i< indentation; i++) {
            buf.append("    ");
        }
        return buf.toString();
    }
}