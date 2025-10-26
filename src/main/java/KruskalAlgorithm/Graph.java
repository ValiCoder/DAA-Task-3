package KruskalAlgorithm;

import java.util.*;

public class Graph {
    public List<String> vertices;
    public List<Edge> edges;
    public Map<String, List<Edge>> adjList;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        buildAdjList();
    }

    private void buildAdjList() {
        adjList = new HashMap<>();
        for (String v : vertices) {
            adjList.put(v, new ArrayList<>());
        }
        for (Edge e : edges) {
            adjList.get(e.u).add(new Edge(e.u, e.v, e.weight));
            adjList.get(e.v).add(new Edge(e.v, e.u, e.weight));
        }
    }

    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return edges.size(); }
}
