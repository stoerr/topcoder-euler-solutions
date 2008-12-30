import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * http://www.topcoder.com/stat?c=problem_statement&pm=8673
 * <p>
 * A particular star system consists of a single star, with N planets orbiting
 * it. Each planetary orbit is a perfect circle of distinct radius centered on
 * the star and all orbits lie in the same 2-D plane. The planets move along
 * their circular paths with constant speed and planet i completes 1 full orbit
 * in the time given by the absolute value of periods[i]. periods[i] will be
 * positive if planet i orbits clockwise and negative if it orbits
 * counter-clockwise. A k-planetary alignment occurs when an infinite straight
 * line exists, passing through the center of the star and through the centers
 * of at least k of the planets. A N-planetary alignment occurs at time T = 0,
 * i.e., all the planets lie in a line at this time (see notes for
 * clarification). Return the number of distinct times between T1 and T2,
 * inclusive, when a k-planetary alignment occurs.
 * 
 * <p>
 * Notes
 * <p> - The constraints ensure the return value will fit in a signed 32-bit
 * integer.
 * <P> - Alignments can occur with the planets either on the same side of the
 * star or diametrically opposite each other.
 * <P> - The configuration of the planets at T = 0 (i.e., whether the planets
 * are all on the same side, or some are diametrically opposite) makes no
 * difference to the answer.
 * <p>
 * 
 * Überlegung: zwei Planeten mit Perioden a > b stehen aller ab/2(a-b) auf einer
 * Linie. Sortieren wir die Koinzidenzen in ein Treeset ein; wo es k
 * überschreitet wird ein Resultat geliefert.
 * 
 * @author hps
 */
public class KPlanetaryAlignment {

    /**
     * @param periods
     *            will contain between 2 and 5 elements, inclusive. ach element
     *            of periods will be a non-zero integer between -100 and 100,
     *            inclusive. The elements of periods will be distinct.
     * @param k
     *            between 2 and the number of elements in periods, inclusive.
     * @param T1
     *            between 0 and T2, inclusive.
     * 
     * @param T2
     *            between 0 and 50,000,000 (5 * 10^7), inclusive.
     * 
     * @return number of k-meetings.
     */
    public int number(int[] periods, int k, long T1, long T2) {
        Arrays.sort(periods);
        List<Double> meetings = new ArrayList<Double>();
        long kgv = 1;
        for (int i = 0; i < periods.length; ++i) {
            for (int j = i + 1; j < periods.length; ++j) {
                kgv = kgv(kgv, periods[j] - periods[i]);
            }
        }
        System.out.println("kgv=" + kgv);
        for (int i=0; i<periods.length; ++i) {
            periods[i] *= kgv;
        }
        T1 *= kgv;
        T2 *= kgv;
        for (int i = 0; i < periods.length; ++i) {
            for (int j = i + 1; j < periods.length; ++j) {
                double t = 0;
                double per = commonPeriod(periods[i], periods[j]);
                while (t <= T2) {
                    if (t >= T1) {
                        meetings.add(t);
                    }
                    t += per;
                }
            }
        }
        Collections.sort(meetings);
        // System.out.println(meetings);
        int cnt = 0;
        int running = 0;
        double pos = -1e20;
        final double eps = 1e-6;
        for (double v : meetings) {
            if (v < pos + eps) {
                ++running;
            } else {
                if (running >= k - 2) {
                    ++cnt;
                }
                running = 0;
            }
            pos = v;
        }
        return cnt;
    }

    public static double commonPeriod(int a, int b) {
        return Math.abs((a * b * 0.5d) / (a - b));
    }

    public static long ggt(long a, long b) {
        if (a == 0) {
            return b;
        }
        if (a < b) {
            return ggt(b, a);
        }
        return ggt(a % b, b);
    }

    public static long kgv(long a, long b) {
        return Math.abs(a * b) / ggt(Math.abs(a), Math.abs(b));
    }

    public static long kgv(int[] vals) {
        long res = 1;
        for (int v : vals) {
            res = kgv(res, v);
        }
        return res;
    }
    

    public void testCalculation() {
        assertEquals(5, number(new int[] { 8, 40 }, 2, 0, 20));
        assertEquals(8, number(new int[] { 8, 24, 40 }, 2, 0, 20));
        assertEquals(4, number(new int[] { 8, 24, 40 }, 3, 0, 100));
        assertEquals(10, number(new int[] { -10, 10 }, 2, 2, 26));
        assertEquals(53332, number(new int[] { -1, 2, 3, 4, 5 }, 3, 10000,
                50000));
        assertEquals(200001, number(new int[] { -1, 1 }, 2, 0, 50000));
        assertEquals(1471, number(new int[] { -2, 91, 87, 77, 71 }, 4, 0,
                50000000));

    }
}
