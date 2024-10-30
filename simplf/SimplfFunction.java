package simplf;

import java.util.List;
import java.util.Iterator;

class SimplfFunction implements SimplfCallable {
    final Stmt.Function declaration;
    private Environment closure;
    SimplfFunction (Stmt. Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    public void setClosure (Environment environment) {
        this.closure = environment;
    }

    @Override
    public Object call (Interpreter interpreter, List<Object> args) { 
        Environment environment = new Environment (closure);


        Iterator<Token> paramIterator = declaration.params.iterator();
        Iterator<Object> argIterator = args.iterator();

        while (paramIterator.hasNext() && argIterator.hasNext()) {
        Token param = paramIterator.next();
        Object arg = argIterator.next();
        environment = environment.define(param, param.lexeme, arg);
    }

    return interpreter.executeBlock(declaration.body, environment);
}
        @Override
        public String toString() {
        return "<fn "+ declaration.name.lexeme + ">";
        }

}
