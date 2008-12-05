package net.stoerr.functional.backus;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;
import static net.stoerr.functional.backus.Lists.*;
import static net.stoerr.functional.backus.DebugFunctional.*;

/**
 * http://projecteuler.net/index.php?section=problems&id=215
 */
public class TestP215CrackFreeWalls extends TestCase {

    Function start = L(1);
    Function extend = cn(IDENTITY, apply(bindFirst(TIMES, 4).c(bindFirst(PLUS, 1))),
            apply(bindFirst(TIMES, 8).c(bindFirst(PLUS, 1)))).c(APPEND);

    public void testP215() {
        final Function f1 = start.c(extend).c(extend);
        final ListObject res = f1.c(UNLAZY).call(Value.BOTTOM).asList();
        assertEquals("<1, 5.0, 9.0, 5.0, 21.0, 37.0, 9.0, 41.0, 73.0>", res.toString());
        
    }

}
