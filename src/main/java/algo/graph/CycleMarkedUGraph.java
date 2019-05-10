package algo.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

final class CycleMarkedUGraph implements Graph {
    private final Set<Integer>[] adjArr;
    private final int[] cyclicMarks;
    private final int[][] distances;

    @SuppressWarnings("unchecked")
    CycleMarkedUGraph(final int vertexCount, Set<Edge> edges) {
        if (vertexCount <= 0 || edges == null) throw new IllegalArgumentException();
        adjArr = (Set<Integer>[]) new Set[vertexCount];
        distances = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjArr[i] = new HashSet<>();
            Arrays.fill(distances[i], -1);
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
            if (adjMinCycle < cyclicMarks[i]) cyclicMarks[i] = adjMinCycle;
        }
    }

    @Override
    public int vertexCount() {
        return adjArr.length;
    }

    @Override
    public Set<Integer> adjacent(int vertex) {
        return new HashSet<>(adjArr[vertex]);
    }

    @Override
    public boolean isInCycle(int vertex, int cycleLength) {
        return cyclicMarks[vertex] > cycleLength;
    }

    @Override
    public int shortestDistance(int src, int dest) {
        if (src < 0 || src >= adjArr.length || dest < 0 || dest >= adjArr.length) throw new IllegalArgumentException();
        if (src == dest) return 0;

        int dis;
        if ((dis = distances[src][dest]) >= 0) return dis; //cached

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
        dis = adjacent.isEmpty() ? Integer.MAX_VALUE : marked[dest];
        distances[src][dest] = dis;
        distances[dest][src] = dis;
        return dis;
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
