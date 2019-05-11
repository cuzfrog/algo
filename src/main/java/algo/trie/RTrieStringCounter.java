package algo.trie;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Trie to count long String list. This impl is probably not much more spatially efficient than a map.
 */
final class RTrieStringCounter implements StringCounter {

    private final Node root = new Node((char) 0, null);

    /**
     * @param src   coming String list
     * @param n     top n count
     * @param limit maximum count on the source
     * @return top n repeated Strings.
     */
    @Override
    public List<String> top(Stream<String> src, int n, int limit) {
        src.limit(limit).forEach(s -> root.query(s.toCharArray(), 0));
        return root.findTop(n).stream()
                .map(Node::toWord).map(s -> s.substring(1))
                .collect(Collectors.toList());
    }

    private static final class Node extends AbstractNode<Node> implements Comparable<Node> {
        private static final int R = 1 << 16; //char

        Node[] children;
        long cnt = 0;

        Node(final char value, final Node parent) {
            super(value, parent);
        }

        void query(char[] chars, int p) {
            if (p >= chars.length) {
                cnt++;
                return;
            }
            char c = chars[p];
            if (children == null) children = new Node[R]; //lazy
            Node child = children[c];
            if (child == null) child = children[c] = new Node(c, this);
            child.query(chars, p + 1);
        }

        List<Node> findTop(int n) {
            MinHeap<Node> minHeap = new MinHeap<>(n);
            findTopRecursiveHelper(minHeap, -1);
            return minHeap.getAll().stream().filter(Objects::nonNull)
                    .sorted((n1, n2) -> Long.compare(n2.cnt, n1.cnt)).collect(Collectors.toList());
        }

        private void findTopRecursiveHelper(MinHeap<Node> minHeap, int p) {
            if (children != null) {
                for (int i = 0; i < R; i++) {
                    Node child = children[i];
                    if (child != null) {
                        minHeap.insertLarger(child);
                        child.findTopRecursiveHelper(minHeap, p);
                    }
                }
            }
        }

        @Override
        public int compareTo(final Node o) {
            return Long.compare(cnt, o.cnt);
        }
    }
}
