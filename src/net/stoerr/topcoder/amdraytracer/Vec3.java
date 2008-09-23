package net.stoerr.topcoder.amdraytracer;

import static java.lang.Math.*;

/**
 * A threedimensional vector.
 * 
 * @author hps
 * @since 18.09.2008
 */
public final class Vec3 {

    public final double x;

    public final double y;

    public final double z;

    private final boolean normalized;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.normalized = false;
    }

    private Vec3(double x, double y, double z, boolean normalized) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.normalized = normalized;
    }

    public double length() {
        return sqrt(this.scalar(this));
    }

    public Vec3 normalized() {
        if (normalized)
            return this;
        double len = length();
        return new Vec3(x / len, y / len, z / len, true);
    }

    public Vec3 add(final Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 subtract(final Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    public double distance(final Vec3 other) {
        return subtract(other).length();
    }

    public Vec3 scale(double factor) {
        return new Vec3(factor * x, factor * y, factor * z);
    }

    public double scalar(final Vec3 o) {
        return x * o.x + y * o.y + z * o.z;
    }

    /** a2b3 − a3b2, a3b1 − a1b3, a1b2 − a2b1). */
    public Vec3 cross(final Vec3 o) {
        return new Vec3(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x);
    }

    /**
     * Creates a unity vector into a random direction. <br>
     * FIXME this is certainly wrong. 8-}
     */
    public static Vec3 randomUnity() {
        // return new Vec3(random() - 0.5, random() - 0.5, random() -
        // 0.5).normalized();
        double phi = asin(2 * Math.random() - 1);
        double theta = 2 * PI * Math.random();
        return new Vec3(cos(phi) * cos(theta), cos(phi) * sin(theta), sin(phi));
    }

    /** cosine of the angle between the vectors */
    public double cosine(final Vec3 o) {
        double cosine = this.scalar(o) / (this.length() * o.length());
        return cosine;
    }

    public double angle(final Vec3 o) {
        return acos(cosine(o));
    }

    /** Projection of this vector into the direction of base. */
    public Vec3 project(Vec3 base) {
        return base.scale(base.scalar(this) / base.scalar(base));
    }

    @Override
    public String toString() {
        return "Vec3{" + x + ", " + y + ", " + z + "}";
    }

}
