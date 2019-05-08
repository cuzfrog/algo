package algo.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

final class PoliceAndThief {
    private final Graph graph;
    private final int police, thief;

    PoliceAndThief(final Graph graph, final int police, final int thief) {
        this.graph = graph;
        this.police = police;
        this.thief = thief;
    }

    boolean catchable() {
        if (graph.isInCycle(thief)) return false; // if thief is already in a cycle.

        for (int i = 0, l = graph.vertexCount(); i < l; i++) {
            if (graph.isInCycle(i)) {
                if (directlyToThief(i)) return false; // if thief can go to a cycle first.
            }
        }
        return true;
    }

    private boolean directlyToThief(int vertex) {
        boolean[] marked = new boolean[graph.vertexCount()];
        Set<Integer> adjacent = new HashSet<>();
        adjacent.add(vertex);
        while (!marked[thief] && !adjacent.isEmpty()) {
            adjacent = adjacent.stream().flatMap(v ->
                    graph.adjacent(v).stream()
                            .filter(i -> !marked[i] && i != police) // the road is blocked by police.
                            .peek(i -> marked[i] = true)
            ).collect(Collectors.toSet());
        }
        return marked[thief];
    }

}
