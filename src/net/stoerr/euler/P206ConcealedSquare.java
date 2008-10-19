package net.stoerr.euler;

import java.util.regex.Pattern;

/**
 * Find the unique positive integer whose square has the form
 * 1_2_3_4_5_6_7_8_9_0, where each “_” is a single digit.
 */
public class P206ConcealedSquare {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Pattern sqpat = Pattern.compile(".*6.7.8.9");
        for (long i = 1; i < 10000000; ++i) {
            String sq = String.valueOf(i * i);
            if (sqpat.matcher(sq).matches()) {
                System.out.println(i + "\t" + i * i);
            }

        }
    }

}
