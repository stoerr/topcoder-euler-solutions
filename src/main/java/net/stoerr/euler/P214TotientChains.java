package net.stoerr.euler;

import java.util.List;

import net.stoerr.euler.help.Cached;
import net.stoerr.euler.help.Func;
import net.stoerr.euler.help.Pair;
import net.stoerr.euler.help.PrimeUtils;

public class P214TotientChains {

    /** */
    public static void main(String[] args) {
        new P214TotientChains().run(40000000);
    }

    final Func<Pair<Long, Long>, Long> gcdfunc = new Cached<Pair<Long, Long>, Long>() {
        @Override
        protected Long impl(Pair<Long, Long> arg) {
            Long a1 = arg.x;
            Long a2 = arg.y;
            if (a2 > a1) return call(new Pair<Long, Long>(a2, a1));
            if (a2 == 1) return 1L;
            if (a2 == 0) return a1;
            return call(new Pair<Long, Long>(a2, a1 % a2));
        }
    };

    final Func<Long, Long> totient = new Cached<Long, Long>() {
        @Override
        protected Long impl(Long arg) {
            long sum = 0;
            for (long i = 1; i <= arg; ++i) {
                // if (1 == gcdfunc.call(new Pair<Long, Long>(arg, i))) ++sum;
                if (1 == PrimeUtils.gcd(arg, i)) ++sum;
            }
            return sum;
        }
    };

    int chainlength(long l) {
        int len = 1;
        while (l > 1) {
            l = totient.call(l);
            ++len;
        }
        return len;
    }

    private void run(int i) {
        // System.out.println(gcdfunc.call(new Pair<Long, Long>(6L,9L)));
        // System.out.println(totient(7L));
        System.out.println(chainlength(7));
        List<Integer> primes = PrimeUtils.erathostenes(i);
        // System.out.println(primes);
        System.out.println(primes.size());
        System.out.flush();
        long sum = 0;
        for (Integer p : primes) {
            System.out.println(p);
            if (25 == chainlength(p.longValue())) sum += p;
        }
        System.out.println(sum);
    }

}
