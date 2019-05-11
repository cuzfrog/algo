package algo.trie;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class HashMapStringCounter implements StringCounter {

    private final Map<String, Integer> map = new HashMap<>();

    @Override
    public List<String> top(final Stream<String> src, final int n, final int limit) {
        src.limit(limit).forEach(str -> map.compute(str, (k, v) -> v == null ? 1 : v + 1));
        MinHeap<Tuple> minHeap = new MinHeap<>(n);
        map.forEach((k, v) -> minHeap.insertLarger(new Tuple(k, v)));
        return minHeap.getAll().stream()
                .sorted(Comparator.reverseOrder())
                .map(t -> t.key)
                .collect(Collectors.toList());
    }

    private static final class Tuple implements Comparable<Tuple> {
        final String key;
        final Integer value;

        private Tuple(final String key, final Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(final Tuple o) {
            return value.compareTo(o.value);
        }
    }
}
