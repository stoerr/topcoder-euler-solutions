package net.stoerr.functional.backus;

/**
 * Numeric functions
 * 
 * @author hps
 * @since 26.11.2008
 */
public class Numerics {

    public static final Function PLUS = new LazyFunction() {
        @Override
        protected Object compute(Object arg) {
            ListObject vals = (ListObject) arg;
            double res = ((Number) vals.get(0).get()).doubleValue();
            for (int i = 1; i < vals.size(); ++i) {
                double n = ((Number) vals.get(0).get()).doubleValue();
                res = res + n;
            }
            return new ImmediateValue(res);
        }
    };

    public static final Function MINUS = new LazyFunction() {
        @Override
        protected Object compute(Object arg) {
            ListObject vals = (ListObject) arg;
            if (2 != vals.size())
                throw new BottomException("expected 2: " + vals.size());
            return new ImmediateValue(((Number) vals.get(0).get()).doubleValue()
                    - ((Number) vals.get(1).get()).doubleValue());
        }
    };

    public static final Function TIMES = new LazyFunction() {
        @Override
        protected Object compute(Object arg) {
            ListObject vals = (ListObject) arg;
            double res = ((Number) vals.get(0).get()).doubleValue();
            for (int i = 1; i < vals.size(); ++i) {
                double n = ((Number) vals.get(0).get()).doubleValue();
                res = res * n;
            }
            return new ImmediateValue(res);
        }
    };

    public static final Function DIVIDE = new LazyFunction() {
        @Override
        protected Object compute(Object arg) {
            ListObject vals = (ListObject) arg;
            if (2 != vals.size())
                throw new BottomException("expected 2: " + vals.size());
            return new ImmediateValue(((Number) vals.get(0).get()).doubleValue()
                    - ((Number) vals.get(1).get()).doubleValue());
        }
    };

    public static final Function ZERO = new Function() {
        public Value call(Value arg) {
            return new ImmediateValue((double) 0);
        }
    };
    public static final Function ONE = new Function() {
        public Value call(Value arg) {
            return new ImmediateValue((double) 1);
        }
    };
}
