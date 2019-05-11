package algo.trie;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LongListStringCounterTest {

    private final Stream<String> src = Stream.of("A", "A", "A", "B", "B", "C", "D", "D", "D", "D", "E");
    private final Stream<String> infiniteSrc = Stream.generate(() -> RandomStringUtils.randomAlphabetic(3));

    @Before
    public void tryToClearMem() throws Exception {
        Thread.sleep(200);
        System.gc();
        Thread.sleep(200);
    }

    @Test
    public void rWayTrie() {
        assertThat(new RTrieStringCounter().top(src, 3, 100)).containsExactly("D", "A", "B");
    }

    @Test
    public void tsTrie() {
        assertThat(new TstStringCounter().top(src, 3, 100)).containsExactly("D", "A", "B");
    }

    @Test
    public void rWayTrieMemMeasure() {
        new RTrieStringCounter().top(infiniteSrc, 10, 500000).forEach(System.out::println);
        System.out.println("256RTrie: " + memUsage() + "MB");
    }

    @Test
    public void tsTrieMemMeasure() {
        new TstStringCounter().top(infiniteSrc, 10, 500000).forEach(System.out::println);
        System.out.println("TsTrie: " + memUsage() + "MB");
    }

    @Test
    public void hashMapMemMeasure() {
        new HashMapStringCounter().top(infiniteSrc, 10, 500000).forEach(System.out::println);
        System.out.println("HashMap: " + memUsage() + "MB");
    }

    private static long memUsage() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) >> 20;
    }
}
