/* created by hps on 18.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.topcoder.amdraytracer;

import junit.framework.TestCase;

/**
 * @author hps
 * @since 18.09.2008
 */
public class Vec3Test extends TestCase {

    public final void testCross() {
        final double delta = 1e-6;
        Vec3 v1 = Vec3.randomUnity();
        Vec3 v2 = Vec3.randomUnity();
        Vec3 v1v2 = v1.cross(v2);
        Vec3 v2v1 = v2.cross(v1);
        assertEquals(0, v1v2.scalar(v1), delta);
        assertEquals(0, v1v2.scalar(v2), delta);
        assertEquals(0, v2v1.scalar(v1), delta);
        assertEquals(0, v2v1.scalar(v2), delta);
        assertEquals(0, v2.cross(v2).length(), delta);
        assertEquals(0, v1.cross(v1).length(), delta);
        assertEquals(0, v2v1.scale(-1).distance(v1v2), delta);
    }
}
