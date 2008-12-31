package net.stoerr.euler;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class P221AlexandrianIntegers {

    Map<BigInteger, String> res = new TreeMap<BigInteger, String>();
    final long max = 30;

    /** */
    public static void main(String[] args) {
        new P221AlexandrianIntegers().run();
    }

    private void run() {
        long begin = System.currentTimeMillis();
        run3();
        int n = 0;
        for (Map.Entry<BigInteger, String> e : res.entrySet()) {
            ++n;
            if (n < 20 || (149997 < n && 150005 > n)) {
                System.out.println(n + ":\t" + e.getValue());
            }
        }
        System.out.println(n);
        System.out.println(0.001*(System.currentTimeMillis() - begin));
    }

    private void run1() {
        for (long a = 2; a < max; ++a) {
            for (long b = 2; b < a; ++b) {
                if (1 == (a * b) % (a + b)) {
                    long c = (a * b - 1) / (a + b);
                    final long A = a * b * c;
                    res.put(BigInteger.valueOf(A), (a + b) + "\t" + a + "\t" + b + "\t" + c + "\t" + A + "\t" + A * 1.0 / a / a / a);
                }
            }
        }
    }

    private void run2() {
        for (long b = 2; b < max; ++b) {
            for (long c = 2; c < b; ++c) {
                if (b + c - 1 == (b * c) % (b + c)) {
                    long a = (b * c + 1) / (b - c);
                    final long A = b * c * a;
                    res.put(BigInteger.valueOf(A), b + "\t" + c + "\t" + a + "\t" + A);
                }
            }
        }
    }

    private void run3() {
        BigInteger A = null;
        for (long s = 2; true; ++s) {
            // System.out.println(s + "\t" + res.size() + "\t" + A);
            if (res.size() > max) return;
            for (long d = 1; d <= s / 2; ++d) {
                long a = s / 2 + d;
                long b = s - a;
                if (1 == (a * b) % (a + b)) {
                    long c = (a * b - 1) / (a + b);
                    A = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).multiply(BigInteger.valueOf(c));
                    res.put(A, (a + b) + "\t" + a + "\t" + b + "\t" + c + "\t" + A);
                    System.out.println(a+b);
                }
            }
        }
    }
}
