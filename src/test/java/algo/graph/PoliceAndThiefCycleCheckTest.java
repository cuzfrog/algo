package algo.graph;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PoliceAndThiefCycleCheckTest {
    /*
     1--2--3--4--5--0--6--7--14--15
        |  |  |  |     \ /    | / |
        8--9 10 12     13    16--17
              \ /
              11
    */
    private static final UGraphCycle4 graph = TestGraphSample.graph;

    @Test
    public void catchableCheck() {
        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 7).catchable()).isTrue();
        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 16).catchable()).isTrue();
        assertThat(new PoliceAndThiefCycleCheck(graph, 3, 8).catchable()).isTrue();

        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 5).catchable()).isFalse();
        assertThat(new PoliceAndThiefCycleCheck(graph, 0, 1).catchable()).isFalse();
        assertThat(new PoliceAndThiefCycleCheck(graph, 3, 2).catchable()).isFalse();
    }
}
