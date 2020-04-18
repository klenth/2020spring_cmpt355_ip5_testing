package cmpt355.project.ast.node.expression;

import static cmpt355.project.language.PrimitiveType.*;

import cmpt355.project.DataTypeException;
import cmpt355.project.InternalCompilerException;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.DataType;
import cmpt355.project.language.LocalVariable;
import cmpt355.project.language.PrimitiveType;

import java.util.List;

public class LocalVariableReference extends LValue {

    private LocalVariable variable;

    public LocalVariableReference(LocalVariable variable) {
        setVariable(variable);
    }

    public LocalVariable getVariable() {
        return variable;
    }

    public void setVariable(LocalVariable variable) {
        this.variable = reparentNonNull(variable);
    }

    @Override
    public DataType getType() {
        return variable.getType();
    }

    @Override
    public void validateType() {
        // Nothing to do
    }

    @Override
    public String toString() {
        return "variable reference";
    }

    @Override
    public List<LocalVariable> children() {
        return List.of(variable);
    }

}
