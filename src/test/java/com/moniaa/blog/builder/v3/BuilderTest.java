package com.moniaa.blog.builder.v3;

import org.joor.CompileOptions;
import org.joor.Reflect;
import org.junit.Test;

public class BuilderTest {

    @Test
    public void testNewPerson() {
        BuilderProcessor p = new BuilderProcessor();
        Reflect.compile(
                "com.moniaa.blog.builder.v3.Person",
                "package com.moniaa.blog.builder.v3;\n" +
                        "\n" +
                        "@Builder\n" +
                        "public class Person {\n" +
                        "    private String firstName;\n" +
                        "    private String lastName;\n" +
                        "}\n",
                new CompileOptions().processors(p)
        ).create().get();

    }

}
