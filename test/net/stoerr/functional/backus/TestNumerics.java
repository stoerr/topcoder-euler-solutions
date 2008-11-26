package net.stoerr.functional.backus;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;

public class TestNumerics extends TestCase {

    public void testNegate() {
        Function f = compose(NEGATE, ONE);
        assertEquals(-1.0, f.call(Value.BOTTOM).get());
    }
    
}
