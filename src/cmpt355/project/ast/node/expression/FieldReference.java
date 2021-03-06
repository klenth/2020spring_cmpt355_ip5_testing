package cmpt355.project.ast.node.expression;

import cmpt355.project.DataTypeException;
import cmpt355.project.ast.node.AstNode;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.*;
import cmpt355.project.util.Lists;

import java.util.List;
import java.util.Objects;

import static cmpt355.project.language.PrimitiveType.*;

public class FieldReference extends LValue {

    private Expression expr;
    private String fieldName;
    private Field resolved;

    public FieldReference(Expression expr, String fieldName) {
        this.setExpr(expr);
        this.setFieldName(fieldName);
    }

    public FieldReference(Expression expr, Field field) {
        this(expr, field.getName());
        this.resolved = field;
    }

    @Override
    public DataType getType() {
        return getVariable().getType();
    }

    @Override
    public void validateType() throws DataTypeException {
        if (resolved == null) {
            var exprType = expr.getType();
            var maybeVar = exprType.findField(fieldName);
            resolved = maybeVar.orElseThrow(() ->
                    new DataTypeException(String.format("Cannot resolve field %s.%s", exprType, fieldName)));
        }
    }

    @Override
    public List<AstNode> children() {
        return Lists.<AstNode>builder()
                .add(expr)
                .addIfPresent(resolved)
                .build();
    }

    @Override
    public String toString() {
        return String.format("field reference: %s", fieldName);
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = reparentNonNull(expr);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = Objects.requireNonNull(fieldName);
    }

    public Field getVariable() {
        return resolved;
    }

}
