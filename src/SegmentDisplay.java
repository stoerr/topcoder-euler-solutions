/* created by hps on 25.08.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 9fk4ldo
 * <p>
 * You have a digital display with an infinite number of positions. Each
 * position consists of 7 segments - 4 vertical and 3 horizontal. Lighting
 * certain segments within a position allows you to represent different decimal
 * digits on the display:
 * <p>
 * <p>
 * You can use the display to represent different non-negative integers. To
 * represent an integer you need to show its digits, in order, on consecutive
 * display positions. The integers you represent on the display cannot have
 * extra leading zeros.
 * <p>
 * 2: 1; 3: 7, 4: 4; 5: 2,3,5; 6: 0,6,9, 7: 8
 * <p>
 * You are given an int n, and your task is to count each integer that can be
 * represented by lighting exactly n segments. Return this count modulo
 * 1,000,000,007.
 * 
 * @author hps
 * @since 25.08.2008
 */
public class SegmentDisplay {

    /**
     * @param n
     * @return between 0 and 500000, inclusive.
     */
    public int howManyNumbers(final int n) {
        return 0;
    }

    @Test
    public void testIt() {
        assertEquals(1, howManyNumbers(2));
        assertEquals(0, howManyNumbers(1));
        assertEquals(59, howManyNumbers(10));
        assertEquals(0, howManyNumbers(0));
    }
}
