package net.stoerr.topcoder.amdraytracer;

import static java.lang.Math.acos;
import static java.lang.Math.sin;
import static net.stoerr.topcoder.amdraytracer.Vec3Test.assertEq;
import junit.framework.TestCase;

public class GlassSurfaceTest extends TestCase {

    final GlassSurface s = new GlassSurface(2);

    public void testReflect() {
        assertEq(new Vec3(1, 1, 0), s.reflect(new Vec3(1, -1, 0), new Vec3(0, 1, 0)));
    }

    public void testRefract45() {
        Vec3 dir = new Vec3(1, -1, 0);
        Vec3 nrm = new Vec3(0, 1, 0);
        Vec3 v = s.refract(dir, nrm);
        Vec3Test.assertEq(new Vec3(0.353553390d, -0.935414346693d, 0), v);
    }

    public void testRefractm45() {
        Vec3 dir = new Vec3(1, -1, 0);
        Vec3 nrm = new Vec3(0, -1, 0);
        Vec3 v = s.refract(dir, nrm);
        assertNull(v);
    }

    public void testRefractm31() {
        Vec3 dir = new Vec3(1, -3, 0);
        Vec3 nrm = new Vec3(0, -1, 0);
        Vec3 v = s.refract(dir, nrm);
        Vec3Test.assertEq(new Vec3(0.632455532033675866d, -0.774596669241483377d, 0), v);
    }

    public void testRefract1() {
        Vec3 dir = new Vec3(10, -1, 0);
        Vec3 nrm = new Vec3(0, 1, 0);
        Vec3 v = s.refract(dir, nrm);
        assertEquals(2, sin(acos(dir.cosine(nrm))) / sin(acos(v.cosine(nrm))), 1e-6);
        assertTrue(0 < dir.scalar(v));
    }

    public void testRefract2() {
        Vec3 dir = new Vec3(1, 10, 0);
        Vec3 nrm = new Vec3(0, 1, 0);
        Vec3 v = s.refract(dir, nrm);
        assertEquals(0.5, sin(acos(dir.cosine(nrm))) / sin(acos(v.cosine(nrm))), 1e-6);
        assertTrue(0 < dir.scalar(v));
    }

    public void testRefract3() {
        for (int i = 0; i < 100; ++i) {
            Vec3 dir = Vec3.randomUnity();
            Vec3 nrm = Vec3.randomUnity();
            Vec3 v = s.refract(dir, nrm);
            if (null != v) {
                double n = sin(acos(dir.cosine(nrm))) / sin(acos(v.cosine(nrm)));
                assertTrue("" + n, 1e-6 > (n - 2) || 1e-6 > (n - 0.5));
                assertTrue(0 < dir.scalar(v));
            }
            double tc = s.transCoeff(dir, nrm, v);
            assertTrue("" + tc, 0 <= tc && tc <= 1);
        }
    }

    public void testRefract4() {
        Vec3 dir = new Vec3(0, 0, -1);
        Vec3 nrm = new Vec3(0, 0, -1);
        Vec3 v = s.refract(dir, nrm);
        Vec3Test.assertEq(dir, v);
    }

}
