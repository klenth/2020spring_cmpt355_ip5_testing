package cmpt355.project.ast.node.statement;

import cmpt355.project.DataTypeException;
import cmpt355.project.ast.node.AstNode;
import cmpt355.project.ast.node.Expression;
import cmpt355.project.ast.node.Statement;
import cmpt355.project.codegen.Label;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;
import cmpt355.project.jvm.Opcode;
import cmpt355.project.language.PrimitiveType;

import java.util.List;

public class DoWhileStatement extends Statement implements Loop {

    private Expression condition;
    private Statement body;
    private Label breakLabel, continueLabel;

    public DoWhileStatement(Expression condition, Statement body) {
        this.setCondition(condition);
        this.setBody(body);
    }

    @Override
    public String toString() {
        return "do-while";
    }

    @Override
    public List<? extends AstNode> children() {
        return List.of(getCondition(), getBody());
    }

    @Override
    public void validateType() throws DataTypeException {
        var conditionType = condition.getType();
        if (conditionType != PrimitiveType.BOOLEAN)
            throw new DataTypeException("do-while condition of type " + conditionType);
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = reparentNonNull(condition);
    }

    public Statement getBody() {
        return body;
    }

    public void setBody(Statement body) {
        this.body = reparentNonNull(body);
    }

    @Override
    public Label getBreakLabel() {
        return breakLabel;
    }

    @Override
    public Label getContinueLabel() {
        return continueLabel;
    }
}
