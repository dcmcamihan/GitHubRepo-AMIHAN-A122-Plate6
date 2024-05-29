import java.util.*;

/**
 * This class represents a graph and provides methods to calculate and print the degree of each vertex.
 */
public class VertexDegree {
    private final List<List<Integer>> adjacencyList;
    private final Map<String, Integer> vertexIndexMap;

    /**
     * Constructs a new VertexDegree with the specified number of vertices.
     * @param vertexCount the number of vertices in the graph
     */
    public VertexDegree(int vertexCount) {
        adjacencyList = new ArrayList<>(vertexCount);
        vertexIndexMap = new HashMap<>();
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new LinkedList<>());
        }
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex the vertex to add
     */
    public void addVertex(String vertex) {
        // If the vertex is not already in the map, add it with a new index
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertexIndexMap.size());
        }
    }

    /**
     * Adds an edge between two vertices.
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     * @throws IllegalArgumentException if either vertex does not exist in the graph
     */
    public void addEdge(String vertex1, String vertex2) {
        // Ensure both vertices are already added to the graph
        if (!vertexIndexMap.containsKey(vertex1) || !vertexIndexMap.containsKey(vertex2)) {
            throw new IllegalArgumentException("Both vertices must be added before adding an edge.");
        }
        // Get the indices of the vertices from the map
        int index1 = vertexIndexMap.get(vertex1);
        int index2 = vertexIndexMap.get(vertex2);
        // Add each vertex to the other's adjacency list to create an undirected edge
        adjacencyList.get(index1).add(index2);
        adjacencyList.get(index2).add(index1);
    }

    /**
     * Returns the degree of a vertex.
     * @param vertex the vertex to get the degree of
     * @return the degree of the vertex
     * @throws IllegalArgumentException if the vertex does not exist in the graph
     */
    public int getVertexDegree(String vertex) {
        // Ensure the vertex exists in the graph
        if (!vertexIndexMap.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist.");
        }
        // Get the index of the vertex and return the size of its adjacency list
        int index = vertexIndexMap.get(vertex);
        return adjacencyList.get(index).size();
    }

    /**
     * Prints the degree of all vertices.
     */
    public void printAllDegrees() {
        for (Map.Entry<String, Integer> entry : vertexIndexMap.entrySet()) {
            String vertex = entry.getKey();
            System.out.println("Degree of vertex " + vertex + ": " + getVertexDegree(vertex));
        }
    }

    /**
     * The main method that runs the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int vertexCount = s.nextInt();

        VertexDegree calculator = new VertexDegree(vertexCount);

        System.out.println("Enter the vertices:");
        for (int i = 0; i < vertexCount; i++) {
            String vertex = s.next();
            calculator.addVertex(vertex);
        }

        System.out.print("Enter the number of edges: ");
        int edgeCount = s.nextInt();

        System.out.println("Enter the edges or pair of vertices (u v):");
        for (int i = 0; i < edgeCount; i++) {
            String vertex1 = s.next();
            String vertex2 = s.next();

            try {
                calculator.addEdge(vertex1, vertex2);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                i--;
            }
        }

        calculator.printAllDegrees();

        s.close();
    }
}