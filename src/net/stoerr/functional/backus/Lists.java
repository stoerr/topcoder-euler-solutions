package net.stoerr.functional.backus;

/**
 * Functions related to {@link ListObject}s.
 * @author hps
 * @since 26.11.2008
 */
public class Lists {

    public static Function nth(final int l) {
        return new LazyFunction() {
            @Override
            protected Object compute(Value arg) {
                return arg.asList().get(l);
            }
        };
    }

    public static final Function FIRST = nth(0);
    public static final Function SECOND = nth(1);

    public static final Function tail(final int l) {
        return new LazyFunction() {
            @Override
            protected Object compute(final Value arg) {
                return new AbstractList() {
                    public Value get(int i) {
                        return arg.asList().get(i + l);
                    }

                    public int size() {
                        int siz = arg.asList().size() - l;
                        if (0 > siz) throw new BottomException(l + "Tail of " + (siz + l));
                        return siz;
                    }
                };
            }
        };
    }

    public static final Function TAIL = tail(1);

    /** A immediate list value */
    public static Function L(Object... vals) {
        return Combinators.constant(new ImmediateList(vals));
    }

    /* public static final Function APPEND = new LazyFunction() {
        @Override
        protected Object compute(final Value arg) {
            
        }
    }; */
}
