package cmpt355.project.ast.node.expression;

import static cmpt355.project.language.PrimitiveType.BOOLEAN;

import cmpt355.project.DataTypeException;
import cmpt355.project.ast.node.AstNode;
import cmpt355.project.ast.node.DataTypeNode;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.DataType;
import cmpt355.project.language.PrimitiveType;

import java.util.List;
import java.util.Objects;

public class CastExpression extends Expression {

    private DataTypeNode castType;
    private Expression expr;

    public CastExpression(DataTypeNode castType, Expression expr) {
        this.setCastType(castType);
        this.setExpr(expr);
    }

    @Override
    public DataType getType() {
        return castType.getType();
    }

    @Override
    public void validateType() throws DataTypeException {
        DataType sourceType = expr.getType();
        DataType destType = castType.getType();

        if (sourceType == DataType.VOID || destType == DataType.VOID)
            throwDataTypeException(sourceType, destType);

        if (sourceType.equals(destType))
            // no problem casting a type to itself!
            return;

        else if (sourceType instanceof PrimitiveType && destType instanceof PrimitiveType) {
            if (sourceType == BOOLEAN || destType == BOOLEAN)
                // Boolean can only be cast to boolean, and we already know they're not the same
                throwDataTypeException(sourceType, destType);
        } else if (sourceType instanceof PrimitiveType || destType instanceof PrimitiveType)
            // Then exactly one of them is primitive, so an error
            throwDataTypeException(sourceType, destType);
        else if (!sourceType.isSupertypeOf(destType) && !destType.isSupertypeOf(sourceType))
            // Neither is a supertype of the other
            throwDataTypeException(sourceType, destType);
    }

    private void throwDataTypeException(DataType sourceType, DataType destType) throws DataTypeException {
        throw new DataTypeException("Invalid cast: from " + sourceType + " to " + destType);
    }


    @Override
    public List<AstNode> children() {
        return List.of(castType, expr);
    }

    @Override
    public String toString() {
        return "typecast";
    }

    public DataTypeNode getCastType() {
        return castType;
    }

    public void setCastType(DataTypeNode castType) {
        this.castType = reparentNonNull(castType);
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = reparentNonNull(expr);
    }
}
