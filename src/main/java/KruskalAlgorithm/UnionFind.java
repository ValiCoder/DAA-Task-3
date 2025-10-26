package KruskalAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {
    private Map<String, String> parent = new HashMap<>();
    private Map<String, Integer> rank = new HashMap<>();
    public int findOps = 0;
    public int unionOps = 0;

    public UnionFind(Iterable<String> elements) {
        for (String e : elements) {
            parent.put(e, e);
            rank.put(e, 0);
        }
    }

    public String find(String x) {
        findOps++;
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public boolean union(String a, String b) {
        unionOps++;
        String ra = find(a);
        String rb = find(b);
        if (ra.equals(rb)) return false;

        if (rank.get(ra) < rank.get(rb)) {
            parent.put(ra, rb);
        } else if (rank.get(ra) > rank.get(rb)) {
            parent.put(rb, ra);
        } else {
            parent.put(rb, ra);
            rank.put(ra, rank.get(ra) + 1);
        }
        return true;
    }
}
