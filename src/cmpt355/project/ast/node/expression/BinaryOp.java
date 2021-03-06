package cmpt355.project.ast.node.expression;

import cmpt355.project.DataTypeException;
import cmpt355.project.InternalCompilerException;
import cmpt355.project.InternalParserException;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.Label;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.jvm.Opcode;
import cmpt355.project.language.DataType;
import cmpt355.project.language.ExternalClassType;
import cmpt355.project.language.Method;
import cmpt355.project.language.PrimitiveType;

import static cmpt355.project.jvm.Opcode.*;
import static cmpt355.project.language.ExternalClassType.STRING;
import static cmpt355.project.language.PrimitiveType.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BinaryOp extends Expression {

    // + (special handling needed because of string concatenation)
    public static class AddOp extends ArithmeticOp {

        private static final Method
                OBJECT_TO_STRING = ExternalClassType.OBJECT.findMethods("toString").get(0),
                BYTE_TO_STRING,
                SHORT_TO_STRING,
                INT_TO_STRING,
                LONG_TO_STRING,
                FLOAT_TO_STRING,
                DOUBLE_TO_STRING,
                CHAR_TO_STRING,
                BOOLEAN_TO_STRING,
                STRING_CONCAT;

        static {
            try {
                BYTE_TO_STRING      = Method.fromExternal(Byte.class.getMethod(     "toString", byte.class));
                SHORT_TO_STRING     = Method.fromExternal(Short.class.getMethod(    "toString", short.class));
                INT_TO_STRING       = Method.fromExternal(Integer.class.getMethod(  "toString", int.class));
                LONG_TO_STRING      = Method.fromExternal(Long.class.getMethod(     "toString", long.class));
                FLOAT_TO_STRING     = Method.fromExternal(Float.class.getMethod(    "toString", float.class));
                DOUBLE_TO_STRING    = Method.fromExternal(Double.class.getMethod(   "toString", double.class));
                CHAR_TO_STRING      = Method.fromExternal(Character.class.getMethod("toString", char.class));
                BOOLEAN_TO_STRING   = Method.fromExternal(Boolean.class.getMethod(  "toString", boolean.class));
                STRING_CONCAT = Method.fromExternal(String.class.getMethod("concat", String.class));
            } catch (NoSuchMethodException ex) {
                throw new InternalCompilerException(ex);
            }
        }

        public AddOp(String op, Expression left, Expression right) {
            super("+", left, right);
            if (!"+".equals(op))
                throw new IllegalArgumentException("op must be +");
        }

        @Override
        public DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (leftType.equals(STRING) || rightType.equals(STRING))
                return STRING;
            else
                return super.computeType(leftType, rightType);
        }

    }

    // -, *, /, %
    public static class ArithmeticOp extends BinaryOp {
        public ArithmeticOp(String op, Expression left, Expression right) {
            super(op, left, right);
        }

        @Override
        public DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (!(leftType instanceof PrimitiveType)
                    || !(rightType instanceof PrimitiveType)
                    || leftType == BOOLEAN || rightType == BOOLEAN)
                throwIncompatibleTypes(leftType, rightType);
            return ((PrimitiveType)leftType).binaryNumericPromotion((PrimitiveType)rightType);
        }

    }

    // <<, >>, >>>
    public static class ShiftOp extends BinaryOp {
        public ShiftOp(String op, Expression left, Expression right) {
            super(op, left, right);
        }

        @Override
        public DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (!(leftType instanceof PrimitiveType)
                    || !(rightType instanceof PrimitiveType))
                throwIncompatibleTypes(leftType, rightType);
            PrimitiveType leftPType = ((PrimitiveType)leftType).unaryNumericPromotion(),
                    rightPType = ((PrimitiveType)rightType).unaryNumericPromotion();
            if (!leftPType.isIntegral() || !rightPType.isIntegral())
                throwIncompatibleTypes(leftType, rightType);
            return leftPType;
        }

    }

    // <, >, <=, >= (and ==/!= via subclass EqualityOp)
    public static class ComparisonOp extends BinaryOp {
        public ComparisonOp(String op, Expression left, Expression right) {
            super(op, left, right);
        }

        @Override
        DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (!(leftType instanceof PrimitiveType)
                    || !(rightType instanceof PrimitiveType)
                    || leftType == BOOLEAN || rightType == BOOLEAN)
                throwIncompatibleTypes(leftType, rightType);
            return BOOLEAN;
        }

    }

    // ==, !=
    public static class EqualityOp extends ComparisonOp {
        public EqualityOp(String op, Expression left, Expression right) {
            super(op, left, right);
        }

        @Override
        DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (leftType instanceof PrimitiveType && rightType instanceof PrimitiveType) {
                if ((leftType == BOOLEAN || rightType == BOOLEAN) && (leftType != rightType))
                    throwIncompatibleTypes(leftType, rightType);
            } else if (leftType.isReferenceType() && !rightType.isReferenceType()
                    || !leftType.isReferenceType() && rightType.isReferenceType())
                throwIncompatibleTypes(leftType, rightType);

            return BOOLEAN;
        }

    }

    // &, |, ^
    public static class BitwiseOp extends BinaryOp {
        public BitwiseOp(String op, Expression left, Expression right) {
            super(op, left, right);
        }

        @Override
        DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (!(leftType instanceof PrimitiveType) || !(rightType instanceof PrimitiveType))
                throwIncompatibleTypes(leftType, rightType);
            if (leftType == BOOLEAN || rightType == BOOLEAN) {
                if (leftType == rightType)
                    return BOOLEAN;
                else
                    throwIncompatibleTypes(leftType, rightType);
            }

            var pType = ((PrimitiveType)leftType).binaryNumericPromotion((PrimitiveType)rightType);
            if (!pType.isIntegral())
                throwIncompatibleTypes(leftType, rightType);
            return pType;
        }

    }

    // &&, ||
    public static class ConditionalOp extends BinaryOp {
        public ConditionalOp(String op, Expression left, Expression right) {
            super(op, left, right);
        }

        @Override
        DataType computeType(DataType leftType, DataType rightType) throws DataTypeException {
            if (leftType != BOOLEAN || rightType != BOOLEAN)
                throwIncompatibleTypes(leftType, rightType);
            return BOOLEAN;
        }

    }

    private String op;
    private Expression left;
    private Expression right;
    private DataType computedType;

    @FunctionalInterface
    private interface BinaryOpConstructor {
        BinaryOp construct(String op, Expression left, Expression right);
    }

    private static final Map<String, BinaryOpConstructor> opConstructors = new HashMap<>();

    BinaryOp(String op, Expression left, Expression right) {
        setOp(op);
        setLeft(left);
        setRight(right);
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        Objects.requireNonNull(op);
        this.op = op;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = reparentNonNull(left);
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = reparentNonNull(right);
    }

    private static void addOpConstructors(BinaryOpConstructor ctor, String... ops) {
        for (var op : ops)
            opConstructors.put(op, ctor);
    }

    private static Map<String, BinaryOpConstructor> getOpConstructors() {
        if (opConstructors.isEmpty()) {
            addOpConstructors(AddOp::new, "+");
            addOpConstructors(ArithmeticOp::new, "-", "*", "/", "%");
            addOpConstructors(ShiftOp::new, "<<", ">>", ">>>");
            addOpConstructors(ComparisonOp::new, "<", ">", "<=", ">=");
            addOpConstructors(EqualityOp::new, "==", "!=");
            addOpConstructors(BitwiseOp::new, "&", "|", "^");
            addOpConstructors(ConditionalOp::new, "&&", "||");
        }
        return opConstructors;
    }

    public static BinaryOp of(String op, Expression left, Expression right) {
        var ctor = getOpConstructors().getOrDefault(op, null);
        if (ctor == null)
            throw new InternalParserException("Invalid binary op: " + op);
        return ctor.construct(op, left, right);
    }

    void throwIncompatibleTypes(DataType leftType, DataType rightType) throws DataTypeException {
        throw new DataTypeException(String.format("Incompatible types: %s %s %s", leftType, op, rightType));
    }

    DataType leftType() {
        return left.getType();
    }

    DataType rightType() {
        return right.getType();
    }

    abstract DataType computeType(DataType leftType, DataType rightType) throws DataTypeException;

    @Override
    public final DataType getType() {
        return computedType;
    }

    @Override
    public void validateType() throws DataTypeException {
        DataType leftType = leftType(),
                rightType = rightType();
        if (leftType == DataType.VOID || rightType == DataType.VOID)
            throwIncompatibleTypes(leftType, rightType);
        computedType = computeType(leftType(), rightType());
    }

    @Override
    public List<Expression> children() {
        return List.of(left, right);
    }

    @Override
    public String toString() {
        return op;
    }
}
