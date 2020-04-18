package cmpt355.project.ast.node.statement;

import cmpt355.project.ast.node.AstNode;
import cmpt355.project.ast.node.Statement;
import cmpt355.project.codegen.MethodContext;

import java.util.List;

public class EmptyStatement extends Statement {

    @Override
    public List<? extends AstNode> children() {
        return List.of();
    }

    @Override
    public String toString() {
        return "empty statement";
    }

}
