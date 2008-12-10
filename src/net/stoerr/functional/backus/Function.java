package net.stoerr.functional.backus;

/**
 * A function in the sense of backus, calculating a value.
 * @author hps
 * @since 26.11.2008
 */
public interface Function {

    Value call(Value arg);
    
    /** Composition: f is executed on the result of this. Reverse to {@link Combinators#compose(Function, Function)}! */
    Function c(Function f);
    
}
