package cmpt355.project.ast.node;

import cmpt355.project.codegen.MethodContext;

/**
 * Abstract base class for a statement-like AST node.
 */
public abstract class Statement extends AstNode {

    public void generateCode(MethodContext context) {
        throw new UnsupportedOperationException();
    }
}
