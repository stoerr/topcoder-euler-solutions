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
}
