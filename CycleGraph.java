import java.util.*;

/**
 * This class represents a Cycle Graph.
 * It provides methods to add vertices and edges, perform cycle detection, and print the cycle if it exists.
 */
class CycleGraph {
    private final int vertexCnt;
    private final List<List<Integer>> adjacencyList;
    private final Map<String, Integer> vertexToIndexMap;
    private final Map<Integer, String> indexToVertexMap;
    private final boolean isDirected;

    /**
     * Constructs a new CycleGraph with the specified number of vertices and directed/undirected property.
     * @param vertexCount the number of vertices in the graph
     * @param isDirected whether the graph is directed or not
     */
    public CycleGraph(int vertexCount, boolean isDirected) {
        this.vertexCnt = vertexCount;
        this.isDirected = isDirected;
        adjacencyList = new ArrayList<>(vertexCount);
        vertexToIndexMap = new HashMap<>();
        indexToVertexMap = new HashMap<>();
        for (int i = 0; i < vertexCount; i++)
            adjacencyList.add(new LinkedList<>());
    }

    /**
     * Checks if there's a cycle starting from a given vertex.
     * @param vertexIndex the index of the vertex to start the check from
     * @param visitedVertices an array that keeps track of the visited vertices
     * @param recursionStack an array that keeps track of the vertices in the recursion stack
     * @param cycle a list that stores the cycle if it exists
     * @return true if there's a cycle, false otherwise
     */
    private boolean isCycleFromVertex(int vertexIndex, boolean[] visitedVertices, boolean[] recursionStack, List<Integer> cycle) {
        if (recursionStack[vertexIndex]) {
            cycle.add(vertexIndex);
            return true;
        }

        if (visitedVertices[vertexIndex])
            return false;

        visitedVertices[vertexIndex] = true;
        recursionStack[vertexIndex] = true;

        for (Integer neighborIndex: adjacencyList.get(vertexIndex)) {
            if (isCycleFromVertex(neighborIndex, visitedVertices, recursionStack, cycle)) {
                if (!cycle.contains(vertexIndex)) {
                    cycle.add(vertexIndex);
                }
                return true;
            }
        }

        recursionStack[vertexIndex] = false;
        return false;
    }

    /**
     * Adds an edge between two vertices.
     * @param sourceVertex the source vertex of the edge
     * @param destinationVertex the destination vertex of the edge
     */
    private void addEdge(String sourceVertex, String destinationVertex) {
        if (!vertexToIndexMap.containsKey(sourceVertex)) {
            vertexToIndexMap.put(sourceVertex, vertexToIndexMap.size());
            indexToVertexMap.put(vertexToIndexMap.get(sourceVertex), sourceVertex);
        }
        if (!vertexToIndexMap.containsKey(destinationVertex)) {
            vertexToIndexMap.put(destinationVertex, vertexToIndexMap.size());
            indexToVertexMap.put(vertexToIndexMap.get(destinationVertex), destinationVertex);
        }
        adjacencyList.get(vertexToIndexMap.get(sourceVertex)).add(vertexToIndexMap.get(destinationVertex));
        if (!isDirected) {
            adjacencyList.get(vertexToIndexMap.get(destinationVertex)).add(vertexToIndexMap.get(sourceVertex));
        }
    }

    /**
     * Initiates cycle detection in the graph.
     */
    private void cycleSearch() {
        boolean[] visitedVertices = new boolean[vertexCnt];
        boolean[] recursionStack = new boolean[vertexCnt];
        List<Integer> cycle = new ArrayList<>();

        for (int i = 0; i < vertexCnt; i++) {
            if (!visitedVertices[i] && isCycleFromVertex(i, visitedVertices, recursionStack, cycle)) {
                if (cycle.size() == vertexCnt) {
                    System.out.println("The graph is a cycle");
                    return;
                } else {
                    printCycle(cycle);
                    return;
                }
            }
        }

        System.out.println("The graph doesn't contain a cycle");
    }

    /**
     * Prints the cycle.
     * @param cycle the cycle to print
     */
    private void printCycle(List<Integer> cycle) {
        StringBuilder cycleString = new StringBuilder();
        for (Integer index : cycle) {
            cycleString.append(indexToVertexMap.get(index)).append(" -> ");
        }
        cycleString.append(indexToVertexMap.get(cycle.get(0)));
        System.out.println("The graph contains a cycle: " + cycleString);
    }

    /**
     * The main method that runs the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        int vertexCount = s.nextInt();
        s.nextLine();
        boolean isDirected = getGraphDirection(s);
        CycleGraph graph = new CycleGraph(vertexCount, isDirected);
        System.out.println("Enter the number of edges:");
        int edgeCount = s.nextInt();
        s.nextLine();
        System.out.println("Enter the edges (pair of vertices):");
        for (int i = 0; i < edgeCount; i++) {
            String sourceVertex = s.next();
            String destinationVertex = s.next();
            graph.addEdge(sourceVertex, destinationVertex);
        }
        graph.cycleSearch();
    }

    /**
     * Gets if the graph is directed or not.
     * @param s the Scanner to read the user's input
     * @return true if the graph is directed, false otherwise
     */
    private static boolean getGraphDirection(Scanner s) {
        while (true) {
            System.out.println("Is the graph directed? (yes/no):");
            String input = s.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }
}