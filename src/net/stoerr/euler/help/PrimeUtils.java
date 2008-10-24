package net.stoerr.euler.help;

import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilities, die im Verhältnis zu Primzahlen stehen.
 */
public class PrimeUtils {
    public static long gcd(long a, long b) {
        if (0 > a)
            return gcd(-a, b);
        if (0 > b)
            return gcd(a, -b);
        if (0 == a)
            return b;
        if (0 == b)
            return a;
        if (a < b)
            return gcd(b, a);
        return gcd(a % b, b);
    }

    public static long kgv(long a, long b) {
        if (0 == a || 0 == b)
            return 0;
        long gcd = gcd(a, b);
        return a / gcd * b;
    }

    /**
     * Liefert alle Primzahlen <max mit Erathostenes
     */
    public static List<Integer> erathostenes(int max) {
        boolean composite[] = new boolean[max];
        List<Integer> res = new ArrayList<Integer>();
        for (int i = 2; i < max; ++i) {
            if (!composite[i]) {
                res.add(i);
                for (int j = i + i; j < max; j += i)
                    composite[j] = true;
            }
        }
        return res;
    }

    /** Returns the list of prime factors of the number. */
    public List<Long> factor(long number) {
        if (number < 0)
            return factor(-number);
        if (number < 2)
            return Collections.emptyList();
        List<Long> res = new ArrayList<Long>();
        long div = number;
        long sqrdiv = (long) ceil(sqrt(div));
        long i = 2;
        while (div > 1 && i <= sqrdiv) {
            if (0 == div % i) {
                res.add(i);
                div = div / i;
                sqrdiv = (long) ceil(sqrt(div));
            } else {
                ++i;
            }
        }
        if (div>1) res.add(div);
        return res;
    }
    
    public static List<Long> divisors(long number) {
        if (number < 0) return divisors(number);
        if (number==0) return Collections.singletonList(1L);
        List<Long> res = new ArrayList<Long>();
        long i;
        for (i=1; i*i<number; ++i) {
            if (0 == number%i) {
                res.add(i);
                res.add(number/i);
            }
        }
        if (i*i==number) res.add(i);
        Collections.sort(res);
        return res;
    }
    
    /**
     * Number of divisors. May be speeded up by factorung.
     */
    public static int countDivisors(long number) {
        if (number < 0) return countDivisors(number);
        if (number==0) return 1;
        int count=0;
        long i;
        for (i=1; i*i < number; ++i) {
            if (0 == number%i) count +=2;
        }
        if (i*i == number) ++count;
        return count;
    }
}
