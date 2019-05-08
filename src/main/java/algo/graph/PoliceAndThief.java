package algo.graph;

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
                if (graph.shortestDistance(i, thief) < graph.shortestDistance(i, police))
                    return false; // if thief can go to a cycle first.
            }
        }
        return true;
    }
}
