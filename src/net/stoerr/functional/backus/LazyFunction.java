package net.stoerr.functional.backus;

/**
 * A lazily calculated function.
 * 
 * @author hps
 * @since 26.11.2008
 */
public abstract class LazyFunction implements Function {

    public final Value call(final Value arg) {
        return new LazyValue() {
            @Override
            protected Object compute() {
                return LazyFunction.this.compute(arg);
            }
        };
    }

    /** The actual computation. */
    protected abstract Object compute(Value arg);
}
