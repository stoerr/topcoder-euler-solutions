package net.stoerr.topcoder.amdraytracer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class RayTracer implements RaytracerExamples {

    public double[] render(String[] ellipsoids, String[] lights) {
        throw new UnsupportedOperationException(); // FIXME
    }

    public static final int XS = 1000;

    public static final int YS = 1000;

    public static final double MIN_INTENSITY = 0.01;

    public static final int MAXDEPTH = 5;

    public final MappedParallelogram screen = new MappedParallelogram(new Vec3(0, 0, 0), new Vec3(1000, 0, 0), new Vec3(
            0, 1000, 0), XS, YS);

    public final Vec3 view = new Vec3(500, 500, 500);

    public final List<Vec3> lights = new ArrayList<Vec3>();

    public final List<Ellipsoid> objects = new ArrayList<Ellipsoid>();

    public final GlassSurface surface = new GlassSurface(2);

    /** screen */
    double[][] a;
    /** camera */
    double[][] c;

    public RayTracer(double a[][]) {
        /*
         * lights.add(new Vec3(0, 0, 500)); objects.add(new Ellipsoid(new
         * Vec3(0, 0, 101), new Vec3(100, 100, 100)));
         */
        lights.addAll(EX4_LIGHTS);
        objects.addAll(EX4_OBJS);
        this.a = a;
    }

    /** Ein Erleuchtungslauf; einige Dutzend sind nötig. */
    public void lightScreen() {
        for (int i = 0; i < XS * YS; ++i) {
            for (Iterator<Vec3> iter = lights.iterator(); iter.hasNext();) {
                Vec3 l = iter.next();
                Vec3 dir = Vec3.randomUnity();
                Ray r = new Ray(l, dir);
                PMC2<Vec3, Double> block = new PMC2<Vec3, Double>() {
                    public void execute(Vec3 hit, Double intensity) {
                        IVec2 coords = screen.icoordinates(hit);
                        a[coords.x][coords.y] += intensity;
                    }
                };
                trace(r, 1, block, MAXDEPTH);
            }
        }
    }

    /** Projektion auf die Cameraflaeche */
    public void snapshot() {
        c = new double[XS][XS];
        SnapshotBlock block = new SnapshotBlock();
        for (block.x = 0; block.x < XS; ++block.x) {
            for (block.y = 0; block.y < YS; ++block.y) {
                Vec3 p = screen.gridpoint(block.x, block.y);
                Ray r = new Ray(view, p.subtract(view));
                trace(r, 1, block, MAXDEPTH);
            }
        }
    }

    class SnapshotBlock implements PMC2<Vec3, Double> {
        int x;
        int y;

        public void execute(Vec3 p, Double intensity) {
            IVec2 hit = screen.icoordinates(p);
            c[x][y] += intensity * a[hit.x][hit.y];
        }
    }

    private void trace(Ray r, double intensity, PMC2<Vec3, Double> block, int maxdepth) {
        if (intensity < MIN_INTENSITY || maxdepth < 0)
            return;
        Vec3 hit = null;
        double dist = Double.MAX_VALUE;
        Ellipsoid hitOid = null;
        final Vec3 screenHit = screen.hitpoint(r);
        final double screenDist = r.distance(screenHit);
        if (screenDist < dist) {
            hit = screenHit;
            dist = screenDist;
        }
        for (Ellipsoid obj : objects) {
            Vec3 thisHit = obj.hitpoint(r);
            double thisDist = r.distance(thisHit);
            if (thisDist < dist) {
                hit = thisHit;
                dist = thisDist;
                hitOid = obj;
            }
        }
        if (null != hit) {
            if (screenDist == dist) {
                block.execute(hit, intensity);
            } else { // hit on a ellipsoid
                @SuppressWarnings("null")
                Vec3 normal = hitOid.normal(hit);
                Vec3 reflect = surface.reflect(r.direction, normal);
                Vec3 refract = surface.refract(r.direction, normal);
                double trans = surface.transCoeff(r.direction, normal, refract);
                trace(new Ray(hit, reflect), intensity * (1 - trans), block, maxdepth - 1);
                if (null != refract) {
                    trace(new Ray(hit, refract), intensity * trans, block, maxdepth - 1);
                }
            }
        }
    }

}
