/**
 * 
 */
package net.stoerr.euler.help;

import static net.stoerr.euler.help.Combinatorics.*;
import junit.framework.TestCase;

/**
 * @author hps
 *
 */
public class CombinatoricsTest extends TestCase {

    /**
     * Test method for {@link net.stoerr.euler.help.Combinatorics#fac(long)}.
     */
    public void testFac() {
        assertEquals(6,fac(3));
    }

    /**
     * Test method for {@link net.stoerr.euler.help.Combinatorics#over(long, long)}.
     */
    public void testOver() {
        assertEquals(fac(5)/fac(2)/fac(3), over(5,2));
    }

}
