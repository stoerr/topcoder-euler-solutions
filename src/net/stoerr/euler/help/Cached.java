package net.stoerr.euler.help;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a cache such that a side effect free function is called only once
 * for each argument value. Does never forget.
 */
public abstract class Cached<Arg, Val> implements Func<Arg, Val> {

    private Map<Arg, Val> cache = new HashMap<Arg, Val>();

    /**
     * Consults the cache or calls {@link #impl(Object)}
     */
    public final Val call(Arg arg) {
        if (cache.containsKey(arg)) {
            return cache.get(arg);
        } else {
            Val res = impl(arg);
            cache.put(arg, res);
            return res;
        }
    }

    /**
     * The actual calculation that is to be done.
     */
    protected abstract Val impl(Arg arg);

    /**
     * Caching wrapper around a func.
     */
    public static <Arg, Val> Func<Arg, Val> cacheFunc(final Func<Arg, Val> func) {
        return new Cached<Arg, Val>() {

            @Override
            protected Val impl(Arg arg) {
                return func.call(arg);
            }

        };
    }

}
