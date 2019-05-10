package algo.graph;

import java.util.Set;

interface Graph {

    int vertexCount();

    Set<Integer> adjacent(int vertex);

    /** Check if a pv is in a cycle of a given minimum length. */
    boolean isInCycle(int vertex, int cycleLength);

    /** Return Integer.MAX_VALUE if there's no path linking src and dest. */
    int shortestDistance(int src, int dest);
}
