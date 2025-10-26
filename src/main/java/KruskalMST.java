import java.util.*;

public class KruskalMST {
    private final Graph graph;
    private List<Edge> mstEdges;
    private int totalCost;
    private long executionTime;
    private int operationsCount;

    public KruskalMST(Graph graph) {
        this.graph = graph;
    }

    public void run() {
        long startTime = System.nanoTime();
        mstEdges = new ArrayList<>();
        totalCost = 0;
        operationsCount = 0;

        if (!graph.isConnected()) {
            executionTime = (System.nanoTime() - startTime) / 1_000_000;
            return;
        }

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);
        operationsCount += sortedEdges.size() * (int) Math.log(sortedEdges.size()); // sort operations

        UnionFind uf = new UnionFind(graph.getVertices());

        for (Edge edge : sortedEdges) {
            operationsCount++; // edge check
            if (mstEdges.size() == graph.getVertexCount() - 1) break;

            String root1 = uf.find(edge.from);
            String root2 = uf.find(edge.to);
            operationsCount += 2; // two find operations

            if (!root1.equals(root2)) {
                mstEdges.add(edge);
                totalCost += edge.weight;
                uf.union(edge.from, edge.to);
                operationsCount++; // union operation
            }
        }

        executionTime = (System.nanoTime() - startTime) / 1_000_000;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public long getExecutionTime() { return executionTime; }
    public int getOperationsCount() { return operationsCount; }

    private static class UnionFind {
        private final Map<String, String> parent;
        private final Map<String, Integer> rank;

        public UnionFind(List<String> vertices) {
            parent = new HashMap<>();
            rank = new HashMap<>();
            for (String vertex : vertices) {
                parent.put(vertex, vertex);
                rank.put(vertex, 0);
            }
        }

        public String find(String x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        public void union(String x, String y) {
            String rootX = find(x);
            String rootY = find(y);
            if (!rootX.equals(rootY)) {
                if (rank.get(rootX) < rank.get(rootY)) {
                    parent.put(rootX, rootY);
                } else if (rank.get(rootX) > rank.get(rootY)) {
                    parent.put(rootY, rootX);
                } else {
                    parent.put(rootY, rootX);
                    rank.put(rootX, rank.get(rootX) + 1);
                }
            }
        }
    }
}