package cmpt355.project.ast.node;

import cmpt355.project.ast.Typed;
import cmpt355.project.codegen.MethodContext;

/**
 * Abstract base class for expression-like AST nodes.
 */
public abstract class Expression extends AstNode implements Typed {

    public final void generateCode(MethodContext context) {
        throw new UnsupportedOperationException();
    }
}
