package net.stoerr.topcoder.amdraytracer;

/**
 * A theedimensional Ellipsoid.
 * 
 * @author hps
 * @since 18.09.2008
 */
public final class Ellipsoid {

    public final Vec3 center;
    public final Vec3 axes;

    public Ellipsoid(Vec3 center, Vec3 axes) {
        this.center = center;
        this.axes = axes;
    }

    @Override
    public String toString() {
        return "Ellipsoid{" + center + "; " + axes + "}";
    }
}
