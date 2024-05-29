import java.util.*;

/**
 * This class represents a Connected Graph.
 * It provides methods to add vertices and edges, perform depth-first search (DFS), check if the graph is connected, and calculate the number of connected components.
 */
public class ConnectedGraph {
    private final List<List<Integer>> graphAdjacencyList;
    private final int totalVertices;

    /**
     * Constructs a new ConnectedGraph with the specified number of vertices.
     * @param totalVertices the number of vertices in the graph
     */
    public ConnectedGraph(int totalVertices) {
        this.totalVertices = totalVertices;
        graphAdjacencyList = new ArrayList<>(totalVertices);
        for (int i = 0; i < totalVertices; i++) {
            graphAdjacencyList.add(new LinkedList<>());
        }
    }

    /**
     * Adds an edge between two vertices in the graph.
     * @param vertexOne the first vertex of the edge
     * @param vertexTwo the second vertex of the edge
     */
    public void createEdge(int vertexOne, int vertexTwo) {
        graphAdjacencyList.get(vertexOne).add(vertexTwo);
        graphAdjacencyList.get(vertexTwo).add(vertexOne);
    }

    /**
     * Performs depth-first search (DFS) from a given vertex.
     * @param currentVertex the vertex to start the DFS from
     * @param visitedVertices an array that keeps track of the visited vertices
     */
    private void executeDFS(int currentVertex, boolean[] visitedVertices) {
        // Mark the current vertex as visited
        visitedVertices[currentVertex] = true;
        // Visit all the adjacent vertices
        for (int adjacentVertex : graphAdjacencyList.get(currentVertex)) {
            if (!visitedVertices[adjacentVertex]) {
                executeDFS(adjacentVertex, visitedVertices);
            }
        }
    }

    /**
     * Checks if the graph is connected.
     * @return true if the graph is connected, false otherwise
     */
    public boolean checkGraphConnectivity() {
        // Array to keep track of visited vertices
        boolean[] visitedVertices = new boolean[totalVertices];
        // Start DFS from the first vertex (vertex 0)
        executeDFS(0, visitedVertices);
        // Check if all vertices are visited
        for (boolean visitStatus : visitedVertices) {
            if (!visitStatus) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the number of connected components in the graph.
     * @return the number of connected components
     */
    public int calculateConnectedComponents() {
        // Array to keep track of visited vertices
        boolean[] visitedVertices = new boolean[totalVertices];
        int componentCount = 0;
        // Perform DFS for each unvisited vertex and count the components
        for (int vertex = 0; vertex < totalVertices; vertex++) {
            // Check if the vertex has any edges
            if (!graphAdjacencyList.get(vertex).isEmpty() && !visitedVertices[vertex]) {
                executeDFS(vertex, visitedVertices);
                componentCount++;
            }
        }
        return componentCount;
    }

    /**
     * The main method that runs the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Enter the number of vertices:");
        int totalVertices = userInput.nextInt();

        ConnectedGraph graphChecker = new ConnectedGraph(totalVertices);

        System.out.println("Enter the number of edges:");
        int totalEdges = userInput.nextInt();

        System.out.println("Enter the edges (pair of vertices):");
        for (int i = 0; i < totalEdges; i++) {
            int vertexOne = userInput.nextInt();
            int vertexTwo = userInput.nextInt();

            if (vertexOne >= totalVertices || vertexTwo >= totalVertices || vertexOne < 0 || vertexTwo < 0) {
                System.out.println("Invalid vertices. Vertices should be between 0 and " + (totalVertices - 1));
                i--;
                continue;
            }

            graphChecker.createEdge(vertexOne, vertexTwo);
        }

        if (graphChecker.checkGraphConnectivity()) {
            System.out.println("The graph is connected.");
        } else {
            System.out.println("The graph is not connected.");
            System.out.println("Number of connected components: " + graphChecker.calculateConnectedComponents());
        }

        userInput.close();
    }
}