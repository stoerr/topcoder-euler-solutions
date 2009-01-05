package net.stoerr.euler.help;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Modulo {

    private static final long b16 = 65536;

    public static long mod(final long x, final long m) {
        if (0 <= x) return x % m;
        else return m - (-x % m);
    }

    public static long mod(final double x, final long m) {
        final double d = Math.floor(x / m);
        return Math.round(x - d * m);
    }

    public static long mult(final long a, final long b, final long m) {
        final long al = a % b16;
        final long ah = a / b16;
        final long bl = b % b16;
        final long bh = b / b16;
        long res = mod(ah * bh, m);
        res = mod(b16 * (double) res + al * bh + bl * ah, m);
        res = mod(b16 * (double) res + al * bl, m);
        return res;
    }

    /**
     * Yields the list of tuples modulo m2 that fulfill pred and are equal to the tuples modulo m1 from l. <br>
     * m1 has to divide m2.
     */
    static List<List<Long>> liftStep(final List<List<Long>> l, final long m1, final long m2,
            final Func2<List<Long>, Long, Boolean> pred) {
        final int n = l.get(0).size();
        List<List<Long>> from = l;
        for (int i = 0; i < n; ++i) {
            final List<List<Long>> to = new ArrayList<List<Long>>();
            for (final List<Long> el : from) {
                for (long k = el.get(i); k < m2; k += m1) {
                    final List<Long> eln = new ArrayList<Long>(el);
                    eln.set(i, k);
                    to.add(eln);
                }
            }
            from = to;
        }
        return CollectionUtils.filter(from, FuncUtil.bind2nd(pred, m2));
    }

    /**
     * Yields the list of tuples modulo m2 that fulfill pred and are equal to the tuples modulo m1 from l. <br>
     * m1 has to divide m2.
     */
    public static List<List<Long>> lift(final List<List<Long>> l, final long m1, final long m2,
            final Func2<List<Long>, Long, Boolean> pred) {
        final List<Long> fac = PrimeUtils.factor(m2 / m1);
        List<List<Long>> res = l;
        long m = m1;
        for (final Long f : fac) {
            final long mn = m * f;
            res = liftStep(res, m, mn, pred);
            m = mn;
        }
        final Comparator<Collection<Long>> listComparator = CollectionUtils.listComparator();
        Collections.sort(res, listComparator);
        return res;
    }

    /** Yields the list of n-tuples modulo m that fulfill pred. Calculation using {@link #lift}. */
    public static List<List<Long>> solve(final int n, final long m, final Func2<List<Long>, Long, Boolean> pred) {
        final List<List<Long>> seed = Collections.singletonList(Collections.nCopies(n, new Long(0)));
        return lift(seed, 1, m, pred);
    }
}
