package net.stoerr.functional.backus;

/**
 * A {@link Value} calculated on demand.
 * 
 * @author hps
 * @since 26.11.2008
 */
public abstract class LazyValue implements Value {

    private volatile Value val = null;

    public final Object get() {
        if (null != val)
            return val.get();
        else
            return computeValue();
    }

    private final synchronized Object computeValue() {
        if (null == val) {
            val = new ImmediateValue(compute());
        }
        return val.get();
    }

    /** The lazily carried out computation; called once. */
    protected abstract Object compute();
}
