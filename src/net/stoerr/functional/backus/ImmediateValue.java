package net.stoerr.functional.backus;

/**
 * A {@link Value} that is precalculated.
 * @author hps
 * @since 26.11.2008
 */
public final class ImmediateValue extends AbstractValue {

    private final Object val;

    public ImmediateValue(Object val) {
        this.val = val;
    }

    public Object get() {
        return val;
    }
    
    @Override
    public String toString() {
        return String.valueOf(get());
    }
}
