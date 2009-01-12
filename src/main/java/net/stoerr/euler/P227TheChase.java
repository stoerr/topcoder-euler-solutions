package net.stoerr.euler;

import junit.framework.TestCase;

/**
 * http://projecteuler.net/index.php?section=problems&id=227
 * @author hps
 * @since 11.01.2009
 */
public class P227TheChase extends TestCase {

    /** Calculate sum(i=n,infty,i*q**(i-n)) */
    double sumNQN(final int n, final double q) {
        final double q1 = 1 - q;
        return (n - 1) / q1 + 1 / q1 / q1;
    }

    public void testSumNQN() {
        final int n = 3;
        final double q = 0.7;
        double s = 0;
        int i = n;
        double qf = 1;
        while (qf > 0.000000001) {
            s += i * qf;
            qf *= q;
            i++;
        }
        assertEquals(s, sumNQN(n, q), 0.00001);
    }

    final int N = 100;

    public void testChase() {
        double p[] = new double[N];
        p[0] = 1.0;
        double avg = 0;
        double pges = 0;
        double prunning = 1;
        for (int r = 1; r < 100000; ++r) {
            // System.out.println(CollectionUtils.asList(p));
            final double pn[] = new double[N];
            for (int i = 0; i < N; ++i) {
                pn[i] += p[i] * 2 / 3;
                pn[(i + 1) % N] += p[i] / 6;
                pn[(i + N - 1) % N] += p[i] / 6;
            }
            p = pn;
            double pfin = 0;
            for (int i = 0; i < N; ++i) {
                pfin += p[i] * p[(i + N / 2) % N];
            }
            pges += pfin * prunning;
            avg += r * pfin * prunning;
            prunning *= (1 - pfin);
            if (0 == r % 100) {
                System.out.println(r + "\t" + pfin + "\t" + prunning + "\t" + avg);
            }
        }
        System.out.println("avg=" + avg + " , pges=" + pges);
    }

}
