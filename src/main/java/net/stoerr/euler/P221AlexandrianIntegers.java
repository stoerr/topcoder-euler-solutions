package net.stoerr.euler;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.stoerr.euler.help.Modulo;

/**
 * run4: 30000 : 460 (0.016 s) <br>
 * 300000 : 2006 (0.125 s)<br>
 * 3000000 : 8127 (1.047 s)<br>
 * run6: 300000 : 2008 (1.094 s) 3000000 : 8132 (10.642 s) <br>
 * run7: 3000000 : 8127 (0.609 s) 30000000 : 31442 (6.03 s)
 * @author hps
 * @since 04.01.2009
 */
public class P221AlexandrianIntegers {

    Map<Number, String> res = Collections.synchronizedMap(new TreeMap<Number, String>());
    // final long max = 700000000L;
    final long max = 700000000L;

    /**
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        new P221AlexandrianIntegers().run();
    }

    private void run() throws InterruptedException {
        final long begin = System.currentTimeMillis();
        run7();
        int n = 0;
        for (final Map.Entry<Number, String> e : res.entrySet()) {
            ++n;
            if (n < 20 || (149997 < n && 150005 > n)) {
                System.out.println(n + ":\t" + e.getValue());
            }
        }
        System.out.println(max + " : " + n + "\t (" + 0.001 * (System.currentTimeMillis() - begin) + " s)");
        System.out.flush();
        System.err.flush();
        Thread.sleep(1000);
    }

    private void run7() {
        for (double b = 2; b <= max; ++b) {
            for (double c = 2; c <= b && b * c <= max; ++c) {
                if (b + c - 1 == (b * c) % (b + c)) {
                    final double a = (b * c + 1) / (b - c);
                    final double A = b * c * a;
                    res.put(A, b + "\t" + c + "\t" + a + "\t" + A);
                }
            }
        }
    }

    private void run6() throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        for (long bx = 2; bx <= max; ++bx) {
            final long b = bx;
            executor.execute(new Runnable() {
                public void run() {
                    // TODO Auto-generated method stub
                    for (long c = 2; c <= b && c <= max / b + 1; ++c) {
                        if (b + c - 1 == Modulo.mult(b, c, b + c)) {
                            final BigInteger bi = BigInteger.valueOf(b);
                            final BigInteger ci = BigInteger.valueOf(c);
                            final BigInteger a = bi.multiply(ci).add(BigInteger.valueOf(1)).divide(
                                    BigInteger.valueOf(b - c));
                            final BigInteger A = bi.multiply(ci).multiply(a);
                            res.put(A, b + "\t" + c + "\t" + a + "\t" + A);
                        }
                    }
                    if (0 == b % 1000) {
                        System.out.println("..." + b);
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    private void run5() {
        for (long b = 2; b <= max; ++b) {
            for (long c = 2; c <= b && c <= max / b + 1; ++c) {
                if (b + c - 1 == Modulo.mult(b, c, b + c)) {
                    final BigInteger bi = BigInteger.valueOf(b);
                    final BigInteger ci = BigInteger.valueOf(c);
                    final BigInteger a = bi.multiply(ci).add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(b - c));
                    final BigInteger A = bi.multiply(ci).multiply(a);
                    res.put(A, b + "\t" + c + "\t" + a + "\t" + A);
                }
            }
        }
    }

    private void run4() {
        for (long b = 2; b <= max; ++b) {
            for (long c = 2; c <= b && b * c <= max; ++c) {
                if (b + c - 1 == (b * c) % (b + c)) {
                    final long a = (b * c + 1) / (b - c);
                    final long A = b * c * a;
                    res.put(BigInteger.valueOf(A), b + "\t" + c + "\t" + a + "\t" + A);
                }
            }
        }
    }

    private void run1() {
        for (long a = 2; a < max; ++a) {
            for (long b = 2; b < a; ++b) {
                if (1 == (a * b) % (a + b)) {
                    final long c = (a * b - 1) / (a + b);
                    final long A = a * b * c;
                    res.put(BigInteger.valueOf(A), (a + b) + "\t" + a + "\t" + b + "\t" + c + "\t" + A + "\t" + A * 1.0
                            / a / a / a);
                }
            }
        }
    }

    private void run2() {
        for (long b = 2; b < max; ++b) {
            for (long c = 2; c < b; ++c) {
                if (b + c - 1 == (b * c) % (b + c)) {
                    final long a = (b * c + 1) / (b - c);
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
                final long a = s / 2 + d;
                final long b = s - a;
                if (1 == (a * b) % (a + b)) {
                    final long c = (a * b - 1) / (a + b);
                    A = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).multiply(BigInteger.valueOf(c));
                    res.put(A, (a + b) + "\t" + a + "\t" + b + "\t" + c + "\t" + A);
                    System.out.println(a + b);
                }
            }
        }
    }

}
