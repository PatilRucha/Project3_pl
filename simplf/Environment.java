package simplf;

class Environment {
    public AssocList assocList;
    public final Environment enclosing;  

    Environment() {
        this.assocList = null;
        this.enclosing = null;  
    }

    Environment(Environment enclosing) {
        this.assocList = null;
        this.enclosing = enclosing;  
    }

    Environment(AssocList assocList, Environment enclosing) {
        this.assocList = assocList;
        this.enclosing = enclosing; 
    }

    Environment define(Token varToken, String name, Object value) {
        AssocList newAssocList = new AssocList(name, value, this.assocList);
        this.assocList = newAssocList;
        return new Environment(newAssocList, this); 
    }

    void assign(Token name, Object value) {
        Environment env = this; 
    // Traverse up the environment chain
        while (env != null) {  
            AssocList assoc = env.assocList; 
            while (assoc != null) { 
                if (assoc.name.equals(name.lexeme)) {
                    assoc.value = value; 
                    return; 
                }
                assoc = assoc.next;
            }
            env = env.enclosing;
    }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'");
    }

    public Object get(Token name) {
        for (AssocList assoc = this.assocList; assoc != null; assoc = assoc.next) {
            if (assoc.name.equals(name.lexeme)) {
                return assoc.value;
            }
        }

        if (this.enclosing != null) {
            return this.enclosing.get(name);  
        }

        throw new RuntimeException("Undefined variable '" + name.lexeme + "'");
    }


}
