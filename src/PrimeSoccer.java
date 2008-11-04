import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;

public class PrimeSoccer {
    
    final int intervals = 90/5;

    public double getProbability(int skillOfTeamA, int skillOfTeamB) {
        double p1 = prob(skillOfTeamA/100.0);
        double p2 = prob(skillOfTeamB/100.0);
        return 1 - (1 - p1) * (1 - p2);
    }

    double prob(double skill) {
        int[] primes = new int[] {
                2,3,5,7,11,13,17
        };
        double res = 0;
        for (int i = 0; i < primes.length; i++) {
            int goals = primes[i];
            double goalp = Math.pow(skill, goals) * Math.pow(1-skill, intervals-goals) * noverk(intervals, goals);
            res += goalp;
        }
        return res;
    }
    
    public double noverk(int n, int k) {
        double res= fac(n)/fac(k)/fac(n-k);
        return res;
    }
    
    double fac(int n) {
        double res=1;
        for(int i=2; i<=n; ++i) res *= i;
        return res;
    }

    // BEGIN KAWIGIEDIT TESTING
    // Generated by KawigiEdit 2.1.4 (beta) modified by pivanof
    private static boolean KawigiEdit_RunTest(int testNum, int p0, int p1,
            boolean hasAnswer, double p2) {
        System.out.print("Test " + testNum + ": [" + p0 + "," + p1);
        System.out.println("]");
        PrimeSoccer obj;
        double answer;
        obj = new PrimeSoccer();
        long startTime = System.currentTimeMillis();
        answer = obj.getProbability(p0, p1);
        long endTime = System.currentTimeMillis();
        boolean res;
        res = true;
        System.out.println("Time: " + (endTime - startTime) / 1000.0
                + " seconds");
        if (hasAnswer) {
            System.out.println("Desired answer:");
            System.out.println("\t" + p2);
        }
        System.out.println("Your answer:");
        System.out.println("\t" + answer);
        if (hasAnswer) {
            res = Math.abs(p2 - answer) <= 1e-9 * Math.max(1.0, Math.abs(p2));
        }
        if (!res) {
            System.out.println("DOESN'T MATCH!!!!");
        } else if ((endTime - startTime) / 1000.0 >= 2) {
            System.out.println("FAIL the timeout");
            res = false;
        } else if (hasAnswer) {
            System.out.println("Match :-)");
        } else {
            System.out.println("OK, but is it right?");
        }
        System.out.println("");
        return res;
    }

    public static void main(String[] args) {
        boolean all_right;
        all_right = true;

        int p0;
        int p1;
        double p2;

        // ----- test 0 -----
        p0 = 50;
        p1 = 50;
        p2 = 0.5265618908306351D;
        all_right = KawigiEdit_RunTest(0, p0, p1, true, p2) && all_right;
        // ------------------

        // ----- test 1 -----
        p0 = 100;
        p1 = 100;
        p2 = 0.0D;
        all_right = KawigiEdit_RunTest(1, p0, p1, true, p2) && all_right;
        // ------------------

        // ----- test 2 -----
        p0 = 12;
        p1 = 89;
        p2 = 0.6772047168840167D;
        all_right = KawigiEdit_RunTest(2, p0, p1, true, p2) && all_right;
        // ------------------

        if (all_right) {
            System.out
                    .println("You're a stud (at least on the example cases)!");
        } else {
            System.out.println("Some of the test cases had errors.");
        }
    }
    // END KAWIGIEDIT TESTING
}
// Powered by KawigiEdit 2.1.4 (beta) modified by pivanof!
