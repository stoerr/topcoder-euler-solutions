package net.stoerr.topcoder.amdraytracer;

/**
 * A theedimensional Ellipsoid.
 * 
 * @author hps
 * @since 18.09.2008
 */
public final class Ellipsoid implements Hitable {

    public final Vec3 center;

    public final Vec3 axes;
    
    private final double max;

    public Ellipsoid(Vec3 center, Vec3 axes) {
        this.center = center;
        this.axes = axes;
        assert axes.x > 0;
        assert axes.y > 0;
        assert axes.z > 0;
        max = Math.max(Math.max(axes.x, axes.y),axes.z);
    }

    /**
     * For example 355.555813 667.041444 173.665646 12.305219 59.773426
     * 89.020176
     */
    public Ellipsoid(String descr) {
        String[] vals = descr.split("\\s");
        double v1 = Double.valueOf(vals[0]);
        double v2 = Double.valueOf(vals[1]);
        double v3 = Double.valueOf(vals[2]);
        double v4 = Double.valueOf(vals[3]);
        double v5 = Double.valueOf(vals[4]);
        double v6 = Double.valueOf(vals[5]);
        center = new Vec3(v1, v2, v3);
        axes = new Vec3(v4, v5, v6);
        assert axes.x > 0;
        assert axes.y > 0;
        assert axes.z > 0;
        max = Math.max(Math.max(axes.x, axes.y),axes.z);
    }

    @Override
    public String toString() {
        return "Ellipsoid{" + center + "; " + axes + "}";
    }

    /** Measure of the distance of v from the surface - relative to the axes. */
    public double distance(Vec3 v) {
        Vec3 v1 = v.subtract(center);
        return v1.x * v1.x / (axes.x * axes.x) + v1.y * v1.y / (axes.y * axes.y) + v1.z * v1.z / (axes.z * axes.z) - 1;
    }

    public Vec3 hitpoint(Ray ray) {
        Vec3 o = ray.origin.subtract(center);
        Vec3 d = ray.direction;
            
        double a = d.x * d.x / (axes.x * axes.x) + d.y * d.y / (axes.y * axes.y) + d.z * d.z / (axes.z * axes.z);
        double b = 2 * (d.x * o.x / (axes.x * axes.x) + d.y * o.y / (axes.y * axes.y) + d.z * o.z / (axes.z * axes.z));
        double c = o.x * o.x / (axes.x * axes.x) + o.y * o.y / (axes.y * axes.y) + o.z * o.z / (axes.z * axes.z) - 1;
        QuadraticEquation q = new QuadraticEquation(a, b, c);
        Double t = q.leastPositiveSolution();
        if (null == t)
            return null;
        return ray.forward(t);
    }

    /** surface normal at hitpoint */
    public Vec3 normal(Vec3 hitpoint) {
        Vec3 dif = hitpoint.subtract(center);
        return new Vec3(dif.x / axes.x, dif.y / axes.y, dif.z / axes.z).normalized();
    }

}
