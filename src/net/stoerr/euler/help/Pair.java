package net.stoerr.euler.help;

public class Pair<T1, T2> {

    public final T1 x;

    public final T2 y;

    public Pair(final T1 x, final T2 y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof Pair) {
            Pair o = (Pair) arg0;
            if (null == o)
                return false;
            return CompareUtils.safeEquals(x, o.x)
                    && CompareUtils.safeEquals(y, o.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        long hash = 42;
        if (null != x) hash += x.hashCode()*17;
        if (null != y) hash += y.hashCode()*23;
        
        return (int) (hash%(Integer.MAX_VALUE-1)) ;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }

}
