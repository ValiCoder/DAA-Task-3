import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== PERFORMANCE TEST FOR GRAPHS ===");

        List<Graph> graphs = GraphGenerator.generateAllDatasets();

        System.out.println("GraphID,Vertices,Edges,Algorithm,Cost,TimeMS,Operations");

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);

            KruskalMST kruskal = new KruskalMST(graph);
            kruskal.run();

            PrimMST prim = new PrimMST(graph);
            prim.run();

            System.out.printf("%d,%d,%d,Kruskal,%d,%d,%d\n",
                    i+1, graph.getVertexCount(), graph.getEdgeCount(),
                    kruskal.getTotalCost(), kruskal.getExecutionTime(), kruskal.getOperationsCount());

            System.out.printf("%d,%d,%d,Prim,%d,%d,%d\n",
                    i+1, graph.getVertexCount(), graph.getEdgeCount(),
                    prim.getTotalCost(), prim.getExecutionTime(), prim.getOperationsCount());
        }
    }
}