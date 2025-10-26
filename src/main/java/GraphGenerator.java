import java.util.*;

public class GraphGenerator {
    private static final Random random = new Random();

    public static List<Graph> generateAllDatasets() {
        List<Graph> allGraphs = new ArrayList<>();

        // Small graphs (4-6 vertices)
        allGraphs.add(generateGraph(4, 0.8, 50));
        allGraphs.add(generateGraph(5, 0.7, 50));
        allGraphs.add(generateGraph(6, 0.6, 50));

        // Medium graphs (10-15 vertices)
        allGraphs.add(generateGraph(10, 0.5, 100));
        allGraphs.add(generateGraph(12, 0.4, 100));
        allGraphs.add(generateGraph(15, 0.3, 100));

        // Large graphs (20-30 vertices)
        allGraphs.add(generateGraph(20, 0.25, 200));
        allGraphs.add(generateGraph(25, 0.2, 200));
        allGraphs.add(generateGraph(30, 0.15, 200));

        return allGraphs;
    }

    private static Graph generateGraph(int vertices, double density, int maxWeight) {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            nodes.add("D" + (i + 1));
        }

        List<Edge> edges = new ArrayList<>();
        Set<String> usedEdges = new HashSet<>();

        // Ensure connectivity - create spanning tree first
        for (int i = 1; i < vertices; i++) {
            int weight = 1 + random.nextInt(maxWeight);
            edges.add(new Edge(nodes.get(i - 1), nodes.get(i), weight));
            usedEdges.add(nodes.get(i - 1) + "-" + nodes.get(i));
        }

        // Add additional edges based on density
        int maxPossibleEdges = vertices * (vertices - 1) / 2;
        int targetEdges = Math.max(vertices - 1, (int) (maxPossibleEdges * density));

        while (edges.size() < targetEdges) {
            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);
            if (u == v) continue;

            String edge1 = nodes.get(u) + "-" + nodes.get(v);
            String edge2 = nodes.get(v) + "-" + nodes.get(u);

            if (!usedEdges.contains(edge1) && !usedEdges.contains(edge2)) {
                int weight = 1 + random.nextInt(maxWeight);
                edges.add(new Edge(nodes.get(u), nodes.get(v), weight));
                usedEdges.add(edge1);
                usedEdges.add(edge2);
            }
        }

        return new Graph(nodes, edges);
    }
}