package algo.graph;

import java.util.Arrays;
import java.util.stream.Collectors;

final class TestGraphSample {
    /*
     1--2--3--4--5--0--6--7--14--15
        |  |  |  |     \ /    | / |
        8--9 10 12     13    16--17
              \ /
              11
    */
    static final CycleMarkedUGraph graph;
    static {
        Edge[] edges = new Edge[]{
                Edge.of(1, 2),
                Edge.of(2, 3),
                Edge.of(3, 4),
                Edge.of(4, 5),
                Edge.of(5, 0),
                Edge.of(0, 6),
                Edge.of(6, 7),
                Edge.of(2, 8),
                Edge.of(8, 9),
                Edge.of(3, 9),
                Edge.of(4, 10),
                Edge.of(10, 11),
                Edge.of(11, 12),
                Edge.of(12, 5),
                Edge.of(6, 13),
                Edge.of(13, 7),
                Edge.of(7, 14),
                Edge.of(14, 15),
                Edge.of(14, 16),
                Edge.of(16, 17),
                Edge.of(16, 15),
                Edge.of(15, 17),
        };
        graph = new CycleMarkedUGraph(18, Arrays.stream(edges).collect(Collectors.toSet()));
    }
}
