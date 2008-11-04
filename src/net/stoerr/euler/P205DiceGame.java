package net.stoerr.euler;

import java.util.ArrayList;
import java.util.Arrays;

public class P205DiceGame {

    static int[] possibilities(int sides, int rounds) {
        int[] res = new int[37];
        res[0] = 1;
        for (int i = 0; i < rounds; ++i) {
            int[] v = res;
            res = new int[37];
            for (int k = 0; k < 37 - sides; ++k) {
                for (int j = 1; j <= sides; ++j) {
                    res[k+j] += v[k];
                }
            }
        }
        return res;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] colin = possibilities(6, 6);
        int[] pete = possibilities(4, 9);
        System.out.println(winprob(pete, colin));
        System.out.println(winprob(colin, pete));
    }

    private static double winprob(int[] pete, int[] colin) {
        long count=0;
        long win =0;
        for (int i = 0; i < colin.length; i++) {
            for (int j = 0; j < pete.length; j++) {
                final long pos = colin[i]*pete[j];
                if (j>i) {
                    win+= pos;
                }
                count += pos;
            }
        }
        return ((double)win)/count;
    }

}
