package KruskalAlgorithm;

import java.util.*;

public class KruskalMST {
    private Graph graph;
    private List<Edge> mstEdges = new ArrayList<>();
    private int totalCost = 0;
    private long timeMs;
    private int edgeChecks = 0;

    public KruskalMST(Graph graph) {
        this.graph = graph;
    }

    public void run() {
        long start = System.nanoTime();
        UnionFind uf = new UnionFind(graph.vertices);
        List<Edge> sortedEdges = new ArrayList<>(graph.edges);
        Collections.sort(sortedEdges);

        for (Edge e : sortedEdges) {
            edgeChecks++;
            if (!uf.find(e.u).equals(uf.find(e.v))) {
                uf.union(e.u, e.v);
                mstEdges.add(e);
                totalCost += e.weight;
            }
            if (mstEdges.size() == graph.vertices.size() - 1) break;
        }

        long end = System.nanoTime();
        timeMs = (end - start) / 1_000_000;

        System.out.println("Kruskal’s Algorithm");
        System.out.println("MST edges: " + mstEdges);
        System.out.println("Total cost: " + totalCost);
        System.out.println("Operations: find=" + uf.findOps + ", union=" + uf.unionOps + ", edgeChecks=" + edgeChecks);
        System.out.println("Execution time: " + timeMs + " ms\n");
    }

    public int getTotalCost() { return totalCost; }
}
