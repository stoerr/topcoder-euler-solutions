package net.stoerr.euler;

public class P027Primequadratic {

    public static void main(String[] args) {
        new P027Primequadratic().run();
    }

    int nmax = -1;
    int amax = -1;
    int bmax = -1;

    private void run() {
        for (int a = 0; a < 10000; ++a) {
            for (int b = 0; b < 10000; ++b) {
                chk(a, b);
                chk(a, -b);
                chk(-a, b);
                chk(-a, -b);
            }
        }
        System.out
                .println(nmax + ":" + amax + "," + bmax + " - " + amax * bmax);
    }

    /**
     * @param a
     * @param b
     * @return
     */
    private int chk(int a, int b) {
        int n;
        for (n = 0; isPrime(n * n + a * n + b); ++n);
        if (n > nmax) {
            System.out.println(n + ":" + a + "," + b);
            nmax = n;
            amax = a;
            bmax = b;
        }
        return n;
    }

    private boolean isPrime(int p) {
        if (p<2) return false;
        int mdiv = (int) Math.sqrt(p + 1);
        for (int i = 2; i < mdiv; ++i) {
            if (0 == p % i)
                return false;
        }
        return true;
    }

}
