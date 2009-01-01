package net.stoerr.functional.backus;

/**
 * Numeric functions
 * 
 * @author hps
 * @since 26.11.2008
 */
public final class Numerics {

    public static final Function PLUS = new PlusDoubleFunction();

    private static final class PlusDoubleFunction extends LazyFunction {
        @Override
        protected Object compute(Value arg) {
            ListObject vals = arg.asList();
            double res = ((Number) vals.get(0).get()).doubleValue();
            for (int i = 1; i < vals.size(); ++i) {
                double n = ((Number) vals.get(i).get()).doubleValue();
                res = res + n;
            }
            return res;
        }
    };

    public static final Function MINUS = new LazyFunction() {
        @Override
        protected Object compute(Value arg) {
            ListObject vals = arg.asList();
            if (2 != vals.size())
                throw new BottomException("expected 2: " + vals.size());
            return ((Number) vals.get(0).get()).doubleValue() - ((Number) vals.get(1).get()).doubleValue();
        }
    };

    public static final Function TIMES = new LazyFunction() {
        @Override
        protected Object compute(Value arg) {
            ListObject vals = arg.asList();
            double res = ((Number) vals.get(0).get()).doubleValue();
            for (int i = 1; i < vals.size(); ++i) {
                double n = ((Number) vals.get(i).get()).doubleValue();
                res = res * n;
            }
            return res;
        }
    };

    public static final Function DIVIDE = new LazyFunction() {
        @Override
        protected Object compute(Value arg) {
            ListObject vals = arg.asList();
            if (2 != vals.size())
                throw new BottomException("expected 2: " + vals.size());
            return ((Number) vals.get(0).get()).doubleValue() - ((Number) vals.get(1).get()).doubleValue();
        }
    };

    public static final Function NEGATE = new LazyFunction() {
        @Override
        protected Object compute(Value arg) {
            return -((Number) arg.get()).doubleValue();
        }
    };

    public static final Function ZERO = new AbstractFunction() {
        public Value call(Value arg) {
            return new ImmediateValue((double) 0);
        }
    };

    public static final Function ONE = new AbstractFunction() {
        public Value call(Value arg) {
            return new ImmediateValue((double) 1);
        }
    };

    public static Function numberList(final double start, final double end, final double step) {
        return new LazyFunction() {
            @Override
            protected Object compute(Value arg) {
                class NumberList extends AbstractList {
                    public Value get(int i) {
                        return new ImmediateValue(start + i + step);
                    }

                    public int size() {
                        return (int) Math.round((end - start) / step);
                    }
                }
                return new NumberList();
            }
        };
    }

    /** Number -> Boolean */
    public static Function between(final double start, final double end) {
        class BetweenFunction extends LazyFunction {
            @Override
            protected Object compute(Value arg) {
                double val = arg.asDouble();
                return (start <= val) && (val <= end) || (start >= val) && (val >= end);
            }
        }
        return new BetweenFunction();
    }
}
