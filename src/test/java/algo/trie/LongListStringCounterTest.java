package algo.trie;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LongListStringCounterTest {

    private final Stream<String> src = Stream.of("A", "A", "A", "B", "B", "C", "D", "D", "D", "D", "E");

    @Before
    public void tryToClearMem() throws Exception {
        Thread.sleep(200);
        System.gc();
        Thread.sleep(200);
    }

    @Test
    public void test() {
        assertThat(new RTrieStringCounter().top(src, 3, 100)).containsExactly("D", "A", "B");
        assertThat(new TstStringCounter().top(src, 3, 100)).containsExactly("D", "A", "B");
    }

    @Test
    public void memMeasure() throws Exception {
        int TRIAL_TIME = 1;
        Map<Integer, Map<String, Long>> measures = new TreeMap<>();
        for (int w = 3; w <= 5; w++) {
            Map<String, Long> count = new TreeMap<>();
            for (int i = 0; i < TRIAL_TIME; i++) {
                List<String> infiniteSrc = generateStrings(w, 1_000_000);
                if (w == 3) {
                    long u = measure(new RTrieStringCounter(), infiniteSrc);
                    count.compute("256RTrie", (k, v) -> v == null ? u : v + u);
                }
                {
                    long u = measure(new TstStringCounter(), infiniteSrc);
                    count.compute("TsTrie", (k, v) -> v == null ? u : v + u);
                }
                {
                    long u = measure(new MapStringCounter(() -> new HashMap<>(100, 0.25f)), infiniteSrc);
                    count.compute("HashMap_0.25", (k, v) -> v == null ? u : v + u);
                }
                {
                    long u = measure(new MapStringCounter(HashMap::new), infiniteSrc);
                    count.compute("HashMap_0.75", (k, v) -> v == null ? u : v + u);
                }
                {
                    long u = measure(new MapStringCounter(() -> new HashMap<>(100, 1.0f)), infiniteSrc);
                    count.compute("HashMap_1.00", (k, v) -> v == null ? u : v + u);
                }
                {
                    long u = measure(new MapStringCounter(TreeMap::new), infiniteSrc);
                    count.compute("TreeMap", (k, v) -> v == null ? u : v + u);
                }
            }
            measures.put(w, count);
        }
        measures.forEach((w, c) -> {
            System.out.println("-----width=" + w + "------");
            c.forEach((k, v) -> System.out.println(k + ": " + ((v / TRIAL_TIME) >> 20) + "MB"));
        });
    }

    private static long measure(StringCounter counter, List<String> infiniteSrc) throws Exception {
        System.gc();
        Thread.sleep(300);
        long before = memUsage();
        counter.top(infiniteSrc.stream(), 10, 1_000_000).forEach(w -> {});
        return memUsage() - before;
    }

    private static List<String> generateStrings(int width, int size) {
        return Stream.generate(() -> RandomStringUtils.randomAlphabetic(width)).limit(size).collect(Collectors.toList());
    }

    private static long memUsage() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
