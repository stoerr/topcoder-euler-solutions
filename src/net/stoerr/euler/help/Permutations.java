package net.stoerr.euler.help;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Klasse zur Erzeugung von Permutationen. Es gibt übrigens einen Algorithmus
 * zur direkten Erzeugung der n-ten Permutation: eine Zahl muss dazu in die
 * "factoradical" form gebracht werden (n-te Stelle is modulo n!)
 */
public class Permutations {

    /**
     * Executes block on all permutations of objs.
     */
    public static <T> void permutate(T[] objs, Block<T[]> block) {
        if (null == objs || 0 == objs.length)
            return;
        boolean[] used = new boolean[objs.length];
        Class<?> objclass = objs.getClass().getComponentType();
        T[] perm = (T[]) Array.newInstance(objclass, objs.length);
        permutate(objs, block, used, perm, 0);
    }

    private static <T> void permutate(T[] objs, Block<T[]> block,
            boolean[] used, T[] perm, int pos) {
        if (objs.length == pos) {
            block.exec(perm);
        } else {
            for (int i = 0; i < objs.length; ++i) {
                if (!used[i]) {
                    perm[pos] = objs[i];
                    used[i] = true;
                    permutate(objs, block, used, perm, pos + 1);
                    used[i] = false;
                }
            }
        }
    }

}
