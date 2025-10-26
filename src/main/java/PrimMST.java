import java.util.*;

public class PrimMST {
    private final Graph graph;
    private List<Edge> mstEdges;
    private int totalCost;
    private long executionTime;
    private int operationsCount;

    public PrimMST(Graph graph) {
        this.graph = graph;
    }

    public void run() {
        long startTime = System.nanoTime();
        mstEdges = new ArrayList<>();
        totalCost = 0;
        operationsCount = 0;

        if (graph.getVertexCount() == 0) {
            executionTime = (System.nanoTime() - startTime) / 1_000_000;
            return;
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        String startVertex = graph.getVertices().get(0);
        visited.add(startVertex);
        operationsCount++;

        // Add all edges from start vertex
        for (Edge edge : graph.getAdjacencyList().get(startVertex)) {
            pq.offer(edge);
            operationsCount++;
        }

        while (!pq.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = pq.poll();
            operationsCount++; // heap pop

            String nextVertex = null;
            if (visited.contains(edge.from) && !visited.contains(edge.to)) {
                nextVertex = edge.to;
            } else if (visited.contains(edge.to) && !visited.contains(edge.from)) {
                nextVertex = edge.from;
            }

            if (nextVertex != null) {
                visited.add(nextVertex);
                mstEdges.add(edge);
                totalCost += edge.weight;
                operationsCount += 3; // add to visited, mst, and cost update

                // Add edges from the new vertex
                for (Edge nextEdge : graph.getAdjacencyList().get(nextVertex)) {
                    operationsCount++;
                    if (!visited.contains(nextEdge.to)) {
                        pq.offer(nextEdge);
                        operationsCount++; // heap push
                    }
                }
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1_000_000;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public long getExecutionTime() { return executionTime; }
    public int getOperationsCount() { return operationsCount; }
}