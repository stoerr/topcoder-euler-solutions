package net.stoerr.functional.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.stoerr.functional.util.Iterators;
import junit.framework.TestCase;

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
}
