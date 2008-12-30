package net.stoerr.euler.help;

public class CompareUtils {

    /**
     * Equals without NPE.
     * @param x
     * @param y
     * @return
     */
    public static boolean safeEquals(Object x, Object y) {
        if (null == x) return null == y;
        if (null == y) return false;
        return x.equals(y);
    }
    
}
