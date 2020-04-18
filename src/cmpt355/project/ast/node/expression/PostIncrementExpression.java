package cmpt355.project.ast.node.expression;

import cmpt355.project.DataTypeException;
import cmpt355.project.InternalCompilerException;
import cmpt355.project.ast.node.DataTypeNode;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.jvm.Opcode;
import cmpt355.project.language.*;

import java.util.List;

import static cmpt355.project.language.PrimitiveType.*;

public class PostIncrementExpression extends Expression {

    private boolean decrement;
    private LValue lvalue;

    public PostIncrementExpression(boolean decrement, LValue lvalue) {
        this.setDecrement(decrement);
        this.setLvalue(lvalue);
    }

    @Override
    public DataType getType() {
        return lvalue.getType();
    }

    @Override
    public void validateType() throws DataTypeException {
        var lvalueType = getLvalue().getType();

        if (!(lvalueType instanceof PrimitiveType)
                || lvalueType == PrimitiveType.BOOLEAN)
            throw new DataTypeException(String.format("Incompatible types: %s %s", lvalueType, isDecrement() ? "--" : "++"));
    }

    @Override
    public List<LValue> children() {
        return List.of(getLvalue());
    }

    @Override
    public String toString() {
        return isDecrement() ? "( )--" : "( )++";
    }

    public boolean isDecrement() {
        return decrement;
    }

    public void setDecrement(boolean decrement) {
        this.decrement = decrement;
    }

    public LValue getLvalue() {
        return lvalue;
    }

    public void setLvalue(LValue lvalue) {
        this.lvalue = reparentNonNull(lvalue);
    }

}
