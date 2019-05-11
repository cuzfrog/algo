package algo.trie;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class TstStringCounter implements StringCounter {

    private Node root;

    @Override
    public List<String> top(Stream<String> src, int n, int limit) {
        src.limit(limit).forEach(str -> root = query(root, null, str, 0));
        MinHeap<Node> minHeap = new MinHeap<>(n);
        recursivelyFindTop(minHeap, root);
        return minHeap.getAll().stream()
                .sorted(Comparator.reverseOrder())
                .map(w -> w.toWord())
                .collect(Collectors.toList());
    }

    private static Node query(Node node, Node parent, String word, int p) {
        char c = word.charAt(p);
        Node n = node;
        if (n == null) n = new Node(c, parent);
        if (c < n.value) n.left = query(n.left, parent, word, p);
        else if (c > n.value) n.right = query(n.right, parent, word, p);
        else if (p < word.length() - 1) n.mid = query(n.mid, n, word, p + 1);
        else n.cnt++;
        return n;
    }

    private static void recursivelyFindTop(MinHeap<Node> minHeap, Node node) {
        if (node != null) {
            minHeap.insertLarger(node);
            recursivelyFindTop(minHeap, node.left);
            recursivelyFindTop(minHeap, node.mid);
            recursivelyFindTop(minHeap, node.right);
        }
    }

    private static final class Node extends AbstractNode<Node> implements Comparable<Node> {
        int cnt = -1;
        Node left, mid, right;

        private Node(char c, Node parent) {
            super(c, parent);
        }

        @Override
        public int compareTo(final Node o) {
            return Integer.compare(cnt, o.cnt);
        }
    }
}
