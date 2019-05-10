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