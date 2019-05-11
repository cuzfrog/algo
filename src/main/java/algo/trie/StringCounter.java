package algo.trie;

import java.util.List;
import java.util.stream.Stream;

interface StringCounter {
    List<String> top(Stream<String> src, int n, int limit);
}
