import java.util.*;

public class Graph {
    private final List<String> vertices;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;

    public Graph(List<String> vertices, List<Edge> edges) {
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
        this.adjacencyList = buildAdjacencyList();
    }

    private Map<String, List<Edge>> buildAdjacencyList() {
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String vertex : vertices) {
            adj.put(vertex, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adj.get(edge.from).add(new Edge(edge.from, edge.to, edge.weight));
            adj.get(edge.to).add(new Edge(edge.to, edge.from, edge.weight));
        }
        return adj;
    }

    public List<String> getVertices() { return Collections.unmodifiableList(vertices); }
    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
    public Map<String, List<Edge>> getAdjacencyList() { return Collections.unmodifiableMap(adjacencyList); }
    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return edges.size(); }

    public boolean isConnected() {
        if (vertices.isEmpty()) return true;
        Set<String> visited = new HashSet<>();
        dfs(vertices.get(0), visited);
        return visited.size() == vertices.size();
    }

    private void dfs(String vertex, Set<String> visited) {
        visited.add(vertex);
        for (Edge edge : adjacencyList.get(vertex)) {
            String neighbor = edge.to;
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }
}