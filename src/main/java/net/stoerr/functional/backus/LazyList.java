package net.stoerr.functional.backus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A listobject for which even the {@link LazyValue}s are lazily calculated.
 * 
 * @author hps
 * @since 26.11.2008
 */
public abstract class LazyList extends AbstractList {

    private Map<Integer, Value> values = new ConcurrentHashMap<Integer, Value>();

    public final Value get(int i) {
        Value res = values.get(i);
        if (null == res) {
            res = makeValue(i);
        }
        return res;
    }

    private synchronized Value makeValue(int i) {
        Value res = values.get(i);
        if (null == res) {
            res = value(i);
            if (null == res) {
                System.out.println("AAARGH");
                value(i);
            }
            values.put(i, res);
        }
        return res;
    }

    /** The actual computation of the value - usually a {@link LazyValue}. */
    protected abstract Value value(final int i);

}
