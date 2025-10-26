package KruskalAlgorithm;

import java.util.*;

public class PrimMST {
    private Graph graph;
    private List<Edge> mstEdges = new ArrayList<>();
    private int totalCost = 0;
    private long timeMs;
    private int heapOps = 0, neighborChecks = 0;

    public PrimMST(Graph graph) {
        this.graph = graph;
    }

    public void run() {
        long start = System.nanoTime();
        String startNode = graph.vertices.get(0);
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        visited.add(startNode);
        pq.addAll(graph.adjList.get(startNode));

        while (!pq.isEmpty() && mstEdges.size() < graph.vertices.size() - 1) {
            Edge e = pq.poll();
            heapOps++;
            if (visited.contains(e.v)) continue;

            mstEdges.add(e);
            totalCost += e.weight;
            visited.add(e.v);

            for (Edge next : graph.adjList.get(e.v)) {
                neighborChecks++;
                if (!visited.contains(next.v)) pq.add(next);
            }
        }

        long end = System.nanoTime();
        timeMs = (end - start) / 1_000_000;

        System.out.println("Prim’s Algorithm");
        System.out.println("MST edges: " + mstEdges);
        System.out.println("Total cost: " + totalCost);
        System.out.println("Operations: heapOps=" + heapOps + ", neighborChecks=" + neighborChecks);
        System.out.println("Execution time: " + timeMs + " ms\n");
    }

    public int getTotalCost() { return totalCost; }
}
