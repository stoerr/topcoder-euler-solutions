package net.stoerr.euler.help;

import java.math.BigInteger;

import junit.framework.TestCase;
import static net.stoerr.euler.help.Modulo.*;

public class TestModulo extends TestCase {

    public void testModLongLong() {
        assertEquals(13434 % 9382, mod(13434, 9382));
        assertEquals(-13434 + 9382 + 9382, mod(-13434, 9382));
    }

    public void testModDoubleLong() {
        assertEquals(-13434 + 9382 + 9382, mod(-13434.0, 9382));
    }

    public void testMult() {
        assertEquals((8 * 15) % 17, mult(8, 15, 17));
        long a = 94833492;
        long b = 38239232;
        long m = 88232124;
        assertEquals(BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(m)).longValue(),
                mult(a, b, m));
    }

}
