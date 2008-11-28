package net.stoerr.functional.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Helperclass for {@link Iterator}s.
 * @author hps
 * @since 27.11.2008
 */
public final class Iterators {

    public static <S, T> Iterator<T> map(final Iterator<S> it, final F<S, T> f) {
        return new Iterator<T>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public T next() {
                return f.call(it.next());
            }

            public void remove() {
                it.remove();
            }
        };
    }

    public static <T> Iterator<T> concat(Iterator<T>... iterators) {
        return concat(Arrays.asList(iterators).iterator());
    }

    /**
     * Iterator that yields all elements from all iterators superit returns, in order. Not threadsafe.
     */
    public static <T> Iterator<T> concat(final Iterator<Iterator<T>> superit) {
        return new Iterator<T>() {
            private Iterator<T> current = null;

            /**
             * getCurrent.hasNext() is always true iff not null; when null everything is exhausted.
             */
            private Iterator<T> getCurrent() {
                while (null == current && superit.hasNext()) {
                    current = superit.next();
                    if (!current.hasNext()) {
                        current = null;
                    }
                }
                return current;
            }

            public boolean hasNext() {
                return null != getCurrent();
            }

            public T next() {
                T val = getCurrent().next();
                if (!current.hasNext()) current = null;
                return val;
            }

            public void remove() {
                getCurrent().remove();
            }

        };
    }

    /** For abbreviation for(T val : iterable(it)) {...} ; one time only. */
    public static <T> Iterable<T> iterable(final Iterator<T> it) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return it;
            }
        };
    }

    /** Reads everything from an iterator into a collection. */
    public static <T> List<T> collection(Iterator<T> it) {
        List<T> res = new ArrayList<T>();
        for (T val : iterable(it))
            res.add(val);
        return res;
    }

    /** A list that is lazily calculated */
    public static interface DelayedList<T> extends List<T> {
        /** Tells if there is a element at index index; the element is not yet calculated. */
        public boolean has(int index);
    }

    private static abstract class AbstractLazyList<T> extends AbstractList<T> implements DelayedList<T> {}

    /**
     * Makes a lazy collection from the iterator - everything is read as needed. Probably threadsafe.<br>
     * Warning: size() reads everything from the iterator - not lazy!<br>
     * Caution: Maybe size() returns too small value if any of it.next() is null. We probably have trouble with null
     * values anyway.
     */
    public static <T> DelayedList<T> delayedList(final Iterator<T> it) {
        return new AbstractLazyList<T>() {
            private volatile int read = 0; // is Integer.MAX_VALUE iff
            // everything was read from it.
            private final Map<Integer, T> values = new ConcurrentHashMap<Integer, T>();

            @Override
            public T get(int index) {
                if (read > index) {
                    return values.get(index);
                } else {
                    readTo(index);
                    return values.get(index);
                }
            }

            /**
             * Reads from it up to the index' element. Postcondition: read>index.
             */
            private synchronized void readTo(int index) {
                while (read <= index) {
                    if (it.hasNext()) {
                        T val = it.next();
                        values.put(read, val);
                        ++read;
                    } else {
                        read = Integer.MAX_VALUE;
                        return;
                    }
                }
            }

            @Override
            public int size() {
                if (read < Integer.MAX_VALUE) readTo(Integer.MAX_VALUE);
                return values.size();
            }

            public boolean has(int index) {
                if (Integer.MAX_VALUE == read) return index < size();
                if (read > index) return true;
                if (read == index) return it.hasNext();
                readTo(index - 1);
                if (Integer.MAX_VALUE == read) return index < size();
                return it.hasNext();
            }
        };
    }

}
