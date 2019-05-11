## 1. Police & Thief in a graph

**Cycle check**

Based on the thought that if the thief can escape to a cycle, then, the chasing becomes infinite.
* Only cycles that contain more than 3 nodes can be available for the thief.
* Even in a 4 nodes cycle, if all thief's next move is within the police's range, the thief can't escape.

[Implementation](src/main/java/algo/graph/PoliceAndThiefCycleCheck.java)

**Backtracking**

Assumption: the police is always heading to the thief by the shortest path.

[Implementation](src/main/java/algo/graph/PoliceAndThiefBacktrack.java)

## 2. Count repetition times of a long list of Strings

* Map.  Space complexity: `N*w`
* Trie. Space complexity: `T*N ~ T*N*w`

When T<w, a Trie could possible win a Map.

* `T` = expansion way degree
* `N` = distinct key count
* `w` = average key length

R-way Trie [implementation](src/main/java/algo/trie/RTrieStringCounter.java)

Ts Trie [implementation](src/main/java/algo/trie/TstStringCounter.java)

**Memory measure:**
Digest 1,000,000 random alphabet String of W width. 
Max distinct key count: Permutation(26,W)
* W=3:    15,600
* W=4:   358,800
* W=5: 1,000,000(7,893,600)
See [test](src/test/java/algo/trie/LongListStringCounterTest.java)

Each for average result(MB) of 5 trials:

Implementation | Width of 3 | Width of 4 | Width of 5
------------ | ------------- | ------------ | ----------
256-way Trie | 771| NA | NA
HashMap_0.25 | 95 | 162 | 174
HashMap_0.75 | 84 | 142 | 170
HashMap_1.00 | 84 | 146 | 162
TreeMap      | 84 | 142 | 162
Ts Trie      | 79 | 122 | 182

(Tested serval times on HotSpot 1.8.0_152 64bit)

Memory usage of TsTrie is pretty much stable. 
TreeMap sizes should be affected by random key count.
A HashMap's size also relates to its load factor.
