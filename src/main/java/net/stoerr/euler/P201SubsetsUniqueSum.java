package net.stoerr.euler;

/**
 * TODO no idea how this goes.
 * http://projecteuler.net/index.php?section=problems&id=201
 */
public class P201SubsetsUniqueSum {

    /**
     * @param args
     */
    public static void main(String[] args) {
        squareSumSearch();
    }

    private static void squareSumSearch() {
        for (int a = 2; a < 100; ++a) {
            for (int b = 1; b < a; ++b) {
                for (int c = 1; c < a; ++c) {
                    for (int d = c + 1; d < a; ++d) {
                        if (a * a + b * b == c * c + d * d) {
                            System.out.println(a + "\t" + b + "\t" + c + "\t"
                                    + d);
                        }
                    }
                }
            }
        }
    }

}
