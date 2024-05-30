import java.util.*;

/**
 * This class represents a Graph using an adjacency matrix.
 * It allows adding vertices and edges to the graph.
 * The graph can be either directed or undirected.
 */
public class AdjacencyMatrix {
    private int[][] adjacencyMatrix;
    private Map<String, Integer> vertexIndexMap;

    /**
     * Constructor for the Graph.
     * @param vertexCount the number of vertices in the graph
     */
    public AdjacencyMatrix(int vertexCount) {
        adjacencyMatrix = new int[vertexCount][vertexCount];
        vertexIndexMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex the vertex to be added
     * @throws IllegalArgumentException if the vertex already exists
     */
    public void addVertex(String vertex) {
        if (vertexIndexMap.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex already exists: " + vertex);
        }
        vertexIndexMap.put(vertex, vertexIndexMap.size());
    }

    /**
     * Adds an edge to the graph.
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     * @param isDirected whether the edge is directed or not
     * @throws IllegalArgumentException if either vertex does not exist
     */
    public void addEdge(String vertex1, String vertex2, boolean isDirected) {
        if (!vertexIndexMap.containsKey(vertex1) || !vertexIndexMap.containsKey(vertex2)) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
        int index1 = vertexIndexMap.get(vertex1);
        int index2 = vertexIndexMap.get(vertex2);
        adjacencyMatrix[index1][index2]++;
        if (!isDirected) {
            adjacencyMatrix[index2][index1]++;
        }
    }

    /**
     * Gets the vertices from the user.
     * @param s the Scanner to use for user input
     * @param vertexCount the number of vertices to get
     * @return an array of vertices
     */
    private static String[] getVertices(Scanner s, int vertexCount) {
        System.out.print("Enter the vertices separated by comma and space (, ): ");
        String[] vertices = s.nextLine().trim().split(", ");
        if (vertices.length != vertexCount) {
            throw new IllegalArgumentException("Invalid input. Please enter exactly " + vertexCount + " vertices separated by comma and space.");
        }
        return vertices;
    }

    /**
     * Gets the edges from the user.
     * @param s the Scanner to use for user input
     * @param edgeCount the number of edges to get
     * @return an array of edges
     */
    public static String[][] getEdges(Scanner s, int edgeCount, Set<String> vertices) {
        System.out.println("Enter the edges (vertex pairs) and whether the edge is directed (1 for yes, 0 for no):");
        String[][] edges = new String[edgeCount][3];
        for (int i = 0; i < edgeCount; i++) {
            String[] edgeInfo = s.nextLine().split(" ");
            if (edgeInfo.length != 3) {
                throw new IllegalArgumentException("Invalid input. Please enter the edge as 'vertex1 vertex2 isDirected'.");
            }
            edges[i] = edgeInfo;
        }
        return edges;
    }

    /**
     * Prints the adjacency matrix to the console.
     */
    public void printMatrix() {
        System.out.println("\nThe Adjacency Matrix of the graph: " + "\n");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * The main method that runs the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            int vertexCount = getVertexCount(s);
            AdjacencyMatrix graph = new AdjacencyMatrix(vertexCount);
            String[] vertices = getVertices(s, vertexCount);
            for (String vertex : vertices) {
                graph.addVertex(vertex);
            }
            int edgeCount = getEdgeCount(s);
            String[][] edges = graph.getEdges(s, edgeCount, new HashSet<>(Arrays.asList(vertices)));
            for (String[] edge : edges) {
                graph.addEdge(edge[0], edge[1], edge[2].equals("1"));
            }

            graph.printMatrix();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getVertexCount(Scanner s) {
        System.out.print("Enter the number of vertices: ");
        int vertexCount = s.nextInt();
        s.nextLine();  // consume the newline
        return vertexCount;
    }

    private static int getEdgeCount(Scanner s) {
        System.out.print("Enter the number of edges: ");
        int edgeCount = s.nextInt();
        s.nextLine();  // consume the newline
        return edgeCount;
    }
}