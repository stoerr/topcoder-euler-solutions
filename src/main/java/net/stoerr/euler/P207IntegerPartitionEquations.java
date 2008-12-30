package net.stoerr.euler;

import net.stoerr.topcoder.amdraytracer.QuadraticEquation;

/**
 * Idee: Partition ist x*x-x-k = 0, alles longeger.
 * Partition ist "perfekt" wenn x eine Zweierpotenz ist.
 * P(m) ist anzahl "perfekter" Partitionen mit k<=m  
 * Wie gross ist kleinstes m so dass P(m) < 1/12345
 * @author hps
 */
public class P207IntegerPartitionEquations {
    
    static double p(long m) {
        long perfects = 0;
        long count =0;
        long x=1;
        while(true) {
            ++x;
            long k=x*x-x;
            if (k>m) break;
            if (isTwoPow(x)) perfects++;
            count++;
        }
        return perfects*1.0/count;
    }
    
    static long invp(double pmax) {
        long perfects = 0;
        long count =0;
        long x=1;
        while(true) {
            ++x;
            long k=x*x-x;
            if (isTwoPow(x)) perfects++;
            count++;
            final double p = perfects*1.0/count;
            if (p < pmax) return k;
        }
    }
    
    static boolean isTwoPow(long n) {
        if (1 > n ) return false;
        if (1 ==n) return true;
        if (0 != n%2) return false;
        return isTwoPow(n/2);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (long k=2; k<31; ++k) {
            QuadraticEquation eqn = new QuadraticEquation(1,-1,-k);
            final Double sol = eqn.leastPositiveSolution();
            final double p = p(k);
            System.out.println(k + "\t"+sol + "\t" + Math.log(sol)/Math.log(2) + "\t" + p + "\t" + invp(p));
        }
        System.out.println(invp(1.0/12345));
    }

}
