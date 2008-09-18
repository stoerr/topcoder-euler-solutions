package net.stoerr.topcoder.amdraytracer;

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

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double length() {
        return Math.sqrt(this.scalar(this));
    }

    public Vec3 normalized() {
        double len = length();
        return new Vec3(x / len, y / len, z / len);
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
        return new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).normalized();
    }

    /** cosine of the angle between the vectors */
    public double cosine(final Vec3 o) {
        double cosine = this.scalar(o) / (this.length() * o.length());
        return cosine;
    }

    public double angle(final Vec3 o) {
        return Math.acos(cosine(o));
    }

    @Override
    public String toString() {
        return "Vec3{" + x + ", " + y + ", " + z + "}";
    }

}
