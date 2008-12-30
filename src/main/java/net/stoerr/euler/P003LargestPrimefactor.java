package net.stoerr.euler;


/**
 * Find the largest prime factor of 600851475143 
 * @author hps
 *
 */
public class P003LargestPrimefactor {
    
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(largestFactor(13195));
        System.out.println(largestFactor(600851475143L));
    }

    private static long largestFactor(long l) {
        if (l<4) return l;
        double sql = Math.sqrt(l)+0.1;
        int i=2;
        while (i<sql) {
            if (0 == l%i) {
                l = l/i;
                sql = Math.sqrt(l)+0.1;
            } else {
                ++i;
            }
        }
        return l;
    }

}
