package net.stoerr.functional.backus;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;
import static net.stoerr.functional.backus.Lists.*;

/**
 * Tests for {@link Lists}.
 * @author hps
 * @since 26.11.2008
 */
public class TestLists extends TestCase {

    public void testAppend() {
        Function l = L(1,2,3);
        Function tst = cn(l,l.c(TAIL)).c(APPEND);
        assertEquals("<1, 2, 3, 2, 3>", tst.call(Value.BOTTOM).asList().toString());
    }

}
