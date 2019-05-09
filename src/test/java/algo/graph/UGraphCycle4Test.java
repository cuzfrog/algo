package algo.graph;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public final class UGraphCycle4Test {

    private static final UGraphCycle4 graph = TestGraphSample.graph;

    @Test
    public void isInCycleOfMoreThan3Nodes() {
        int[] expectedInCycle = {2, 3, 8, 9, 4, 5, 10, 11, 12};
        assertThat(Arrays.stream(expectedInCycle).mapToObj(graph::isInCycleOfMoreThan3Nodes)).allMatch(r -> r);
        int[] expectedNotInCycle = {1, 6, 7, 13, 0, 14, 15, 16, 17};
        assertThat(Arrays.stream(expectedNotInCycle).mapToObj(graph::isInCycleOfMoreThan3Nodes)).allMatch(r -> !r);
    }

    @Test
    public void shortestDistance() {
        assertThat(graph.shortestDistance(1, 8)).isEqualTo(2);
        assertThat(graph.shortestDistance(11, 3)).isEqualTo(3);
    }
}