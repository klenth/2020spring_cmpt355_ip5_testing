package cmpt355.project.codegen;

import cmpt355.project.jvm.InstructionAddressOperand;

public class Label extends InstructionAddressOperand {

    Label(String text) {
        super(text);
    }

    public void mark() {
        throw new UnsupportedOperationException("Cannot mark() a vanilla label");
    }
}
