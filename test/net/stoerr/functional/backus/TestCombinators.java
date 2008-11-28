package net.stoerr.functional.backus;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;
import static net.stoerr.functional.backus.Lists.*;

public class TestCombinators extends TestCase {

    public final void testFixpoint() {
        Function app12 = bindFirst(APPEND, V(1,2));
        // assertEquals("<1, 2, 5, 8>", app12.call(V(5,8)).asList().toString());
        Function tst = fixpoint(app12);
        final ListObject res = tst.call(Value.BOTTOM).asList();
        assertEquals("<1, 2, 1, 2, 1, 2, 1, 2, 1, 2, ...>", res.toString());
    }
    
    public final void testApply() {
        Function tst = cn(L(1,2),L(5,7)).c(apply(PLUS));
        final ListObject res = tst.c(UNLAZY).call(Value.BOTTOM).asList();
        assertEquals("<3.0, 12.0>", res.toString());
    }
    
    public final void testFibonacci() {
        Function app11 = bindFirst(APPEND, V(1,1));
        Function fibonacci = app11.c(cn(IDENTITY, TAIL).c(TRANSPOSE).c(apply(PLUS)));
        Function tst = fixpoint(fibonacci);
        // final ListObject res = c(fibonacci).call(Value.BOTTOM).asList();
        final ListObject res = fibonacci.c(UNLAZY).call(V(3,5)).asList();
        assertEquals("", res.toString());
    }
}
