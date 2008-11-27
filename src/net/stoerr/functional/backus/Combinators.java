package net.stoerr.functional.backus;

import java.util.ArrayList;
import java.util.List;

/**
 * Functional combinators.
 * 
 * @author hps
 * @since 26.11.2008
 */
public class Combinators {

    public static final Function constant(final Object val) {
        return new AbstractFunction() {
            public Value call(Value arg) {
                return new ImmediateValue(val);
            }
        };
    }

    public static final Function IDENTITY = new AbstractFunction() {
        public Value call(Value arg) {
            return arg;
        }
    };

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

    /** Construction [f1, f2, f3]. */
    public static Function cn(final Function... functions) {
        return new LazyFunction() {
            @Override
            protected Object compute(final Value arg) {
                return new LazyList() {
                    @Override
                    protected Value value(final int i) {
                        return new LazyValue() {
                            @Override
                            protected Object compute() {
                                return functions[i].call(arg).get();
                            }
                        };
                    }

                    public int size() {
                        return functions.length;
                    }
                };
            }
        };
    }

    /**
     * Applies f to its own result - makes only sense for recursively
     * constructed lists. The arguments are ignored.
     */
    public static Function fixpoint(final Function f) {
        return new AbstractFunction() {
            public Value call(Value ignored) {
                return new LazyValue() {
                    @Override
                    protected Object compute() {
                        return f.call(this).get();
                    }
                };
            }
        };
    }
    
    /** Binds the first argument to a given value. */
    public static Function bindFirst(final Function f, final Value firstArg) {
        return new LazyFunction() {
            @Override
            protected Object compute(final Value arg) {
                ListObject l = new ImmediateList(firstArg, arg);
                return f.call(new ImmediateValue(l)).get();
            }
        };        
    }
}
