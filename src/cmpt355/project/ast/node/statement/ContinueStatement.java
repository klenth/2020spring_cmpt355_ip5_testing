package cmpt355.project.ast.node.statement;

import cmpt355.project.SyntaxException;
import cmpt355.project.ast.node.AstNode;
import cmpt355.project.ast.node.Statement;
import cmpt355.project.codegen.MethodContext;
import cmpt355.project.jvm.Instruction;

import java.util.List;
import java.util.Optional;

public class ContinueStatement extends Statement {

    @Override
    public List<? extends AstNode> children() {
        return List.of();
    }

    @Override
    public String toString() {
        return "continue";
    }

}
