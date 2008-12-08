package net.stoerr.functional.backus;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;

public class TestNumerics extends TestCase {

    public void testNegate() {
        Function f = compose(NEGATE, ONE);
        assertEquals(-1.0, f.call(Value.BOTTOM).get());
    }

    public void testPlus() {
        Function f = compose(PLUS, cn(ONE, ONE));
        assertEquals(2.0, f.call(Value.BOTTOM).get());
    }

    public void testVarious() {
        Function dup = cn(IDENTITY, IDENTITY);
        // Function f = compose(TIMES, compose(dup, compose(PLUS, cn(ONE, ONE))));
        Function f = cn(ONE, ONE).c(PLUS).c(dup).c(TIMES);
        assertEquals(4.0, f.call(Value.BOTTOM).get());
    }
}
