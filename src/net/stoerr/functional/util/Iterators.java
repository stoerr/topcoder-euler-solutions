package net.stoerr.functional.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import net.stoerr.euler.help.Pair;
import net.stoerr.functional.backus.Value;

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

    /**
     * Makes a lazy collection from the iterator - everything is read as needed. Probably threadsafe.<br>
     * Warning: size() reads everything from the iterator - not lazy!<br>
     * Caution: Maybe size() returns too small value if any of it.next() is null. We probably have trouble with null
     * values anyway.
     */
    public static <T> DelayedList<T> delayedList(final Iterator<T> it) {
        final class DelayedIteratorList extends AbstractList<T> implements DelayedList<T> {
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
        }
        return new DelayedIteratorList();
    }

    /**
     * Yields an iterator that iterates over the elements of all iterators of superit merged such that smallest come
     * first if the iterators are smallest first.
     */
    public static <T extends Comparable<T>> Iterator<T> merge(Iterator<T> it1, Iterator<T> it2) {
        final class MergedIterator implements Iterator<T> {
            Iterator<T> i1 = null;
            Iterator<T> i2 = null;
            T x1;
            T x2;

            MergedIterator(Iterator<T> it1, Iterator<T> it2) {
                if (it1.hasNext()) {
                    i1 = it1;
                    x1 = it1.next();
                }
                if (it2.hasNext()) {
                    i2 = it2;
                    x2 = it2.next();
                }
                order();
            }

            private void order() {
                if (null == i1) {
                    i1 = i2;
                    x1 = x2;
                    i2 = null;
                }
                if (null == i2) return;
                if (x1.compareTo(x2) > 0) {
                    Iterator<T> ih = i1;
                    T xh = x1;
                    i1 = i2;
                    x1 = x2;
                    i2 = ih;
                    x2 = xh;
                }
            }

            public boolean hasNext() {
                return (null != i1) || (null != i2);
            }

            public T next() {
                T res = x1;
                if (i1.hasNext()) x1 = i1.next();
                else i1 = null;
                order();
                return res;
            }

            public void remove() {
                i1.remove();
            }
        }
        return new MergedIterator(it1, it2);
    }
}
