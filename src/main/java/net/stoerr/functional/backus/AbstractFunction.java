package net.stoerr.functional.backus;

/**
 * Implements the convenience methods of {@link Function}.
 * @author hps
 * @since 26.11.2008
 */
public abstract class AbstractFunction implements Function {
    
    public Function c(Function f) {
        return Combinators.compose(f, this);
    }

}
