package KruskalAlgorithm;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> vertices = Arrays.asList("A","B","C","D","E","F","G","H");
        List<Edge> edges = Arrays.asList(
                new Edge("A","B",4), new Edge("A","H",8),
                new Edge("B","C",8), new Edge("B","H",11),
                new Edge("C","D",7), new Edge("C","F",4), new Edge("C","G",2),
                new Edge("D","E",9), new Edge("D","F",14),
                new Edge("E","F",10), new Edge("F","G",2), new Edge("G","H",1)
        );

        Graph graph = new Graph(vertices, edges);
        System.out.println("Graph: " + graph.getVertexCount() + " vertices, " + graph.getEdgeCount() + " edges\n");

        KruskalMST kruskal = new KruskalMST(graph);
        kruskal.run();

        PrimMST prim = new PrimMST(graph);
        prim.run();

        System.out.println("=== Comparison ===");
        System.out.println("Both MST total costs equal? " +
                (kruskal.getTotalCost() == prim.getTotalCost() ? "Yes" : "No"));
    }
}
