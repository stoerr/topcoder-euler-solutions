package net.stoerr.functional.backus;

/**
 * Implements the convenience methods of {@link Function}.
 * @author hps
 * @since 26.11.2008
 */
public abstract class AbstractFunction implements Function {
    
    private final String name;
    
    public AbstractFunction() {
        name = super.toString();
    }
    
    public AbstractFunction(String name) {
        this.name = name + super.toString();
    }

    public Function c(Function f) {
        return Combinators.compose(f, this);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
