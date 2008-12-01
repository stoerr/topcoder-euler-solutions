package net.stoerr.functional.backus;

import static net.stoerr.functional.backus.Combinators.cn;
import static net.stoerr.functional.backus.DebugFunctional.UNLAZY;
import static net.stoerr.functional.backus.Lists.APPEND;
import static net.stoerr.functional.backus.Lists.L;
import static net.stoerr.functional.backus.Lists.TAIL;
import static net.stoerr.functional.backus.Lists.TRANSPOSE;
import junit.framework.TestCase;

/**
 * Tests for {@link Lists}.
 * @author hps
 * @since 26.11.2008
 */
public class TestLists extends TestCase {

    public void testAppend() {
        Function l = L(1, 2, 3);
        assertEquals("<1, 2, 3>", l.call(Value.BOTTOM).asList().toString());
        Function tst = cn(l, l.c(TAIL)).c(APPEND);
        assertEquals("<1, 2, 3, 2, 3>", tst.call(Value.BOTTOM).asList().toString());
    }

    public void testTranspose() {
        Function l = cn(L(1, 2, 3), L(5, 6, 7));
        assertEquals("<<1, 5>, <2, 6>, <3, 7>>", l.c(TRANSPOSE).call(Value.BOTTOM).asList().toString());
        assertEquals("<<1, 2, 3>, <5, 6, 7>>", l.c(UNLAZY).call(Value.BOTTOM).asList().toString());
    }

}
