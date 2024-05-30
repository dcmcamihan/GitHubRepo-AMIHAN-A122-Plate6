import java.util.*;

/**
 * This class represents a Bipartite Graph.
 * It provides methods to add vertices and edges, check if a vertex does not exist, and check if the graph is bipartite.
 */
public class BipartiteGraph {
    private final Map<String, List<String>> adjacencyList;
    private final Map<String, Integer> vertexSet;

    /**
     * Constructs a new BipartiteGraph with the specified number of vertices.
     * @param vertexCount the number of vertices in the graph
     */
    public BipartiteGraph(int vertexCount) {
        adjacencyList = new HashMap<>(vertexCount);
        vertexSet = new HashMap<>(vertexCount);
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex the vertex to be added
     */
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        vertexSet.putIfAbsent(vertex, -1);
    }

    /**
     * Adds an edge between two vertices in the graph.
     * @param vertexOne the first vertex of the edge
     * @param vertexTwo the second vertex of the edge
     */
    public void addEdge(String vertexOne, String vertexTwo) {
        adjacencyList.get(vertexOne).add(vertexTwo);
        adjacencyList.get(vertexTwo).add(vertexOne);
    }

    /**
     * Checks if a vertex does not exist in the graph.
     * @param vertex the vertex to be checked
     * @return true if the vertex does not exist, false otherwise
     */
    public boolean vertexDoesNotExist(String vertex) {
        return !adjacencyList.containsKey(vertex);
    }

    /**
     * Checks if the graph is bipartite.
     * @return true if the graph is bipartite, false otherwise
     */
    public boolean checkIfBipartite() {
        // Try to assign each unassigned vertex to a set
        for (String startingVertex : adjacencyList.keySet()) {
            if (vertexSet.get(startingVertex) == -1) {
                // Assign the vertex to set 0
                vertexSet.put(startingVertex, 0);
                // Try to assign the other vertices so that no two adjacent vertices are in the same set
                if (cannotAssignSets(startingVertex)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tries to assign vertices to sets.
     * @param startingVertex the vertex to start the assignment from
     * @return true if the assignment was not successful, false otherwise
     */
    private boolean cannotAssignSets(String startingVertex) {
        for (String neighbor : adjacencyList.get(startingVertex)) {
            if (vertexSet.get(neighbor) == -1) {
                vertexSet.put(neighbor, 1 - vertexSet.get(startingVertex));
                if (cannotAssignSets(neighbor)) {
                    return true;
                }
            } else if (vertexSet.get(neighbor).equals(vertexSet.get(startingVertex))) {
                return true;
            }
        }
        return false;
    }

    /**
     * The main method that runs the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter the number of vertices\t\t\t\t\t\t\t: ");
        int vertexCount = s.nextInt();
        s.nextLine();  // consume the newline

        BipartiteGraph checker = new BipartiteGraph(vertexCount);

        System.out.print("Enter the vertices, separated by a comma then space (, ): ");
        String[] vertices = s.nextLine().trim().split(", ");
        if (vertices.length != vertexCount) {
            System.out.println("Invalid input. Please enter exactly " + vertexCount + " vertices separated by comma and space.");
            return;
        }
        for (String vertex : vertices) {
            checker.addVertex(vertex);
        }

        System.out.print("Enter the number of edges\t\t\t\t\t\t\t\t: ");
        try {
            int edgeCount = s.nextInt();
            s.nextLine();

            System.out.println("Enter the edges (pairs of vertices):");
            for (int i = 0; i < edgeCount; i++) {
                String[] edgeVertices = s.nextLine().split(" ");
                if (edgeVertices.length != 2) {
                    System.out.println("Invalid input. Please enter two vertices separated by a space.");
                    i--;
                    continue;
                }
                String vertexOne = edgeVertices[0];
                String vertexTwo = edgeVertices[1];
                if (checker.vertexDoesNotExist(vertexOne) || checker.vertexDoesNotExist(vertexTwo)) {
                    System.out.println("One or both vertices do not exist in the graph. Please enter valid vertices.");
                    i--;
                    continue;
                }
                checker.addEdge(vertexOne, vertexTwo);
            }

            if (checker.checkIfBipartite()) {
                System.out.println("The graph is bipartite.");
            } else {
                System.out.println("The graph is not bipartite.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number for the number of edges.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}