package net.stoerr.euler.help;

public final class Modulo {

    private static final long b16 = 65536;

    public static long mod(long x, long m) {
        if (0 <= x) {
            return x % m;
        } else {
            return m - (-x % m);
        }
    }

    public static long mod(double x, long m) {
        double d = Math.floor(x / m);
        return Math.round(x - d * m);
    }

    public static long mult(long a, long b, long m) {
        long al = a % b16;
        long ah = a / b16;
        long bl = b % b16;
        long bh = b / b16;
        long res = mod(ah * bh, m);
        res = mod(b16 * (double) res + al * bh + bl * ah, m);
        res = mod(b16 * (double) res + al * bl, m);
        return res;
    }

}
