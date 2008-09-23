package net.stoerr.topcoder.amdraytracer;

import junit.framework.TestCase;

public class TestEllipsoid extends TestCase {

    public void testHitpoint() {
        boolean success = false;
        for (int i = 0; i < 100; ++i) {
            Ellipsoid e = new Ellipsoid(Vec3.randomUnity(), new Vec3(Math
                    .random(), Math.random(), Math.random()));
            Ray r = new Ray(new Vec3(-2, -3, -4), new Vec3(Math.random(), Math
                    .random(), Math.random()));
            Vec3 p = e.hitpoint(r);
            if (null != p) {
                success = true;
                assertEquals(0, e.distance(p), 1e-6);
            }
        }
        assertTrue(success);
    }

    public void testH() {
        Ellipsoid e = new Ellipsoid(new Vec3(2, 2, 0), new Vec3(1, 1, 1));
        Ray r = new Ray(new Vec3(-2, -2, 0), new Vec3(1, 1, 0));
        Vec3 p = e.hitpoint(r);
        assertEquals(0, e.distance(p), 1e-6);
    }

}
