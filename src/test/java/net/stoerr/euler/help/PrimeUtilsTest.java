package net.stoerr.euler.help;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class PrimeUtilsTest extends PrimeUtils {

    @Test
    public void testGCD() {
        assertEquals(3L,gcd(6,9));
    }
    
    @Test
    public void testFactor() {
        long n=5*7*7*17;
        List<Long> l = factor(n);
        assertEquals(4,l.size());
        assertEquals(n, CollectionUtils.multiply(l));
    }
}
