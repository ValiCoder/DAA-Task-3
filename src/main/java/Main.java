import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== City Transportation Network Optimization ===\n");

        List<Graph> graphs = GraphGenerator.generateAllDatasets();

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            System.out.println("Graph " + (i + 1) + ": " + graph.getVertexCount() +
                    " districts, " + graph.getEdgeCount() + " potential roads");

            // Визуализируем оригинальный граф (только для маленьких графов)
            if (graph.getVertexCount() <= 10) {
                GraphVisualizer.visualizeOriginalGraph(graph);
            }

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

            if (graph.getVertexCount() <= 300) {
                GraphVisualizer.visualizeMST(graph, kruskal.getMstEdges(), "Kruskal");
                GraphVisualizer.visualizeMST(graph, prim.getMstEdges(), "Prim");
            }

            System.out.println("=".repeat(50));

            // Пауза между графами для визуализации
            if (graph.getVertexCount() <= 10) {
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
            }
        }
    }
}