import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== City Transportation Network Optimization ===\n");

        List<Graph> graphs = GraphGenerator.generateAllDatasets();

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            System.out.println("Graph " + (i + 1) + ": " + graph.getVertexCount() +
                    " districts, " + graph.getEdgeCount() + " potential roads");

            // Kruskal
            KruskalMST kruskal = new KruskalMST(graph);
            kruskal.run();

            // Prim
            PrimMST prim = new PrimMST(graph);
            prim.run();

            // Results
            System.out.println("Kruskal - Cost: " + kruskal.getTotalCost() +
                    ", Time: " + kruskal.getExecutionTime() + "ms, " +
                    "Operations: " + kruskal.getOperationsCount());
            System.out.println("Prim    - Cost: " + prim.getTotalCost() +
                    ", Time: " + prim.getExecutionTime() + "ms, " +
                    "Operations: " + prim.getOperationsCount());

            // MST edges (show first 5 for large graphs)
            System.out.print("MST Edges: ");
            List<Edge> edgesToShow = kruskal.getMstEdges();
            int showCount = Math.min(5, edgesToShow.size());
            for (int j = 0; j < showCount; j++) {
                System.out.print(edgesToShow.get(j) + " ");
            }
            if (edgesToShow.size() > 5) System.out.print("...");

            System.out.println("\n" + "=".repeat(50));
        }
    }
}