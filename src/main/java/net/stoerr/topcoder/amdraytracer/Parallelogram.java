/* created by hps on 18.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.topcoder.amdraytracer;

/**
 * A parallelogram in 3dimensional space.
 *
 * @author hps
 * @since 18.09.2008
 */
public class Parallelogram implements Hitable {

    public final Vec3 origin;
    public final Vec3 xdir;
    public final Vec3 ydir;
    /** perpendicular on the surface; normalized. */
    public final Vec3 normal;

    /** perpendicular to xdir but in the parallelogram plane */
    private final Vec3 yperp;

    public Parallelogram(Vec3 origin, Vec3 xdir, Vec3 ydir) {
        this.origin = origin;
        this.xdir = xdir;
        this.ydir = ydir;
        this.normal = xdir.cross(ydir).normalized();
        yperp = ydir.cross(xdir).cross(xdir).normalized();
    }

    @Override
    public String toString() {
        return "Parallelogram{" + origin + "; x=" + xdir + "; y=" + ydir + "}";
    }

    /**
     * The point a ray hits the parallelogram - if it does.
     *
     * @param ray
     * @return the intersection point or null if it doesn't
     */
    public Vec3 hitpoint(Ray ray) {
        Vec3 originrel = ray.origin.subtract(origin);
        double raynorm = normal.scalar(ray.direction);
        double originnorm = normal.scalar(originrel);
        double distance = -originnorm / raynorm;
        if (0 > distance)
            return null;
        Vec3 hitpoint = ray.forward(distance);
        Vec2 c = coordinates(hitpoint);
        if (0 > c.x || 1 < c.x)
            return null;
        if (0 > c.y || 1 < c.y)
            return null;
        return hitpoint;
    }

    /**
     * The coordinates of a vector o in the plane. o must be in the plane - this
     * is not checked.
     */
    public Vec2 coordinates(Vec3 o) {
        Vec3 orelative = o.subtract(origin);
        double orelp = yperp.scalar(orelative);
        double ydirp = yperp.scalar(ydir);
        double ycoord = orelp / ydirp;
        double xcoord = orelative.subtract(ydir.scale(ycoord)).length() / xdir.length();
        return new Vec2(xcoord, ycoord);
    }

}
