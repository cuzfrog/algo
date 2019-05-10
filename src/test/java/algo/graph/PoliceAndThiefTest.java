package algo.graph;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PoliceAndThiefTest {
    /*
     1--2--3--4--5--0--6--7--14--15
        |  |  |  |     \ /    | / |
        8--9 10 12     13    16--17
              \ /
              11
    */
    private static final Graph graph = TestGraphSample.graph;

    @Test
    public void cycleCheck() {
        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 7).catchable()).isTrue();
        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 16).catchable()).isTrue();
        assertThat(new PoliceAndThiefCycleCheck(graph, 3, 8).catchable()).isTrue();

        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 5).catchable()).isFalse();
        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 1).catchable()).isFalse();
        assertThat(new PoliceAndThiefCycleCheck(graph, 3, 2).catchable()).isFalse();
    }

    @Test
    public void backtracking() {
        assertThat(new PoliceAndThiefBacktrack(graph, 0, 7).catchable()).isTrue();
        assertThat(new PoliceAndThiefBacktrack(graph, 0, 16).catchable()).isTrue();
        assertThat(new PoliceAndThiefBacktrack(graph, 3, 8).catchable()).isTrue();

        assertThat(new PoliceAndThiefBacktrack(graph, 0, 5).catchable()).isFalse();
        assertThat(new PoliceAndThiefBacktrack(graph, 0, 1).catchable()).isFalse();
        assertThat(new PoliceAndThiefBacktrack(graph, 3, 2).catchable()).isFalse();
    }
}
