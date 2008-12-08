package net.stoerr.topcoder.amdraytracer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public final class RayTracer implements RaytracerExamples {

    private static final int LIGHTSCREEN_CHUNKSIZE = 100000;
    public static final int XS = 1000;
    public static final int YS = 1000;

    final int NUM_THREADS = 1;

    final long MAXCALCTIME = 1000 * 60;
    final long MAXRAYS = 1000*XS*YS;


    public static final double MIN_INTENSITY = 0.01;

    public static final int MAXDEPTH = 50;

    public final MappedParallelogram screen = new MappedParallelogram(new Vec3(
            0, 0, 0), new Vec3(1000, 0, 0), new Vec3(0, 1000, 0), XS, YS);

    public final Vec3 view = new Vec3(500, 500, 500);

    public final List<Vec3> lights = new ArrayList<Vec3>();

    public final List<Ellipsoid> objects = new ArrayList<Ellipsoid>();

    public final GlassSurface surface = new GlassSurface(2);

    Exception exception = null;

    /** screen */
    double[][] a;

    /** camera */
    double[][] c;

    long rays = 0;

    long begintime;

    synchronized void incrementRays(long val) {
        rays += val;
    }

    public RayTracer(double a[][], double[][] c) {
        lights.add(new Vec3(0, 0, 500));
        lights.add(new Vec3(0, 1000, 500));
        lights.add(new Vec3(1000, 0, 500));
        lights.add(new Vec3(1000, 1000, 500));
        /*
         * objects.add(new Ellipsoid(new Vec3(500, 500, 200), new Vec3(100, 200,
         * 300)));
         */
        // lights.addAll(EX0_LIGHTS); objects.addAll(EX0_OBJS);
        for (int i = 0; i < 100; ++i) {
            objects.add(new Ellipsoid(new Vec3(1000 * Math.random(), 1000 * Math.random(), 200 * Math.random()),
                    new Vec3(
                    50, 50, 50)));
        }
        this.a = a;
        this.c = c;
    }

    public RayTracer() {
        a = new double[XS][YS];
        c = new double[XS][YS];
        lights.clear();
        objects.clear();
        rays = 0;
    }

    int lightscreencnt = 0;

    /** {@link #MAXCALCTIME} langer Erleuchtungslauf */
    public void lightScreen() {
        while (System.currentTimeMillis() < begintime + MAXCALCTIME && rays < MAXRAYS) {
            for (int i = 0; i < LIGHTSCREEN_CHUNKSIZE; ++i) {
                for (Iterator<Vec3> iter = lights.iterator(); iter.hasNext();) {
                    Vec3 l = iter.next();
                    Vec3 dir = Vec3.randomUnity();
                    Ray r = new Ray(l, dir);
                    PMC2<Vec3, Double> block = new PMC2<Vec3, Double>() {
                        public void execute(Vec3 hit, Double intensity) {
                            IVec2 coords = screen.icoordinates(hit);
                            a[coords.x][coords.y] += intensity;
                            lightscreencnt++;
                        }
                    };
                    try {
                        trace(r, 1, block, MAXDEPTH);
                    } catch (RuntimeException e) {
                        exception = e;
                    }
                }
            }
            incrementRays(LIGHTSCREEN_CHUNKSIZE);
        }
        System.out.println("lightscreencount = " + lightscreencnt);
    }

    int snapshotcnt = 0;

    /** Projektion auf die Cameraflaeche */
    public void snapshot(int num, int count) {
        SnapshotBlock block = new SnapshotBlock();
        for (block.x = num; block.x < XS; block.x += count) {
            for (block.y = 0; block.y < YS; ++block.y) {
                Vec3 p = screen.gridpoint(block.x, block.y);
                Ray r = new Ray(view, p.subtract(view));
                snapshotcnt++;
                try {
                    trace(r, 1, block, MAXDEPTH);
                } catch (RuntimeException e) {
                    exception = e;
                }
            }
        }
        System.out.println("Snapshotcount " + snapshotcnt);
    }

    class SnapshotBlock implements PMC2<Vec3, Double> {
        int x;

        int y;

        public void execute(Vec3 p, Double intensity) {
            IVec2 hit = screen.icoordinates(p);
            // TODO interpolation
            c[x][y] += intensity * a[hit.x][hit.y];
            snapshotcnt++;
        }
    }

    private void trace(Ray r, double intensity, PMC2<Vec3, Double> block,
            int maxdepth) {
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
                trace(new Ray(hit, reflect), intensity * (1 - trans), block,
                        maxdepth - 1);
                if (null != refract) {
                    trace(new Ray(hit, refract), intensity * trans, block,
                            maxdepth - 1);
                }
            }
        }
    }

    /**
     * Running average on the lines and rows of a.
     */
    private void smoothScreen() {
        final int width = (int) Math.round(Math.sqrt(XS * XS * 1000.0d / rays));
        System.out.println("Smooth factor " + width + " becayse of " + rays
                + " rays.");
        if (1 >= width) return;
        int x = 0;
        int y = 0;
        try {
            for (x = 0; x < XS; ++x) {
                double sum = 0;
                int cnt = 0;
                for (y = 0; y < YS; ++y) {
                    sum += a[x][y];
                    cnt++;
                    if (width < cnt || y >= YS - width) {
                        cnt--;
                        sum -= a[x][y - cnt];
                    }
                    c[x][y - cnt / 2] = sum / cnt;
                }
            }
            for (y = 0; y < XS; ++y) {
                double sum = 0;
                int cnt = 0;
                for (x = 0; x < YS; ++x) {
                    sum += c[x][y];
                    cnt++;
                    if (width < cnt || x >= XS - width) {
                        cnt--;
                        sum -= c[x - cnt][y];
                    }
                    a[x - cnt / 2][y] = sum / cnt;
                }
            }
            for (x = 0; x < XS; ++x) {
                for (y = 0; y < YS; ++y) {
                    c[x][y] = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public String getStatusText() {
        return rays + " rays done.";
    }

    CyclicBarrier barrier;

    abstract class ParmThread extends Thread {
        int i;

        int j;
    }

    /**
     * The complete rendering process.
     */
    public void doRendering() throws Exception {
        begintime = System.currentTimeMillis();
        long beg;
        long en;
        barrier = new CyclicBarrier(NUM_THREADS + 1);
        beg = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; ++i) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        lightScreen();
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        barrier.await();
        en = System.currentTimeMillis();
        System.out.println("lighting " + 0.001 * (en - beg));
        smoothScreen();
        smoothScreen();
        beg = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; ++i) {
            ParmThread t = new ParmThread() {
                @Override
                public void run() {
                    try {
                        snapshot(i, j);
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.i = i;
            t.j = NUM_THREADS;
            t.start();
        }
        barrier.await();
        en = System.currentTimeMillis();
        System.out.println("Snapshot time " + 0.001 * (en - beg));
    }

    public double[] render(String[] ellipsoids, String[] thelights) {
        try {
            for (String el : ellipsoids) {
                Ellipsoid e = new Ellipsoid(el);
                objects.add(e);
            }
            for (String li : thelights) {
                Vec3 l = new Vec3(li);
                this.lights.add(l);
            }
            doRendering();
            if (null != exception)
                exception.printStackTrace();
            double[] res = new double[XS * YS];
            int i = 0;
            for (int x = 0; x < XS; ++x) {
                for (int y = 0; y < YS; ++y) {
                    res[i++] = c[x][y];
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
