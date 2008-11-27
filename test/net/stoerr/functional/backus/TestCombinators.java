package net.stoerr.functional.backus;

import junit.framework.TestCase;
import static net.stoerr.functional.backus.Combinators.*;
import static net.stoerr.functional.backus.Numerics.*;
import static net.stoerr.functional.backus.Lists.*;

public class TestCombinators extends TestCase {

    public final void testFixpoint() {
        Function app12 = bindFirst(APPEND, new ImmediateValue(new ImmediateList(1,2)));
        assertEquals("<1, 2, 3, 2, 3>", app12.call(new ImmediateValue(new ImmediateList(5,8))).asList().toString());         
    }
}
