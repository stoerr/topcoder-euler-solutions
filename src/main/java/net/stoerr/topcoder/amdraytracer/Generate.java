package net.stoerr.topcoder.amdraytracer;

import java.util.*;
import java.security.*;

public class Generate {
    static double[] a, b, c, cx, cy, cz, lx, ly, lz;

    static int N, M;

    static double tol = 1.1;

    static boolean intersect(int i, int j) {
        if (cx[i] + a[i] < cx[j] - a[j])
            return false;
        if (cx[j] + a[j] < cx[i] - a[i])
            return false;
        if (cy[i] + b[i] < cy[j] - b[j])
            return false;
        if (cy[j] + b[j] < cy[i] - b[i])
            return false;
        if (cz[i] + c[i] < cz[j] - c[j])
            return false;
        if (cz[j] + c[j] < cz[i] - c[i])
            return false;
        for (double phi = -Math.PI / 2; phi < Math.PI / 2; phi += 0.05) {
            for (double theta = 0; theta < Math.PI * 2; theta += 0.05) {
                double x = cx[i] + a[i] * Math.cos(phi) * Math.cos(theta) * tol;
                double y = cy[i] + b[i] * Math.cos(phi) * Math.sin(theta) * tol;
                double z = cz[i] + c[i] * Math.sin(phi) * tol;
                double xx = (x - cx[j]) / a[j] / tol;
                double yy = (y - cy[j]) / b[j] / tol;
                double zz = (z - cz[j]) / c[j] / tol;
                if (xx * xx + yy * yy + zz * zz < 1)
                    return true;
            }
        }
        double x = cx[j];
        double y = cy[j];
        double z = cz[j];
        double xx = (x - cx[i]) / a[i] / tol;
        double yy = (y - cy[i]) / b[i] / tol;
        double zz = (z - cz[i]) / c[i] / tol;
        if (xx * xx + yy * yy + zz * zz < 1) {
            System.err.println("HERE");
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Random r = null;
        try {
            r = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            return;
        }
        r.setSeed((long)(Math.random()*Long.MAX_VALUE));

        N = r.nextInt(50) + 1;// ellipsoids
        M = r.nextInt(5) + 1;// lights
        // System.err.println(args[0]);
        System.out.printf("%d %d\n", N, M);
        a = new double[N];
        b = new double[N];
        c = new double[N];
        cx = new double[N];
        cy = new double[N];
        cz = new double[N];
        lx = new double[M];
        ly = new double[M];
        lz = new double[M];
        for (int i = 0; i < N; i++) {
            a[i] = r.nextDouble() * 90 + 10;
            b[i] = r.nextDouble() * 90 + 10;
            c[i] = r.nextDouble() * 90 + 10;
            cx[i] = r.nextDouble() * (1000 - 2 * a[i]) + a[i];
            cy[i] = r.nextDouble() * (1000 - 2 * b[i]) + b[i];
            cz[i] = r.nextDouble() * (500 - 2 * c[i]) + c[i];
            if (Math.abs(cx[i] - 500) + cz[i] + a[i] > 500
                    || Math.abs(cy[i] - 500) + cz[i] + b[i] > 500) {
                i--;
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (intersect(i, j)) {
                    i--;
                    continue;
                }
            }
        }
        for (int i = 0; i < M; i++) {
            lx[i] = r.nextDouble() * 1000;
            ly[i] = r.nextDouble() * 1000;
            lz[i] = r.nextDouble() * 300 + 200;
            for (int j = 0; j < N; j++) {
                double x = lx[i];
                double y = ly[i];
                double z = lz[i];
                double xx = (x - cx[j]) / a[j] / tol;
                double yy = (y - cy[j]) / b[j] / tol;
                double zz = (z - cz[j]) / c[j] / tol;
                if (xx * xx + yy * yy + zz * zz < 1) {
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            System.out.printf("%f %f %f %f %f %f\n", cx[i], cy[i], cz[i], a[i],
                    b[i], c[i]);
        }
        for (int i = 0; i < M; i++) {
            System.out.printf("%f %f %f\n", lx[i], ly[i], lz[i]);
        }
    }
}
