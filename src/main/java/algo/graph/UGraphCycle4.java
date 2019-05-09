package algo.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

final class UGraphCycle4 {
    private final Set<Integer>[] adjArr;
    private final int[] cyclicMarks;

    @SuppressWarnings("unchecked")
    UGraphCycle4(final int vertexCount, Set<Edge> edges) {
        if (vertexCount <= 0 || edges == null) throw new IllegalArgumentException();
        adjArr = (Set<Integer>[]) new Set[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjArr[i] = new HashSet<>();
        }
        edges.forEach(e -> addEdge(e.a, e.b));
        cyclicMarks = new int[vertexCount];
        markCycles();
    }

    private void addEdge(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= adjArr.length || v2 >= adjArr.length) throw new IllegalArgumentException();
        adjArr[v1].add(v2);
        adjArr[v2].add(v1);
    }

    private void markCycles() {
        markCyclesDfs(-1, new Node(0, null), new boolean[adjArr.length]);
        downgradeCycleLength();
    }
    private void markCyclesDfs(int last, Node node, boolean[] marked) {
        final int v = node.vertex;
        marked[v] = true;
        for (int i : adjArr[v]) {
            Node next = new Node(i, node);
            if (i != last && marked[i]) {
                List<Integer> cycle = new ArrayList<>();
                cycle.add(next.vertex);
                Node n = next.parent;
                AtomicInteger l = new AtomicInteger();
                while (n != null) {
                    cycle.add(n.vertex);
                    l.incrementAndGet();
                    if (n.vertex == next.vertex) {
                        cycle.stream().filter(cv -> cyclicMarks[cv] == 0)
                                .forEach(cv -> cyclicMarks[cv] = l.get());
                    }
                    n = n.parent;
                }
            } else if (!marked[i]) {
                markCyclesDfs(v, next, marked);
            }
        }
    }
    private void downgradeCycleLength() {
        for (int i = 0; i < adjArr.length; i++) {
            int adjMinCycle = adjArr[i].stream().mapToInt(v -> cyclicMarks[v]).filter(c -> c > 0).min().orElse(Integer.MAX_VALUE);
            if(adjMinCycle < cyclicMarks[i]) cyclicMarks[i] = adjMinCycle;
        }
    }

    int vertexCount() {
        return adjArr.length;
    }

    Set<Integer> adjacent(int vertex) {
        return new HashSet<>(adjArr[vertex]);
    }

    /** Cycles that contain more than 3 nodes. */
    boolean isInCycleOfMoreThan3Nodes(int vertex) {
        return cyclicMarks[vertex] > 3;
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

    private static final class Node {
        final int vertex;
        final Node parent;
        private Node(final int vertex, final Node parent) {
            this.vertex = vertex;
            this.parent = parent;
        }
    }
}