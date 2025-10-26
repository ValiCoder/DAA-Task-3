import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphVisualizer extends JFrame {
    private Graph graph;
    private List<Edge> mstEdges;
    private String algorithmName;

    public GraphVisualizer(Graph graph, List<Edge> mstEdges, String algorithmName) {
        this.graph = graph;
        this.mstEdges = mstEdges != null ? new ArrayList<>(mstEdges) : new ArrayList<>();
        this.algorithmName = algorithmName;

        setTitle("Graph Visualization - " + algorithmName);
        setSize(1000, 800); // Увеличил размер окна
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new GraphPanel());
    }

    class GraphPanel extends JPanel {
        private Map<String, Point> vertexPositions;

        public GraphPanel() {
            setBackground(Color.WHITE);
            vertexPositions = new HashMap<>();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            calculateVertexPositions();

            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.setColor(Color.BLACK);
            g2d.drawString(algorithmName + " - Vertices: " + graph.getVertexCount() +
                    ", Edges: " + graph.getEdgeCount(), 20, 30);

            if (mstEdges.isEmpty()) {
                g2d.drawString("Original Graph", 20, 55);
            } else {
                g2d.drawString("MST Edges: " + mstEdges.size() + ", Total Cost: " +
                        calculateTotalCost(mstEdges), 20, 55);
            }

            // Draw edges
            if (mstEdges.isEmpty()) {
                // Draw all edges in light gray
                g2d.setColor(Color.LIGHT_GRAY);
                for (Edge edge : graph.getEdges()) {
                    drawEdge(g2d, edge, false);
                }
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(1));
                Set<String> mstEdgeSet = new HashSet<>();
                for (Edge edge : mstEdges) {
                    mstEdgeSet.add(edge.from + "-" + edge.to);
                    mstEdgeSet.add(edge.to + "-" + edge.from);
                }

                for (Edge edge : graph.getEdges()) {
                    if (!mstEdgeSet.contains(edge.from + "-" + edge.to)) {
                        drawEdge(g2d, edge, false);
                    }
                }

                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                for (Edge edge : mstEdges) {
                    drawEdge(g2d, edge, true);
                }
            }

            for (Map.Entry<String, Point> entry : vertexPositions.entrySet()) {
                Point p = entry.getValue();

                g2d.setColor(Color.BLUE);
                g2d.fillOval(p.x - 20, p.y - 20, 40, 40);

                g2d.setColor(Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(p.x - 20, p.y - 20, 40, 40);

                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                String label = entry.getKey();
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(label);
                int textHeight = fm.getHeight();
                g2d.drawString(label, p.x - textWidth/2, p.y + textHeight/4);
            }
        }

        private void calculateVertexPositions() {
            List<String> vertices = graph.getVertices();
            int width = getWidth();
            int height = getHeight();

            // Оставляем поля по краям
            int margin = 80;
            int centerX = width / 2;
            int centerY = height / 2;
            int radius = Math.min(centerX, centerY) - margin;

            for (int i = 0; i < vertices.size(); i++) {
                double angle = 2 * Math.PI * i / vertices.size();
                int x = centerX + (int)(radius * Math.cos(angle));
                int y = centerY + (int)(radius * Math.sin(angle));
                vertexPositions.put(vertices.get(i), new Point(x, y));
            }
        }

        private void drawEdge(Graphics2D g2d, Edge edge, boolean showWeight) {
            Point p1 = vertexPositions.get(edge.from);
            Point p2 = vertexPositions.get(edge.to);

            if (p1 != null && p2 != null) {
                // Calculate direction vector
                double dx = p2.x - p1.x;
                double dy = p2.y - p1.y;
                double length = Math.sqrt(dx * dx + dy * dy);

                // Normalize and scale to vertex radius
                double scale = 20.0 / length;
                int startX = p1.x + (int)(dx * scale);
                int startY = p1.y + (int)(dy * scale);
                int endX = p2.x - (int)(dx * scale);
                int endY = p2.y - (int)(dy * scale);

                g2d.drawLine(startX, startY, endX, endY);

                if (showWeight) {
                    int midX = (startX + endX) / 2;
                    int midY = (startY + endY) / 2;

                    // Draw weight background
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(midX - 12, midY - 10, 24, 20);

                    // Draw weight text
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    String weightText = String.valueOf(edge.weight);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(weightText);
                    g2d.drawString(weightText, midX - textWidth/2, midY + 4);
                }
            }
        }

        private int calculateTotalCost(List<Edge> edges) {
            int total = 0;
            for (Edge edge : edges) {
                total += edge.weight;
            }
            return total;
        }
    }

    public static void visualizeGraph(Graph graph, List<Edge> mstEdges, String algorithmName) {
        SwingUtilities.invokeLater(() -> {
            GraphVisualizer visualizer = new GraphVisualizer(graph, mstEdges, algorithmName);
            visualizer.setVisible(true);
        });
    }

    public static void visualizeOriginalGraph(Graph graph) {
        visualizeGraph(graph, null, "Original Graph");
    }

    public static void visualizeMST(Graph graph, List<Edge> mstEdges, String algorithmName) {
        visualizeGraph(graph, mstEdges, algorithmName + " MST");
    }
}