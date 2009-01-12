package net.stoerr.euler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import net.stoerr.euler.help.CollectionUtils;
import net.stoerr.euler.help.PrimeUtils;

public class P026b049 extends TestCase {

    public void testP026() {
        int maxcycle = -1;
        for (int i = 2; i < 1000; ++i) {
            int rest = 1;
            int stelle = 0;
            final Map<Integer, Integer> valToi = new HashMap<Integer, Integer>();
            while (0 != rest && !valToi.containsKey(rest)) {
                valToi.put(rest, stelle);
                ++stelle;
                rest = (10 * rest) % i;
            }
            if (0 != rest) {
                final int cycle = stelle - valToi.get(rest);
                if (cycle > maxcycle) {
                    // System.out.println(i+ "\t"+cycle);
                    maxcycle = cycle;
                }
            }
        }
        assertEquals(982, maxcycle);
    }

    public void testP030() {
        long sum = 0;
        for (int i = 2; i < 1000000; ++i) {
            final String s = "" + i;
            long digsum = 0;
            for (final char c : s.toCharArray()) {
                final int d = c - '0';
                digsum += d * d * d * d * d;
            }
            if (i == digsum) {
                sum += i;
            }
        }
        assertEquals(443839, sum);
    }

    public void testP033() {
        int num = 1;
        int den = 1;
        for (int j = 1; j <= 9; ++j) {
            for (int i = 1; i < j; ++i) {
                // System.out.println(i+""+j);
                for (int d = 1; d <= 9; d++) {
                    final int n1 = i + 10 * d;
                    final int n2 = 10 * i + d;
                    final int d1 = j + 10 * d;
                    final int d2 = 10 * j + d;
                    if (n1 * j == d1 * i || n2 * j == d1 * i || n1 * j == d2 * i || n2 * j == d2 * i) {
                        System.out.println(i + "," + j + "," + d);
                        num *= i;
                        den *= j;
                    }
                }
            }
        }
        final long gcd = PrimeUtils.gcd(num, den);
        assertEquals(100L, den / gcd);
    }

    /** Distinct terms a**b for 2<=a,b<=100 */
    public void testP029() {
        final Set<List<Long>> s = new HashSet<List<Long>>();
        final int N = 100;
        for (int a = 2; a <= N; ++a) {
            final List<Long> fa = PrimeUtils.factor(a);
            for (int b = 2; b <= N; ++b) {
                final List<Long> fab = CollectionUtils.nAppends(fa, b);
                Collections.sort(fab);
                s.add(fab);
            }
        }
        // System.out.println(s);
        assertEquals(9183, s.size());
    }

}
