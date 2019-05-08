package algo.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        cyclicMarks = new boolean[vertexCount];
        markCycles();
    }

    private void addEdge(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= vertices.length || v2 >= vertices.length) throw new IllegalArgumentException();
        vertices[v1].add(v2);
        vertices[v2].add(v1);
    }

    private void markCycles() {
        throw new AssertionError("Not implemented");
    }

    int vertexCount() {
        return vertices.length;
    }

    Set<Integer> adjacent(int vertex) {
        return Collections.unmodifiableSet(vertices[vertex]);
    }

    boolean isInCycle(int vertex) {
        return cyclicMarks[vertex];
    }

}
