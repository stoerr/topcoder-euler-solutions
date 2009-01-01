package net.stoerr.topcoder.amdraytracer;

import junit.framework.TestCase;


public class TestQuadraticEquation extends TestCase {
    
    public void testQuadraticEquation() {
        boolean solved = false;
        for (int i=0; i<100; ++i) {
            double a = Math.random()-0.5;
            double b = Math.random()-0.5;
            double c = Math.random()-0.5;
            QuadraticEquation q = new QuadraticEquation(a,b,c);
            Double x = q.leastPositiveSolution();
            if (null != x) {
                solved = true;
                assertEquals(0, a*x*x+b*x+c, 1e-6);
                assertTrue(1e-6 < x);
            }
        }
        assertTrue(solved);
    }

}
