package cmpt355.project.ast.node.statement;

import cmpt355.project.DataTypeException;
import cmpt355.project.InternalCompilerException;
import cmpt355.project.InternalParserException;
import cmpt355.project.ast.Typed;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.ast.node.MethodDefinition;
import cmpt355.project.ast.node.Statement;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.jvm.Opcode;
import cmpt355.project.language.ArrayType;
import cmpt355.project.language.ClassType;
import cmpt355.project.language.DataType;
import cmpt355.project.language.PrimitiveType;

import java.util.List;
import java.util.Optional;

import static cmpt355.project.language.PrimitiveType.*;

public class ReturnStatement extends Statement implements Typed {

    private Expression returnValue;

    public ReturnStatement() { }

    public ReturnStatement(Expression returnValue) {
        this.setReturnValue(returnValue);
    }

    @Override
    public List<Expression> children() {
        return getReturnValue().map(List::of).orElse(List.of());
    }

    @Override
    public void validateType() throws DataTypeException {
        Optional<MethodDefinition> maybeMethodDef = findAncestor(n -> n instanceof MethodDefinition);
        if (maybeMethodDef.isEmpty())
            throw new InternalParserException("Return statement not inside a method definition!");
        var methodDef = maybeMethodDef.get();
        var returnType = methodDef.getReturnType().getType();
        if (returnType == DataType.VOID && returnValue != null)
            throw new DataTypeException("Returning value from void method");
        else if (returnType != DataType.VOID && returnValue == null)
            throw new DataTypeException("Return without value from non-void method");
        else if (returnValue != null && !returnType.isSupertypeOf(returnValue.getType()))
            throw new DataTypeException("Cannot return value of type " + returnValue.getType() + " from method with return type " + returnType);
    }

    @Override
    public DataType getType() {
        return (returnValue == null) ? DataType.VOID : returnValue.getType();
    }

    @Override
    public String toString() {
        return "return";
    }

    public Optional<Expression> getReturnValue() {
        return Optional.ofNullable(returnValue);
    }

    public void setReturnValue(Expression returnValue) {
        this.returnValue = reparent(returnValue);
    }

}
