package algo.trie;

import java.util.Deque;
import java.util.LinkedList;

abstract class AbstractNode<N extends AbstractNode<N>> {

    final char value;
    private final N parent;

    AbstractNode(final char value, final N parent) {
        this.value = value;
        this.parent = parent;
    }

    final String toWord() {
        AbstractNode n = this;
        Deque<AbstractNode> stack = new LinkedList<>();
        while (n != null) {
            stack.push(n);
            n = n.parent;
        }
        StringBuilder builder = new StringBuilder(stack.size());
        for (final AbstractNode node : stack) {
            builder.append(node.value);
        }
        return builder.toString();
    }
}
