import java.util.*;

/**
 * This class represents a graph and provides methods to add vertices and edges,
 * and to print the edges and their counts.
 */
public class EdgeCounter
{
    private int[][] adjacencyMatrix;
    private Map<String, Integer> vertexIndexMap;
    private Map<String, Integer> edgeCountMap;

    /**
     * Constructs a new EdgeCounter with the specified number of vertices.
     *
     * @param vertexCount the number of vertices in the graph
     */
    public EdgeCounter(int vertexCount) {
        adjacencyMatrix = new int[vertexCount][vertexCount];
        vertexIndexMap = new HashMap<>();
        edgeCountMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex to add
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
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     * @param count the number of times the edge appears
     * @throws IllegalArgumentException if either vertex does not exist
     */
    public void addEdge(String vertex1, String vertex2, int count) {
        if (!vertexIndexMap.containsKey(vertex1) || !vertexIndexMap.containsKey(vertex2)) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
        int vertex1Index = vertexIndexMap.get(vertex1);
        int vertex2Index = vertexIndexMap.get(vertex2);
        adjacencyMatrix[vertex1Index][vertex2Index] = count;

        String edge = vertex1 + "-" + vertex2;
        edgeCountMap.put(edge, count);
    }

    /**
     * Prints the edges of the graph and their counts.
     */
    public void printEdges() {
        System.out.println("The edges of the graph and their counts: ");
        for (Map.Entry<String, Integer> entry : edgeCountMap.entrySet()) {
            System.out.println("Edge: " + entry.getKey() + ", Count: " + entry.getValue());
        }
    }

    /**
     * The main method that drives the program.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();
        scanner.nextLine();

        EdgeCounter adjacencyMatrixGraph = new EdgeCounter(numVertices);

        System.out.println("Enter the vertices (can be either string or integer):");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Enter vertex " + (i + 1) + ": ");
            String vertex = scanner.nextLine();
            adjacencyMatrixGraph.addVertex(vertex);
        }

        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                int edgeCount = scanner.nextInt();
                if (edgeCount > 0) {
                    String vertex1 = adjacencyMatrixGraph.vertexIndexMap.keySet().toArray()[i].toString();
                    String vertex2 = adjacencyMatrixGraph.vertexIndexMap.keySet().toArray()[j].toString();
                    adjacencyMatrixGraph.addEdge(vertex1, vertex2, edgeCount);
                }
            }
        }

        System.out.println("\nEdges and their counts:");
        adjacencyMatrixGraph.printEdges();

        scanner.close();
    }
}