package net.stoerr.euler.help;

import java.math.BigInteger;

public class Combinatorics {

    public static long fac(long n) {
        long res = 1;
        for (long i = 2; i <= n; ++i) {
            res *= i;
        }
        return res;
    }

    public static long over(long n, long m) {
        if (m > n || m < 0 || n <= 0)
            return 0;
        long res = 1;
        for (long i = n - m + 1; i <= n; ++i) {
            res *= i;
        }
        res /= fac(m);
        return res;
    }

    public static BigInteger overB(long n, long m) {
        if (m > n || m < 0 || n <= 0)
            return new BigInteger("0");
        BigInteger res = new BigInteger("1");
        for (long i = n - m + 1; i <= n; ++i) {
            res = res.multiply(new BigInteger(""+i));
        }
        for (long i=2; i<=m; ++i) {
            res = res.divide(new BigInteger(""+i));
        }
        return res;
    }

}
