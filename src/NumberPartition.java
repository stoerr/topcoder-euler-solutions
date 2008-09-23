/**
 * 
 */

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Lösung zu http://www.topcoder.com/stat?c=problem_statement&pm=9750
 * 
 * @author hps
 */
public class NumberPartition {

    /**
     * A partition of the number n is the set of numbers that all sum up to n.
     * For example, we have 5 different partitions of 4: {1, 1, 1, 1}, {1, 1,
     * 2}, {1, 3}, {2, 2}, {4}. If we sort all the numbers within each set, we
     * can treat each partition as a sorted list of numbers and so introduce a
     * lexicographic order on all partitions. For example, the partitions of 4
     * mentioned before are given in order. You are given ints n and k.
     * Determine all the partitions of n, sort them in lexicographical order,
     * and return a int[] containing the k-th one (k is a 1-based index). If
     * there are not enough partitions of n, return an empty int[].
     * 
     * @param n
     *            between 1 and 50, inclusive.
     * @param k
     *            between 1 and 1000000, inclusive.
     * @return not null
     */
    public int[] kthPartition(int n, int k) {
        List<Integer> res = new ArrayList<Integer>();
        cnt = k;
        res = doPartition(res, n, 1);
        if (null == res) return new int[0];
        int[] arrres = new int[res.size()];
        int c=0;
        for (int val : res) {
            arrres[c++] = val;
        }
        return arrres; 
    }
    
    int cnt;

    /**
     * Hängt an res sukzessive alle Partitionen von n an, die Werte von mindestens u hat; 
     * wenn eine komplette Zerlegung von n gefunden ist wird cnt dekrementiert und falls 
     * es 0 ist die Partition zurückgegeben. 
     * @param pref
     * @param n
     * @param u
     */
    private List<Integer> doPartition(List<Integer> pref, int n, int u) {
        if (0 == n) {
            int sum = 0;
            // System.out.println(pref);
            --cnt;
            if (0 == cnt) {
                return pref;
            } else {
                return null;
            }
        }
        if (u > n) {
            return null;
        }
        for (int v = u; v <= n; ++v) {
            pref.add(v);
            List<Integer> res = doPartition(pref, n-v, v);
            if (null != res) {
                return res;
            }
            pref.remove(pref.size()-1);
        }
        return null;
    }


    @Test
    public void testKthPartition() {
        verify(new int[] { 1, 1, 1, 1 }, kthPartition(4, 1));
        verify(new int[] { 1, 3 }, kthPartition(4, 3));
        verify(new int[] { 1, 1, 1, 3, 3, 3, 5 }, kthPartition(17, 123));
        verify(new int[] {}, kthPartition(12, 1234));
    }

    /**
     * Compare the arrays elementwise
     * @param is
     * @param is2
     */
    private void verify(int[] is, int[] is2) {
        assertEquals(Arrays.toString(is), Arrays.toString(is2));
    }

}
