package algo.trie;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

final class MinHeap<N extends Comparable<N>> {
    private final N[] arr;
    private int p = -1;

    @SuppressWarnings("unchecked")
    MinHeap(int capacity) {
        arr = (N[]) Array.newInstance(Comparable.class, capacity);
    }

    /** If heap is full, insert element and remove the minimum one. */
    void insertLarger(N elem) {
        if (p < 0) arr[++p] = elem;
        else {
            if (p >= arr.length - 1) { // if full, remove min
                if (less(arr[0], elem)) {
                    swap(0, p--);
                    heapSwimDown();
                    arr[++p] = elem;
                    heapSwimUp();
                }
            } else {
                arr[++p] = elem;
                heapSwimUp();
            }
        }
    }

    N get(int index) {
        if (index < 0 || index > p) throw new IndexOutOfBoundsException();
        return arr[index];
    }

    List<N> getAll() {
        return Arrays.asList(Arrays.copyOf(arr, p + 1));
    }

    private void heapSwimUp() {
        int a = p, b;
        while (less(arr[a], arr[b = (a >> 1)])) {
            swap(a, b);
            a = b;
        }
    }
    private void heapSwimDown() {
        int a = 0, b;
        while ((b = (a << 1) + 1) <= p) {
            if (b + 1 <= p && less(arr[b + 1], arr[b])) b++;
            if (less(arr[b], arr[a])) swap(a, b);
            a = b;
        }
    }
    private void swap(int a, int b) {
        N t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    private static <T extends Comparable<T>> boolean less(T t1, T t2) {
        return t1.compareTo(t2) < 0;
    }
}
