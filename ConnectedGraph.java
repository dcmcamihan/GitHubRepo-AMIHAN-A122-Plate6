import java.util.*;

/**
 * This class represents a Connected Graph.
 * It provides methods to add vertices and edges, perform depth-first search (DFS), check if the graph is connected, and calculate the number of connected components.
 */
public class ConnectedGraph {
    private final List<List<Integer>> graphAdjacencyList;
    private final int totalVertices;
    private final Map<String, Integer> vertexIndexMap;

    /**
     * Constructs a new ConnectedGraph with the specified number of vertices.
     * @param totalVertices the number of vertices in the graph
     */
    public ConnectedGraph(int totalVertices) {
        this.totalVertices = totalVertices;
        graphAdjacencyList = new ArrayList<>(totalVertices);
        vertexIndexMap = new HashMap<>();
        for (int i = 0; i < totalVertices; i++) {
            graphAdjacencyList.add(new LinkedList<>());
        }
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex the vertex to be added
     */
    public void addVertex(String vertex) {
        if (!vertexIndexMap.containsKey(vertex)) {
            vertexIndexMap.put(vertex, vertexIndexMap.size());
        }
    }

    /**
     * Adds an edge between two vertices in the graph.
     * @param vertexOne the first vertex of the edge
     * @param vertexTwo the second vertex of the edge
     */
    public void createEdge(String vertexOne, String vertexTwo) throws IllegalArgumentException {
        if (!vertexIndexMap.containsKey(vertexOne) || !vertexIndexMap.containsKey(vertexTwo)) {
            throw new IllegalArgumentException("One or both vertices not found in the graph.");
        }
        int indexOne = vertexIndexMap.get(vertexOne);
        int indexTwo = vertexIndexMap.get(vertexTwo);
        graphAdjacencyList.get(indexOne).add(indexTwo);
        graphAdjacencyList.get(indexTwo).add(indexOne);
    }

    /**
     * Performs depth-first search (DFS) from a given vertex.
     * @param currentVertex the vertex to start the DFS from
     * @param visitedVertices an array that keeps track of the visited vertices
     */
    private void executeDFS(int currentVertex, boolean[] visitedVertices) {
        // Mark the current vertex as visited
        visitedVertices[currentVertex] = true;
        // Visit all the adjacent vertices that haven't been visited yet
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
        // Start DFS from the first vertex
        executeDFS(0, visitedVertices);
        // If any vertex hasn't been visited, the graph is not connected
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
        // For each vertex, if it hasn't been visited, perform DFS and increment the component count
        for (int vertex = 0; vertex < totalVertices; vertex++) {
            if (!visitedVertices[vertex]) {
                executeDFS(vertex, visitedVertices);
                componentCount++;
            }
        }
        return componentCount;
    }

    public Map<String, Integer> getVertexIndexMap() {
        return vertexIndexMap;
    }

    /**
     * The main method that runs the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Enter the number of vertices\t\t\t\t\t\t\t: ");
        int totalVertices = userInput.nextInt();

        ConnectedGraph graphChecker = new ConnectedGraph(totalVertices);

        System.out.print("Enter the vertices, separated by a comma then space (, ): ");
        userInput.nextLine(); // Consume newline left-over
        String vertices = userInput.nextLine();
        String[] verticesArray = vertices.split(", ");
        for (String vertex : verticesArray) {
            graphChecker.addVertex(vertex);
        }

        System.out.print("Enter the number of edges\t\t\t\t\t\t\t\t: ");
        int totalEdges = userInput.nextInt();

        System.out.println("Enter the edges (pair of vertices):");
        for (int i = 0; i < totalEdges; i++) {
            String vertexOne = userInput.next();
            String vertexTwo = userInput.next();

            if (!graphChecker.getVertexIndexMap().containsKey(vertexOne) || !graphChecker.getVertexIndexMap().containsKey(vertexTwo)) {
                System.out.println("Invalid vertices. Please enter valid vertices.");
                i--;
                continue;
            }

            try {
                graphChecker.createEdge(vertexOne, vertexTwo);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                i--;
            }
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