package net.stoerr.euler.help;

import static net.stoerr.euler.help.Modulo.lift;
import static net.stoerr.euler.help.Modulo.mod;
import static net.stoerr.euler.help.Modulo.mult;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class TestModulo extends TestCase {

    public void testModLongLong() {
        assertEquals(13434 % 9382, mod(13434, 9382));
        assertEquals(-13434 + 9382 + 9382, mod(-13434, 9382));
    }

    public void testModDoubleLong() {
        assertEquals(-13434 + 9382 + 9382, mod(-13434.0, 9382));
    }

    public void testMult() {
        assertEquals((8 * 15) % 17, mult(8, 15, 17));
        final long a = 94833492;
        final long b = 38239232;
        final long m = 88232124;
        assertEquals(BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(m)).longValue(),
                mult(a, b, m));
    }

    public void testLift() {
        final List<Long> seed = Arrays.asList(new Long[] { 0L, 0L, 0L });
        final Func2<List<Long>, Long, Boolean> pred = new Func2<List<Long>, Long, Boolean>() {
            public Boolean call(final List<Long> arg, final Long l) {
                return true;
            }
        };
        final List<List<Long>> seedList = Collections.singletonList(seed);
        final List<List<Long>> res = lift(seedList, 1, 4, pred);
        assertEquals(res.toString(), 64, res.size());
    }

}
