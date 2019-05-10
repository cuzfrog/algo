package algo.graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** This impl only work if the police is always heading to the thief by the shortest path. */
final class PoliceAndThiefBacktrack {
    private final Graph graph;
    private final int police, thief;

    PoliceAndThiefBacktrack(final Graph graph, final int police, final int thief) {
        this.graph = graph;
        this.police = police;
        this.thief = thief;
    }

    boolean catchable() {
        Set<Step> steps = new HashSet<>();
        steps.add(new Step(police, thief, new HashSet<>()));
        AtomicBoolean stalemated = new AtomicBoolean();
        while (!steps.isEmpty()) {
            steps = steps.stream().flatMap(s -> s.next(graph, stalemated).stream()).collect(Collectors.toSet());
        }
        return !stalemated.get();
    }

    private static final class Step implements Comparable<Step> {
        final int pv, tv;
        final Set<Step> history;

        private Step(final int pv, final int tv, Set<Step> history) {
            this.pv = pv;
            this.tv = tv;
            this.history = new HashSet<>(history);
            this.history.add(this);
        }

        Set<Step> next(Graph graph, AtomicBoolean stalemated) {
            return graph.adjacent(tv).stream()
                    .filter(tn -> !tn.equals(pv) && !graph.adjacent(pv).contains(tn))
                    .flatMap(tn ->
                            graph.adjacent(pv).stream()
                                    .map(pn -> new Object() {
                                        int v = pn, dis = graph.shortestDistance(tn, pn);
                                    })
                                    .min(Comparator.comparingInt(tuple -> tuple.dis))
                                    .map(tuple -> tuple.v)
                                    .map(Stream::of).orElseGet(Stream::empty)
                                    .filter(pn -> !pn.equals(tn))
                                    .map(pn -> new Step(pn, tn, history))
                                    .peek(s -> {if(history.contains(s)) stalemated.set(true);})
                                    .filter(s -> !history.contains(s))
                    ).collect(Collectors.toSet());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || Step.class != o.getClass()) return false;
            final Step step = (Step) o;
            return pv == step.pv && tv == step.tv;
        }
        @Override
        public int hashCode() {
            return Objects.hash(pv, tv);
        }
        @Override
        public int compareTo(final Step o) { // for Java8
            int comp = Integer.compare(pv, o.pv);
            if (comp == 1) return Integer.compare(tv, o.tv);
            return comp;
        }
    }
}
