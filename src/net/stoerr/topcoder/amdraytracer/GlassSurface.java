package net.stoerr.topcoder.amdraytracer;

/**
 * A glass surface. <br>
 * 
 * x2=sqrt(n*n+x1*x1-1)
 */
public final class GlassSurface {

    public final double relrefraction;

    /**
     * @param relrefraction
     *            n1/n2
     */
    public GlassSurface(double relrefraction) {
        this.relrefraction = relrefraction;
    }

    public Vec3 reflect(Vec3 direction, Vec3 normal) {
        return direction.add(direction.project(normal).scale(-2));
    }

    public Vec3 refract(Vec3 direction, Vec3 normal) {
        normal = normal.normalized();
        direction = direction.normalized();
        double cos = normal.cosine(direction);
        double n = relrefraction;
        if (0 < cos) {
            n = 1 / relrefraction;
        }
        double c = n * n + cos * cos - 1;
        if (0 > c)
            return null;
        double f = Math.sqrt(c) + cos;
        return direction.add(normal.scale(-f)).normalized();
    }

}
