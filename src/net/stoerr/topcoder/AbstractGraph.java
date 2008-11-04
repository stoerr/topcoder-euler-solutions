package net.stoerr.topcoder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Undirected graph
 * 
 * @author hps
 * 
 * @param <N>
 *            Nodetype; needs to define hash and equals.
 */
public abstract class AbstractGraph<N> {

    public abstract Collection<N> nodes();

    public abstract Collection<N> neighbors(N node);

    /**
     * All nodes that are connected to node.
     */
    public Collection<N> subgraph(N node) {
        Set<N> res = new HashSet<N>();
        res.add(node);
        Queue<N> q = new ArrayDeque<N>();
        q.add(node);
        while (!q.isEmpty()) {
            N n = q.remove();
            Collection<N> ns = neighbors(n);
            for (N nn : ns) {
                if (!res.contains(nn)) {
                    q.add(nn);
                    res.add(nn);
                }
            }
        }
        return res;
    }

    /**
     * Partitions the graph into unconnected subgraphs.
     */
    public Collection<Collection<N>> partitions() {
        Set<N> all = new HashSet<N>();
        Collection<Collection<N>> res = new ArrayList<Collection<N>>();
        Collection<N> ns = nodes();
        for (N n : ns) {
            if (!all.contains(n)) {
                Collection<N> part = subgraph(n);
                res.add(part);
                all.addAll(part);
            }
        }
        return res;
    }
}
