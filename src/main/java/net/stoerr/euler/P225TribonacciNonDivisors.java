package net.stoerr.euler;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class P225TribonacciNonDivisors extends TestCase {

    public boolean tribonaccinondiv(long i1, long i2, long i3, long m) {
        Set<String> have = new HashSet<String>();
        Boolean res = null;
        while (null == res) {
            String trip = i1 + "/" + i2 + "/" + i3;
            if (have.contains(trip)) {
                res = Boolean.TRUE;
            } else {
                have.add(trip);
                long in = (i1 + i2 + i3) % m;
                if (0 != in) {
                    i1 = i2;
                    i2 = i3;
                    i3 = in;
                } else {
                    res = Boolean.FALSE;
                }
            }
        }
        return res;
    }
    
    public void testNondivs() {
        int cnt = 0;
        for (int i = 1; i < 3000; i += 2) {
            if (tribonaccinondiv(1, 1, 1, i)) {
                cnt++;
                System.out.println(cnt + "nondiv: " + i);
            }
        }
    }

}
