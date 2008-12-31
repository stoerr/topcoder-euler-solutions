package net.stoerr.euler.help;

import java.util.Arrays;

import junit.framework.TestCase;

public class PermutationsTest extends TestCase {

    public void testPermutate() {
        final Ref<Integer> count = new Ref<Integer>();
        count.v=0;
        String a[] = new String[]{"0","1","2"};
        Permutations.permutate(a, new Block<String[]>() {

            public void exec(String[] arg) {
                // System.out.println(Arrays.asList(arg));
                count.v++;
            }
            
        });
        assertEquals(6,count.v.intValue());
    }

}
