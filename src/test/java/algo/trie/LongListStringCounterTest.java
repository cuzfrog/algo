package algo.trie;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.stream.Stream;

public class LongListStringCounterTest {

    @Test
    public void top() {
        Stream<String> src = Stream.generate(()-> RandomStringUtils.randomAlphabetic(3));
        LongListStringCounter.top(src, 10, 500000).forEach(System.out::println);
    }
}