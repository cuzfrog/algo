package algo.graph;

final class PoliceAndThiefCycleCheck {
    private final Graph graph;
    private final int police, thief;

    PoliceAndThiefCycleCheck(final Graph graph, final int police, final int thief) {
        this.graph = graph;
        this.police = police;
        this.thief = thief;
    }

    boolean catchable() {
        for (int i = 0, l = graph.vertexCount(); i < l; i++) {
            if (i != thief && graph.isInCycle(i, 3)) {
                if (graph.shortestDistance(i, thief) < graph.shortestDistance(i, police))
                    return false; // if thief can go to a cycle first.
            }
        }
        return true;
    }
}
