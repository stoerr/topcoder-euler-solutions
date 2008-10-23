package net.stoerr.euler;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Find the unique positive integer whose square has the form
 * 1_2_3_4_5_6_7_8_9_0, where each “_” is a single digit. <br>
 * Last digits are 00; we determine the rest.
 */
public class P206ConcealedSquare {
    
    static long maxf = (long)Math.ceil(Math.sqrt(19293949596979899.0))+1;
    static long minf = (long)Math.floor(Math.sqrt(10203040506070809.0))-1;
    /**
     * @param args
     */
    public static void main(String[] args) {
        Pattern sqpat = Pattern.compile("1.2.3.4.5.6.7.8.9");
        for (long i = minf; i < maxf; ++i) {
            String sq = String.valueOf(i * i);
            if (sqpat.matcher(sq).matches()) {
                System.out.println(i + "\t" + i * i);
            }

        }
    }

}
