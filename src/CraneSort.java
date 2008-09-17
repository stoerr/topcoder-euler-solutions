import static org.junit.Assert.*;
import org.junit.Test;

/**
 * http://www.topcoder.com/stat?c=problem_statement&pm=8775&rd=12019
 * <p>
 * We are loading a container ship with a single crane. The containers are on
 * the dock, adjacent to each other in a line. Each container is owned by one of
 * our 4 customers. We need to rearrange them so that the containers are still
 * in a line with no gaps located somewhere on the dock, with each customer's
 * containers adjacent to each other.
 * 
 * We move a container by using our crane to pick up a single container and
 * place it somewhere on the dock (but not on top of another container). The
 * problem is to minimize the number of moves.
 * 
 * The String containers shows the original order of the containers, with each
 * container appearing as 'A','B','C', or 'D' according to which of our
 * customers is the owner. Return the minimum number of moves required.
 * 
 * @author hps
 */
public class CraneSort {

    /**
     * @param containers
     *            between 1 and 50 characters, inclusive; only the characters
     *            'A','B','C', or 'D'.
     */
    public int moves(String containers) {
        return -1;
    }

    @Test
    public void testIt() {
        assertEquals(0, moves("CBBADDD"));
        assertEquals(1, moves("ABCAA"));
        assertEquals(3, moves("CCCCBAAABA"));
        // We can move the first B to a temporary location somewhere on the
        // dock. Then move the rightmost A to the spot where that B used to be.
        // Finally move the B from its temporary location to the right end of
        // the line, resulting in CCCCAAAABB. (There are other ways to do it in
        // 3 moves.)
    }

}
