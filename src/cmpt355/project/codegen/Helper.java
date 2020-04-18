package cmpt355.project.codegen;

import static cmpt355.project.jvm.Opcode.*;
import static cmpt355.project.language.PrimitiveType.*;

import cmpt355.project.InternalCompilerException;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.jvm.Opcode;
import cmpt355.project.language.PrimitiveType;

import java.util.HashMap;
import java.util.Map;

public final class Helper {

    private static final Map<String, Opcode> PRIMITIVE_CONVERSION_OPCODES = new HashMap<>();

    static {
        for (var opcode : new Opcode[] {
                i2l, i2f, i2d,
                l2i, l2f, l2d,
                f2i, f2l, f2d,
                d2i, d2l, d2f
        }) {
            String s = opcode.getMnemonic();
            String key = "" + s.charAt(0) + s.charAt(2);
            PRIMITIVE_CONVERSION_OPCODES.put(key, opcode);
        }
    }

    // Cannot instantiate
    private Helper() {}

    private static char primitiveNumericCode(PrimitiveType p) {
        return (p.isEffectivelyInt()) ? 'i'
                : (p == LONG) ? 'l'
                : (p == FLOAT) ? 'f'
                : (p == DOUBLE) ? 'd'
                : '\0';
    }

    public static void convertTypes(MethodContext context, PrimitiveType from, PrimitiveType to) {
        // Nothing to be done
        if (from == to
                || from.isEffectivelyInt() && to.isEffectivelyInt())
            return;

        String convCode = "" + primitiveNumericCode(from) + primitiveNumericCode(to);
        Opcode opcode = PRIMITIVE_CONVERSION_OPCODES.getOrDefault(convCode, null);
        if (opcode == null)
            throw new InternalCompilerException("No instruction for converting " + from + " to " + to);
        context.addCode(new Instruction(opcode));
    }
}
