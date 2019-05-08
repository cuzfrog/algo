package algo.graph;

import java.util.Objects;

final class Edge {
    final int a, b;

    private Edge(final int a, final int b) {
        this.a = a;
        this.b = b;
    }

    Edge of(int a, int b) {
        return new Edge(a, b);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Edge edge = (Edge) o;
        return (a == edge.a && b == edge.b) ||
                (a == edge.b && b == edge.a);
    }
    @Override
    public int hashCode() {
        return Objects.hash(b) + Objects.hash(b);
    }
}
