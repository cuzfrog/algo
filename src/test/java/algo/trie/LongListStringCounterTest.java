package algo.trie;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LongListStringCounterTest {

    @Test
    public void top() {
        Stream<String> src = Stream.of("A", "A", "A", "B", "B", "C", "D", "D", "D", "D", "E");
        assertThat(RTrieStringCounter.top(src, 3, 100)).containsExactly("D", "A", "B");
    }

    @Test
    public void longList() {
        Stream<String> src = Stream.generate(() -> RandomStringUtils.randomAlphabetic(3));
        RTrieStringCounter.top(src, 10, 500000).forEach(System.out::println);
    }
}