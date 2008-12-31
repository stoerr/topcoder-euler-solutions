/* created by hps on 18.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.topcoder.amdraytracer;

/**
 * A mathematical Ray: origin and a normalized direction.
 * 
 * @author hps
 * @since 18.09.2008
 */
public class Ray {

    public final Vec3 origin;
    public final Vec3 direction;

    public Ray(final Vec3 origin, final Vec3 direction) {
        this.origin = origin;
        this.direction = direction.normalized();
    }

    /** The point distance after {@link #origin} in {@link #direction} */
    public Vec3 forward(double distance) {
        return origin.add(direction.scale(distance));
    }

    @Override
    public String toString() {
        return "Ray{" + origin + " => " + direction + "}";
    }

    /** Distance of the point hit to the origin. Assumed to be on ray. If null, we return Double.MAX_VALUE. */
    public double distance(Vec3 hit) {
        if (null != hit) {
            return hit.subtract(origin).length();
        }
        return Double.MAX_VALUE;
    }

}
