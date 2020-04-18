package cmpt355.project.ast.node.expression;

import cmpt355.project.ast.node.Expression;
import cmpt355.project.codegen.MethodContext;

public abstract class LValue extends Expression {

    public final void generateSetCode(MethodContext context, Expression rhs) {
        throw new UnsupportedOperationException();
    }
}
