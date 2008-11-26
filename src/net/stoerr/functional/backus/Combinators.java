package net.stoerr.functional.backus;

/**
 * Functional combinators.
 * 
 * @author hps
 * @since 26.11.2008
 */
public class Combinators {

    /** Functional composition f:g:x (that is f(g(x)). */
    public static Function compose(final Function f, final Function g) {
        return new LazyFunction() {
            @Override
            protected Object compute(Value arg) {
                Value gval = g.call(arg);
                return f.call(gval).get();
            }
        };
    }

}
