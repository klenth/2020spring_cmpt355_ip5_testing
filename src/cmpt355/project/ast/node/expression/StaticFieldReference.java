package cmpt355.project.ast.node.expression;

import cmpt355.project.DataTypeException;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.Helper;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.*;

import java.util.List;
import java.util.Objects;

import static cmpt355.project.language.PrimitiveType.*;

public class StaticFieldReference extends LValue {

    private DataType classType;
    private String fieldName;
    private Field resolvedVariable;

    public StaticFieldReference(ClassType classType, String fieldName) {
        setClassType(classType);
        setFieldName(fieldName);
    }

    public StaticFieldReference(Field field) {
        this((ClassType)field.getEnclosingType(), field.getName());
        if (!field.getModifiers().contains(Modifier.STATIC))
            throw new IllegalArgumentException("Field " + field + " is non-static");
        this.resolvedVariable = field;
    }

    @Override
    public DataType getType() {
        return resolvedVariable.getType();
    }

    @Override
    public void validateType() throws DataTypeException {
        resolve();
    }

    @Override
    public String toString() {
        return String.format("Static field reference: %s.%s", classType, fieldName);
    }

    @Override
    public List<Variable> children() {
        return (resolvedVariable == null) ? List.of() : List.of(resolvedVariable);
    }

    public DataType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = Objects.requireNonNull(classType);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = Objects.requireNonNull(fieldName);
    }

    public Field getVariable() {
        return resolvedVariable;
    }

    private void resolve() throws DataTypeException {
        if (resolvedVariable == null) {
            classType = classType.resolve(this);
            var maybeVar = classType.findField(fieldName);
            if (maybeVar.isPresent()) {
                this.resolvedVariable = maybeVar.get();
                if (!resolvedVariable.getModifiers().contains(Modifier.STATIC))
                    throw new DataTypeException(String.format("Cannot reference non-static field %s.%s from a static context",
                            classType, fieldName));
            } else
                throw new DataTypeException(String.format("Class %s has no field named %s", classType, fieldName));
        }
    }

}
