package cmpt355.project.ast.node.expression;

import static cmpt355.project.language.PrimitiveType.*;

import cmpt355.project.DataTypeException;
import cmpt355.project.InternalCompilerException;
import cmpt355.project.ast.node.AstNode;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.ArrayType;
import cmpt355.project.language.DataType;
import cmpt355.project.language.PrimitiveType;

import java.util.List;

public class ArraySubscript extends LValue {

    private Expression array;
    private Expression index;

    public ArraySubscript(Expression array, Expression index) {
        setArray(array);
        setIndex(index);
    }

    @Override
    public DataType getType() {
        return ((ArrayType)array.getType()).getComponentType();
    }

    @Override
    public void validateType() throws DataTypeException {
        if (!(array.getType() instanceof ArrayType))
            throw new DataTypeException("Subscripting value of type " + array.getType());
        var indexType = index.getType();
        if (indexType != PrimitiveType.INT
                && indexType != PrimitiveType.SHORT
                && indexType != PrimitiveType.CHAR
                && indexType != PrimitiveType.BYTE)
            throw new DataTypeException("Index of type " + indexType);
    }

    @Override
    public List<? extends AstNode> children() {
        return List.of(array, index);
    }

    @Override
    public String toString() {
        return "subscript";
    }

    public Expression getArray() {
        return array;
    }

    public void setArray(Expression array) {
        this.array = reparentNonNull(array);
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = reparentNonNull(index);
    }

}
