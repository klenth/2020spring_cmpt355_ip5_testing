package cmpt355.project.ast.node.expression;

import cmpt355.project.InternalCompilerException;
import cmpt355.project.InternalParserException;
import cmpt355.project.ast.node.DataTypeNode;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.language.DataType;
import cmpt355.project.language.ExternalClassType;
import cmpt355.project.language.PrimitiveType;

import java.util.Objects;
import java.util.regex.Pattern;

public class Literal extends Expression {

    private static final Pattern HEXADECIMAL_ESCAPE
            = Pattern.compile("^(u[0-9a-fA-F]{4})");
    private static final Pattern OCTAL_ESCAPE
            = Pattern.compile("^([0-7]|[0-7][0-7]|[0-3][0-7][0-7])");

    private DataTypeNode typeNode;
    private String text;

    public Literal(DataTypeNode typeNode, String text) {
        this.setTypeNode(typeNode);
        this.setText(text);
    }

    @Override
    public DataType getType() {
        return typeNode.getType();
    }

    @Override
    public void validateType() {
        // Literals never need to be validated
    }

    @Override
    public String toString() {
        return "literal: " + getText();
    }

    public void setTypeNode(DataTypeNode typeNode) {
        this.typeNode = reparentNonNull(typeNode);
    }

    public DataTypeNode getTypeNode() {
        return typeNode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = Objects.requireNonNull(text);
    }

}
