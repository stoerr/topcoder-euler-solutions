package net.stoerr.topcoder.amdraytracer;

import static java.lang.Math.*;

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
        double n = 1 / relrefraction;
        if (0 > cos) {
            n = relrefraction;
            normal = normal.scale(-1);
        }
        double c = n * n + cos * cos - 1;
        if (0 > c)
            return null;
        double f = sqrt(c) + cos;
        return direction.add(normal.scale(f)).normalized();
    }

    /**
     * Transmission coefficient according to Fresnel equations
     * http://en.wikipedia.org/wiki/Fresnel_equations
     * 
     * @param dir
     *            direction of incoming ray
     * @param normal
     *            normal of surface
     * @param refract
     *            direction of refracted ray or null if total reflection
     * @return transmission coefficient
     */
    public double transCoeff(Vec3 dir, Vec3 normal, Vec3 refract) {
        if (null == refract) {
            return 0;
        }
        double c1 = abs(dir.cosine(normal));
        double c2 = abs(refract.cosine(normal));
        double rs = (relrefraction * c1 - c2) / (relrefraction * c1 + c2);
        rs = rs * rs;
        double rp = (relrefraction * c2 - c1) / (relrefraction * c2 + c1);
        rp = rp * rp;
        return 1 - (rs + rp) / 2;
    }

}
