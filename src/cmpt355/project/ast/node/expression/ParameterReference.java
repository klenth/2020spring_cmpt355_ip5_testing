package cmpt355.project.ast.node.expression;

import cmpt355.project.DataTypeException;
import cmpt355.project.InternalCompilerException;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.DataType;
import cmpt355.project.language.Parameter;
import cmpt355.project.language.PrimitiveType;

import java.util.List;

import static cmpt355.project.language.PrimitiveType.DOUBLE;
import static cmpt355.project.language.PrimitiveType.LONG;

public class ParameterReference extends LValue {

    private Parameter variable;

    public ParameterReference(Parameter variable) {
        setVariable(variable);
    }

    public Parameter getVariable() {
        return variable;
    }

    public void setVariable(Parameter variable) {
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
    public List<Parameter> children() {
        return List.of(variable);
    }

}
