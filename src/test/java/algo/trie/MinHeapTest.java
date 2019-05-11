package algo.trie;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class MinHeapTest {

    private final MinHeap<Integer> heap = new MinHeap<>(3);

    @Test
    public void insertLarger() {
        heap.insertLarger(1);
        heap.insertLarger(2);
        heap.insertLarger(3);
        assertThat(heap.getAll()).containsExactlyInAnyOrder(1, 2, 3);
        heap.insertLarger(0);
        assertThat(heap.getAll()).containsExactlyInAnyOrder(1, 2, 3);
        heap.insertLarger(2);
        assertThat(heap.getAll()).containsExactlyInAnyOrder(2, 2, 3);
        heap.insertLarger(4);
        assertThat(heap.getAll()).containsExactlyInAnyOrder(2, 3, 4);
    }
}