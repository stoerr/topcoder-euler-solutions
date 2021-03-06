import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;

public class CoolNumber {
    public int count(int A, int B) {
        if (A > B)
            return count(B, A);
        int res = 0;
        for (int i = A; i <= B; ++i) {
            boolean isCool = cool(i);
            if (isCool)
                ++res;
        }
        return res;
    }

    private boolean cool(int num) {
        String s = String.valueOf(num);
        List<Integer> digits = new LinkedList<Integer>();
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int d = Integer.parseInt(""+s.charAt(i));
            digits.add(d);
            sum += d;
        }
        if (1 == sum % 2)
            return false;
        sum = sum / 2;
        Collections.sort(digits);
        Collections.reverse(digits);
        boolean res = partition(digits, sum);
        return res;
    }

    private boolean partition(List<Integer> digits, int sum) {
        if (0 == sum)
            return true;
        if (0 > sum)
            return false;
        if (digits.isEmpty())
            return false;
        List<Integer> copy = new LinkedList<Integer>(digits);
        for (Integer d : digits) {
            if (d <= sum && d > 0) {
                copy.remove(d);
                if (partition(copy, sum - d))
                    return true;
                copy.add(d);
            }
        }
        return false;
    }

    // BEGIN KAWIGIEDIT TESTING
    // Generated by KawigiEdit 2.1.4 (beta) modified by pivanof
    private static boolean KawigiEdit_RunTest(int testNum, int p0, int p1,
            boolean hasAnswer, int p2) {
        System.out.print("Test " + testNum + ": [" + p0 + "," + p1);
        System.out.println("]");
        CoolNumber obj;
        int answer;
        obj = new CoolNumber();
        long startTime = System.currentTimeMillis();
        answer = obj.count(p0, p1);
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
            res = answer == p2;
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
        int p2;

        // ----- test 0 -----
        p0 = 1;
        p1 = 50;
        p2 = 4;
        all_right = KawigiEdit_RunTest(0, p0, p1, true, p2) && all_right;
        // ------------------
        
        // ----- test 1 -----
        p0 = 1;
        p1 = 1000;
        p2 = 135;
        all_right = KawigiEdit_RunTest(1, p0, p1, true, p2) && all_right;
        // ------------------

        // ----- test 2 -----
        p0 = 6354;
        p1 = 234363;
        p2 = 82340;
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
