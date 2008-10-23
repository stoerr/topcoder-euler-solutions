package net.stoerr.euler;

import static net.stoerr.euler.help.PrimeUtils.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.stoerr.euler.help.PrimeUtils;

public class P001b024 extends Assert {
    @Test
    public void testP001() {
        int sum = 0;
        for (int i = 1; i < 1000; ++i) {
            if (0 == i % 3 || 0 == i % 5)
                sum += i;
        }
        assertEquals(233168, sum);
    }

    @Test
    public void testP002() {
        int p = 1;
        int t = 1;
        int h;
        int sum = 0;
        for (; t < 4000000; h = p, p = t, t = p + h) {
            if (0 == t % 2)
                sum += t;
        }
        assertEquals(4613732, sum);
    }

    @Test
    public void testP003() {
        int max = 0;
        for (int i = 110; i < 1000; i += 11) {
            for (int j = 100; j < 1000; j += 1) {
                int p = i * j;
                int prev = 0;
                while (0 < p) {
                    prev = 10 * prev + p % 10;
                    p = p / 10;
                }
                if (i * j == prev && prev > max) {
                    max = prev;
                }
            }
        }
        assertEquals(906609, max);
    }

    @Test
    public void testP004() {
        long p = 1;
        for (int i = 1; i <= 20; ++i) {
            p = kgv(i, p);
        }
        assertEquals(232792560L, p);
    }

    @Test
    public void testP005() {
        int sum = 0;
        int sq = 0;
        for (int i = 1; i <= 100; ++i) {
            sum += i;
            sq += i * i;
        }
        assertEquals(25164150, sum * sum - sq);
    }

    @Test
    public void testP006() {
        String s = "73167176531330624919225119674426574742355349194934"
                + "96983520312774506326239578318016984801869478851843"
                + "85861560789112949495459501737958331952853208805511"
                + "12540698747158523863050715693290963295227443043557"
                + "66896648950445244523161731856403098711121722383113"
                + "62229893423380308135336276614282806444486645238749"
                + "30358907296290491560440772390713810515859307960866"
                + "70172427121883998797908792274921901699720888093776"
                + "65727333001053367881220235421809751254540594752243"
                + "52584907711670556013604839586446706324415722155397"
                + "53697817977846174064955149290862569321978468622482"
                + "83972241375657056057490261407972968652414535100474"
                + "82166370484403199890008895243450658541227588666881"
                + "16427171479924442928230863465674813919123162824586"
                + "17866458359124566529476545682848912883142607690042"
                + "24219022671055626321111109370544217506941658960408"
                + "07198403850962455444362981230987879927244284909188"
                + "84580156166097919133875499200524063689912560717606"
                + "05886116467109405077541002256983155200055935729725"
                + "71636269561882670428252483600823257530420752963450";
        int prod = 1;
        int start = 0;
        int max = 0;
        for (int i = 0; i < s.length(); ++i) {
            final int d = s.charAt(i) - '0';
            prod *= d;
            if (prod > max)
                max = prod;
            if (0 == d) {
                start = i + 1;
                prod = 1;
            }
            if (i >= start + 4) {
                int dl = s.charAt(i - 4) - '0';
                prod = prod / (dl);
            }
        }
        assertEquals(40824, max);
    }

    @Test
    public void testP009() {
        for (int a = 1; a < 1000; ++a)
            for (int b = a + 1; b < 1000; ++b) {
                int c = 1000 - a - b;
                if (c*c==a*a+b*b) {
                    assertEquals(200,a);
                    assertEquals(375,b);
                    assertEquals(425,c);
                    assertEquals(31875000,a*b*c);
                }
            }
    }
    
    @Test
    public void testP010() {
        int max=2000000;
        List<Integer> primes = erathostenes(max);
        long sum=0;
        for (Integer p : primes) sum +=p;
        assertEquals(142913828922L,sum);
    }
    
    @Test public void testP012() {
        int i=1;
        int divs=0;
        int n=0;
        while(divs<=30) {
            ++i;
            n = i*(i+1)/2;
            divs = PrimeUtils.countDivisors(n);
            int divs2 = PrimeUtils.countDivisors(i)*PrimeUtils.countDivisors(i+1)/2;
            System.out.println(n + "\t" + divs + "\t" + divs2);
        }
        System.out.println(n + "\t" + divs);
    }
}
