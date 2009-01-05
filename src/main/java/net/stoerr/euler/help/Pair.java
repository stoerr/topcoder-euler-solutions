package net.stoerr.euler.help;

public class Pair<T1, T2> {

    public final T1 x;

    public final T2 y;

    public Pair(final T1 x, final T2 y) {
        this.x = x;
        this.y = y;
    }

    public static <T1, T2> Pair<T1, T2> make(final T1 x, final T2 y) {
        return new Pair<T1, T2>(x, y);
    }

    @Override
    public boolean equals(final Object arg0) {
        if (arg0 instanceof Pair) {
            final Pair o = (Pair) arg0;
            if (null == o) return false;
            return CompareUtils.safeEquals(x, o.x) && CompareUtils.safeEquals(y, o.y);
        } else return false;
    }

    @Override
    public int hashCode() {
        long hash = 42;
        if (null != x) {
            hash += x.hashCode() * 17;
        }
        if (null != y) {
            hash += y.hashCode() * 23;
        }

        return (int) (hash % (Integer.MAX_VALUE - 1));
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }

}
