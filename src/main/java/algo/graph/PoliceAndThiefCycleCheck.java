package algo.graph;

final class PoliceAndThiefCycleCheck {
    private final UGraphCycle4 graph;
    private final int police, thief;

    PoliceAndThiefCycleCheck(final UGraphCycle4 graph, final int police, final int thief) {
        this.graph = graph;
        this.police = police;
        this.thief = thief;
    }

    boolean catchable() {
        for (int i = 0, l = graph.vertexCount(); i < l; i++) {
            if (i != thief && graph.isInCycleOfMoreThan3Nodes(i)) {
                if (graph.shortestDistance(i, thief) < graph.shortestDistance(i, police))
                    return false; // if thief can go to a cycle first.
            }
        }
        return true;
    }
}
