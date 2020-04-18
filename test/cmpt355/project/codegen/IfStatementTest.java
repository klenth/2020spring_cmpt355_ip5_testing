package cmpt355.project.codegen;

import cmpt355.project.SyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IfStatementTest extends CodegenTest {

    private static final String CLASS_CODE = """
            package cmpt355.test;

            public class IfStatements {
                public static int testIf() {
                    int x = 0;

                    if (true) {
                        ++x;
                    }

                    if (true) {
                        ++x;
                    }

                    if (false) {
                        ++x;
                    }

                    return x;
                }


            }
            """;

    private static Class<?> clazz;
    private Object instance;

    @BeforeAll
    static void compile() throws IOException, SyntaxException, ReflectiveOperationException {
        clazz = compile(CLASS_CODE);
    }

    @BeforeEach
    void instantiate() throws ReflectiveOperationException {
        instance = clazz.getConstructor().newInstance();
    }

    @Test
    void simpleIf() throws ReflectiveOperationException {
        var method = clazz.getMethod("testIf");
        int result = (Integer)method.invoke(instance);
        org.junit.jupiter.api.Assertions.assertEquals(2, result);
    }
}
