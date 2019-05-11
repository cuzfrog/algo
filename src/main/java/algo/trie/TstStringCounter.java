package algo.trie;

import java.util.List;
import java.util.stream.Stream;

final class TstStringCounter {

    private Node root;

    List<String> top(Stream<String> src, int n, int limit) {
        src.limit(limit).forEach(str -> root = query(root, str, 0));

        return null;
    }

    private static Node query(Node node, String word, int p) {
        char c = word.charAt(p);
        Node n = node;
        if (n == null) n = new Node(c);
        if (c < n.c) n.left = query(n.left, word, p);
        else if (c > n.c) n.right = query(n.right, word, p);
        else if (p < word.length() - 1) n.mid = query(n.mid, word, p + 1);
        else n.cnt++;
        return n;
    }



    private static final class Node {
        final char c;
        int cnt = -1;
        Node left, mid, right;

        private Node(final char c) {
            this.c = c;
        }
    }
}
