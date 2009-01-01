package net.stoerr.functional.backus;

import java.math.BigInteger;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;
import static net.stoerr.functional.backus.Lists.*;
import static net.stoerr.functional.backus.DebugFunctional.*;

/**
 * http://projecteuler.net/index.php?section=problems&id=215
 */
public class TestP215CrackFreeWalls extends TestCase {

    Function start = L(1.0);
    Function extend = cn(cn(IDENTITY, apply(bindFirst(TIMES, 4).c(bindFirst(PLUS, 1)))).c(MERGEUNIQUE),
            apply(bindFirst(TIMES, 8).c(bindFirst(PLUS, 1)))).c(MERGEUNIQUE);
    Function lines = start.c(extend).c(extend).c(extend).c(extend).c(extend).c(filter(between(512, 1024)));

    public void testExtend() {
        final Function f1 = start.c(extend).c(extend);
        assertEquals("<1.0, 5.0, 9.0, 21.0, 37.0, 41.0, 73.0>", f1.c(UNLAZY).call(Value.BOTTOM).asList().toString());
        final Function filtered = f1.c(filter(between(8, 25)));
        assertEquals("<9.0, 21.0>", filtered.c(UNLAZY).call(Value.BOTTOM).asList().toString());
    }

    public void testLines() {
        ListObject res = lines.c(UNLAZY).call(Value.BOTTOM).asList();
        assertEquals(5, res.size());
        assertEquals("<585.0, 597.0, 661.0, 677.0, 681.0>", res.toString());
        for (int i = 0; i < res.size(); ++i) {
            double val = res.get(i).asDouble();
            System.out.println(BigInteger.valueOf(Math.round(val)).toString(2));
        }
    }

}
