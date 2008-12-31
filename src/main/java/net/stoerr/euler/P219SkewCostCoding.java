package net.stoerr.euler;

import net.stoerr.euler.help.Func;

/**
 * Theorie: mittlerer Informationsgehalt bei 1-Wahrscheinlichkeit p eines Bits ist -p*ld(p)-(1-p)*ld(1-p) ; mittlere
 * Kosten sind c*p+(1-p). Der Code ist optimal wenn die mittleren Kosten pro Informationsgehalt am niedrigsten sind.
 * @author hps
 * @since 29.12.2008
 */
public class P219SkewCostCoding implements Func<Double, Double> {

    private double c = 4;

    /** info per cost (bit per cent) */
    public Double call(Double p) {
        if (0.0 >= p || 1.0 <= p) return 0.0;
        double info = -p * ld(p) - (1 - p) * ld(1 - p);
        double cost = c*p+(1-p);
        return info/cost;
    }
    
    /** the average cost needed to transmit the selection of one of n items. */
    public double totalcost(double p, double n) {
        Double cp = call(p);
        double info = ld(n);
        return info/cp;
    }

    /** -bit aus Wahrscheinlichkeit. */
    private double ld(double d) {
        return Math.log(d) / Math.log(2);
    }
    
    public void testPrint() {
        double p = 0;
        while (p<1) {
            p += 0.05;
            System.out.println(p +"\t"+call(p));
        }
        p = findMax(0.5, 0.618);
        Double cp = call(p);
        System.out.println(p +"\t"+cp);
        System.out.println(totalcost(p, 6)*6);
        final double tc = totalcost(p, 1e9)*1e9;
        System.out.println(tc);
        System.out.println((long)-Math.floor(-tc));
    }
    
    public double findMax(double x, double dx) {
        double mv = call(x);
        double mx = x;
        double nv = call(x+dx);
        if (nv > mv) {
            mv = nv; mx = x+dx;
        }
        nv = call(x-dx);
        if (nv > mv) {
            mv = nv; mx = x-dx;
        }
        if (dx + x != x) {
            return findMax(mx, dx*0.618);
        }
        return mx;
    }
    
    public static void main(String[] args) {
        new P219SkewCostCoding().testPrint();
    }

}
