package algo.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

final class Graph {
    private final Set<Integer>[] vertices;
    private final boolean[] cyclicMarks;

    @SuppressWarnings("unchecked")
    Graph(final int vertexCount, Set<Edge> edges) {
        vertices = (Set<Integer>[]) new Set[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            vertices[i] = new HashSet<>();
        }
        edges.forEach(e -> this.addEdge(e.a, e.b));
        cyclicMarks = markCyclesOfMoreThan3Nodes();
    }

    private void addEdge(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= vertices.length || v2 >= vertices.length) throw new IllegalArgumentException();
        vertices[v1].add(v2);
        vertices[v2].add(v1);
    }

    private boolean[] markCyclesOfMoreThan3Nodes() {
        throw new AssertionError("Not implemented");
    }

    int vertexCount() {
        return vertices.length;
    }

    Set<Integer> adjacent(int vertex) {
        return new HashSet<>(vertices[vertex]);
    }

    /** Cycles that contain more than 3 nodes. */
    boolean isInCycleOfMoreThan3Nodes(int vertex) {
        return cyclicMarks[vertex];
    }

    /** Return Integer.MAX_VALUE if there's no path linking src and dest. */
    int shortestDistance(int src, int dest) {
        int[] marked = new int[this.vertexCount()];
        Set<Integer> adjacent = new HashSet<>();
        adjacent.add(src);
        AtomicInteger step = new AtomicInteger(0);
        while (marked[dest] == 0 && !adjacent.isEmpty()) { //BFS
            step.incrementAndGet();
            adjacent = adjacent.stream().flatMap(v ->
                    this.adjacent(v).stream()
                            .filter(i -> marked[i] == 0)
                            .peek(i -> marked[i] = step.get())
            ).collect(Collectors.toSet());
        }
        return adjacent.isEmpty() ? Integer.MAX_VALUE : marked[dest];
    }
}
