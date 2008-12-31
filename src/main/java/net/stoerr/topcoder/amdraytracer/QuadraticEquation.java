package net.stoerr.topcoder.amdraytracer;


/**
 * Solves a quadratic equation a*x*x+b*x+c==0.
 */
public final class QuadraticEquation {

    public final double a;

    public final double b;

    public final double c;

    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /** yields the least solution above {@link #eps}, or null if there is none. */
    public Double leastPositiveSolution() {
        double d = b * b - 4 * a * c;
        if (0 > d)
            return null;
        double l = Math.sqrt(d);
        double x1 = (-b - l) / (2 * a);
        double x2 = (-b + l) / (2 * a);
        if (eps < x1)
            return x1;
        if (eps < x2)
            return x2;
        return null;
    }

    private final double eps = 1e-6;
    
}
