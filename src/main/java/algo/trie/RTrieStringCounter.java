package algo.trie;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Trie to count long String list. This impl is probably not much more spatially efficient than a map.
 */
final class RTrieStringCounter {
    /**
     * @param src   coming String list
     * @param n     top n count
     * @param limit maximum count on the source
     * @return top n repeated Strings.
     */
    static List<String> top(Stream<String> src, int n, int limit) {
        Node root = new Node((char) 0, null);
        src.limit(limit).forEach(s -> root.query(s.toCharArray(), 0));
        return root.findTop(n).stream().map(Node::toWord).collect(Collectors.toList());
    }

    private static final class Node {
        private static final int R = 1 << 16; //char

        final char value;
        final Node parent;
        Node[] children;
        long cnt = 0;

        Node(final char value, final Node parent) {
            this.value = value;
            this.parent = parent;
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
            Node[] minHeap = new Node[n];
            findTopRecursiveHelper(minHeap, -1);
            return Arrays.stream(minHeap).filter(Objects::nonNull)
                    .sorted((n1, n2) -> Long.compare(n2.cnt, n1.cnt)).collect(Collectors.toList());
        }

        private void findTopRecursiveHelper(Node[] minHeap, int p) {
            if (children != null) {
                for (int i = 0; i < R; i++) {
                    Node child = children[i];
                    if (child != null) {
                        if (p < 0) minHeap[++p] = child;
                        else {
                            if (p >= minHeap.length - 1) { // if full, remove min
                                if (child.cnt > minHeap[0].cnt) {
                                    swap(minHeap, 0, p--);
                                    heapSwimDown(minHeap, p);
                                    minHeap[++p] = child;
                                    heapSwimUp(minHeap, p);
                                }
                            } else {
                                minHeap[++p] = child;
                                heapSwimUp(minHeap, p);
                            }
                        }
                        child.findTopRecursiveHelper(minHeap, p);
                    }
                }
            }
        }
        private static void heapSwimUp(Node[] minHeap, final int p) {
            int a = p, b;
            while (minHeap[a].cnt < minHeap[b = (a >> 1)].cnt) {
                swap(minHeap, a, b);
                a = b;
            }
        }
        private static void heapSwimDown(Node[] minHeap, final int p) {
            int a = 0, b;
            while ((b = (a << 1) + 1) <= p) {
                if (b + 1 <= p && minHeap[b + 1].cnt < minHeap[b].cnt) b++;
                if (minHeap[a].cnt > minHeap[b].cnt) swap(minHeap, a, b);
                a = b;
            }
        }
        private static void swap(Node[] arr, int a, int b) {
            Node t = arr[a];
            arr[a] = arr[b];
            arr[b] = t;
        }

        String toWord() {
            Node n = this;
            Deque<Node> stack = new LinkedList<>();
            while (n.parent != null) {
                stack.push(n);
                n = n.parent;
            }
            StringBuilder builder = new StringBuilder(stack.size());
            for (final Node node : stack) {
                builder.append(node.value);
            }
            return builder.toString();
        }

    }
}
