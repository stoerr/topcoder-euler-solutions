package net.stoerr.euler.help;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static long multiply(final List<Long> l) {
        long res = 1;
        for (final Long v : l) {
            res *= v;
        }
        return res;
    }

    public static long add(final List<Long> l) {
        long res = 0;
        for (final Long v : l) {
            res += v;
        }
        return res;
    }

    public static <T> Map<T, Integer> count(final List<T> l) {
        final Map<T, Integer> res = new HashMap<T, Integer>();
        for (final T n : l) {
            Integer cnt = res.get(n);
            if (null == cnt) {
                cnt = 0;
            }
            cnt++;
            res.put(n, cnt);
        }
        return res;
    }

    public static <T> List<T> filter(final Collection<T> l, final Func<T, Boolean> pred) {
        final List<T> res = new ArrayList<T>();
        for (final T el : l) {
            if (pred.call(el)) {
                res.add(el);
            }
        }
        return res;
    }

    public static <T extends Comparable<T>> Comparator<Collection<T>> listComparator() {
        return new ListComparator<T>();
    }

    private static class ListComparator<T extends Comparable<T>> implements Comparator<Collection<T>> {
        public int compare(final Collection<T> o1, final Collection<T> o2) {
            int res = 0;
            final Iterator<T> i1 = o1.iterator();
            final Iterator<T> i2 = o2.iterator();
            while (0 == res && (i1.hasNext() || i2.hasNext())) {
                if (!i2.hasNext()) {
                    res = 1;
                } else if (!i1.hasNext()) {
                    res = -1;
                } else {
                    res = i1.next().compareTo(i2.next());
                }
            }
            return res;
        }
    }
}
