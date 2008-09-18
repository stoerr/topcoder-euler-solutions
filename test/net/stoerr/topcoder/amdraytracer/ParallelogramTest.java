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
public class ParallelogramTest extends TestCase {

    public void testHitpoint() {
        final double delta = 1e-6;
        Vec3 origin = Vec3.randomUnity();
        Vec3 xdir = Vec3.randomUnity();
        Vec3 ydir = Vec3.randomUnity();
        double xfac = Math.random();
        double yfac = Math.random();

        Parallelogram par = new Parallelogram(origin, xdir, ydir);
        Vec3 hitpoint = origin.add(xdir.scale(xfac)).add(ydir.scale(yfac));
        Vec3 rori = hitpoint.add(par.normal);
        Ray ray = new Ray(rori, par.normal.scale(-1));
        Vec2 coord = par.coordinates(hitpoint);
        Vec3 isHitpoint = par.hitpoint(ray);
        assertEquals(xfac, coord.x, delta);
        assertEquals(yfac, coord.y, delta);
        assertEquals(0, isHitpoint.distance(hitpoint), delta);
    }

}
