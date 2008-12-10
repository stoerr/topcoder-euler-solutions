package net.stoerr.functional.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.stoerr.functional.util.Iterators;
import junit.framework.TestCase;
import static net.stoerr.functional.util.Iterators.*;

/**
 * Tests for {@link Iterators}
 * 
 * @author hps
 * @since 27.11.2008
 */
public class TestIterators extends TestCase {

    public final void testConcatIteratorOfIteratorOfT() {
        Iterator<Integer>[] its = new Iterator[] { Arrays.asList(new Integer[] { 7, 3, 8 }).iterator(),
                Arrays.asList(new Integer[] { 5, 17 }).iterator(), };
        Iterator<Iterator<Integer>> superit = Arrays.asList(its).iterator();
        Iterator<Integer> it = Iterators.concat(superit);
        List<Integer> col = Iterators.delayedList(it);
        assertEquals(Arrays.asList(new Integer[] { 7, 3, 8, 5, 17 }).toString(), col.toString());
    }

    public final void testMerge() {
        List<Integer> a2 = Arrays.asList(new Integer[] { 2, 3, 7 });
        List<Integer> a1 = Arrays.asList(new Integer[] { 1, 3, 5 });
        {
            Iterator<Integer> it = Iterators.merge(a1.iterator(), a2.iterator(), false);
            List<Integer> res = Iterators.collection(it);
            assertEquals(Arrays.asList(new Integer[] { 1, 2, 3, 3, 5, 7 }).toString(), res.toString());
        }
        {
            Iterator<Integer> it = Iterators.merge(a1.iterator(), a2.iterator(), true);
            List<Integer> res = Iterators.collection(it);
            assertEquals(Arrays.asList(new Integer[] { 1, 2, 3, 5, 7 }).toString(), res.toString());

        }
    }

    public final void testFilter() {
        List<Integer> a = Arrays.asList(new Integer[] { 3, 7, 1, 2, 3, 4, 5, 7, 3 });
        F<Integer, Boolean> pred = new F<Integer, Boolean>() {
            public Boolean call(Integer arg) {
                return 3 <= arg && arg <= 5;
            }
        };
        Iterator<Integer> it = filter(a.iterator(), pred);
        List<Integer> res = Iterators.collection(it);
        assertEquals(Arrays.asList(new Integer[] { 3, 3, 4, 5, 3 }).toString(), res.toString());
    }
}
