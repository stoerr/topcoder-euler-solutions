package net.stoerr.euler;

import static net.stoerr.euler.help.Modulo.mod;
import static net.stoerr.euler.help.Modulo.mult;
import static net.stoerr.euler.help.Modulo.solve;

import java.util.List;

import junit.framework.TestCase;

import net.stoerr.euler.help.Func2;

public class P223AlmostRightangledI extends TestCase {

    // final long maxumfang = 25000000L;
    final long maxumfang = 2500L;

    public void dtestSolve() {
        final Func2<List<Long>, Long, Boolean> solution = new Func2<List<Long>, Long, Boolean>() {
            public Boolean call(final List<Long> l, final Long m) {
                assertEquals("" + l, 3, l.size());
                final Long a = l.get(0);
                final Long b = l.get(1);
                final Long c = l.get(2);
                return 0 == mod(mult(a, a, m) + mult(b, b, m) - mult(c, c, m) - 1, m);
            }
        };
        final List<List<Long>> prelim = solve(3, 2048, solution);
        assertTrue(prelim.toString(), false);
    }

}
