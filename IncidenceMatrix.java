import java.util.*;

/**
 * This class represents a Graph using an incidence matrix.
 * It allows adding vertices and edges to the graph.
 * The graph is undirected.
 */
public class IncidenceMatrix {
    private int[][] incidenceMatrix;
    private Map<String, Integer> vertexIndexMap;
    private Map<String, Integer> edgeIndexMap;

    /**
     * Constructor for the Graph.
     * @param vertexCount the number of vertices in the graph
     * @param edgeCount the number of edges in the graph
     */
    public IncidenceMatrix(int vertexCount, int edgeCount) {
        incidenceMatrix = new int[vertexCount][edgeCount];
        vertexIndexMap = new HashMap<>();
        edgeIndexMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex the vertex to be added
     * @throws IllegalArgumentException if the vertex already exists
     */
    public void addVertex(String vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertexIndexMap.size());
        }
    }

    /**
     * Adds an edge to the graph.
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     * @param count the number of times the edge appears
     * @throws IllegalArgumentException if either vertex does not exist
     */
    public void addEdge(String vertex1, String vertex2, int count) {
        if (!vertexIndexMap.containsKey(vertex1) || !vertexIndexMap.containsKey(vertex2)) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
        String edge = vertex1 + "-" + vertex2;
        if (edgeIndexMap.containsKey(edge)) {
            throw new IllegalArgumentException("Edge already exists: " + edge);
        }
        int edgeIndex = edgeIndexMap.size();
        edgeIndexMap.put(edge, edgeIndex);
        int vertex1Index = vertexIndexMap.get(vertex1);
        int vertex2Index = vertexIndexMap.get(vertex2);
        incidenceMatrix[vertex1Index][edgeIndex] = count;
        incidenceMatrix[vertex2Index][edgeIndex] = count;
    }

    /**
     * Prints the incidence matrix to the console.
     */
    public void printMatrix() {
        System.out.println("\nThe incidence matrix of the graph: ");
        for (int i = 0; i < incidenceMatrix.length; i++) {
            for (int j = 0; j < incidenceMatrix[i].length; j++) {
                System.out.print(incidenceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * The main method that drives the program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();

        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();

        IncidenceMatrix incidenceMatrixGraph = new IncidenceMatrix(numVertices, numEdges);

        System.out.println("Enter the vertex pairs associated with each edge and the number of times each edge appears:");
        for (int i = 0; i < numEdges; i++) {
            System.out.print("Enter vertex pair for edge " + (i + 1) + ": ");
            String vertex1 = scanner.next();
            String vertex2 = scanner.next();
            int edgeCount = scanner.nextInt();

            try {
                incidenceMatrixGraph.addVertex(vertex1);
                incidenceMatrixGraph.addVertex(vertex2);
                incidenceMatrixGraph.addEdge(vertex1, vertex2, edgeCount);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                i--; // Decrement i to retry entering the vertex pair
            }
        }

        System.out.println("\nIncidence Matrix:");
        incidenceMatrixGraph.printMatrix();

        scanner.close();
    }
}
