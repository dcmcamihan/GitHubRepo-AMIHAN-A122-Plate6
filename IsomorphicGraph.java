import java.util.*;

/**
 * This class represents an Isomorphic Graph validator.
 * It checks if two given graphs are isomorphic or not.
 */
public class IsomorphicGraph {
    private int[][] firstGraph;
    private int[][] secondGraph;
    private boolean[] visitedVertices;
    private int[] vertexMapping;

    /**
     * Constructs an IsomorphicGraph object with two given graphs.
     *
     * @param firstGraph  the adjacency matrix of the first graph
     * @param secondGraph the adjacency matrix of the second graph
     * @throws IllegalArgumentException if the two graphs have different number of vertices
     */
    public IsomorphicGraph(int[][] firstGraph, int[][] secondGraph) {
        if (firstGraph.length != secondGraph.length) {
            throw new IllegalArgumentException("Graphs must have the same number of vertices");
        }

        this.firstGraph = firstGraph;
        this.secondGraph = secondGraph;
        this.visitedVertices = new boolean[firstGraph.length];
        this.vertexMapping = new int[firstGraph.length];
        Arrays.fill(vertexMapping, -1);
    }

    /**
     * Checks if there is a connection between two vertices in the first graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if there is a connection, false otherwise
     */
    private boolean isConnectedInFirstGraph(int vertex1, int vertex2) {
        return firstGraph[vertex1][vertex2] == 1;
    }

    /**
     * Checks if two vertices are isomorphic.
     *
     * @param vertex1 the vertex in the first graph
     * @param vertex2 the vertex in the second graph
     * @return true if the vertices are isomorphic, false otherwise
     */
    private boolean isIsomorphic(int vertex1, int vertex2) {
        for (int i = 0; i < firstGraph.length; i++) {
            if (vertexMapping[i] != -1 && (isConnectedInFirstGraph(vertex1, i) != isConnectedInFirstGraph(vertex2, vertexMapping[i]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the graphs are isomorphic starting from a given vertex.
     *
     * @param vertex the starting vertex
     * @return true if the graphs are isomorphic, false otherwise
     */
    private boolean checkIsomorphismFromVertex(int vertex) {
        for (int i = 0; i < secondGraph.length; i++) {
            if (!visitedVertices[i] && isIsomorphic(vertex, i)) {
                visitedVertices[i] = true;
                vertexMapping[vertex] = i;

                int nextVertex = vertex + 1;
                if (nextVertex == firstGraph.length || checkIsomorphismFromVertex(nextVertex)) {
                    return true;
                }

                visitedVertices[i] = false;
                vertexMapping[vertex] = -1;
            }
        }
        return false;
    }

    /**
     * Checks if the two graphs are isomorphic.
     *
     * @return true if the graphs are isomorphic, false otherwise
     */
    public boolean areGraphsIsomorphic() {
        return checkIsomorphismFromVertex(0);
    }

    /**
     * The main method that drives the program.
     * It asks for user input to define two graphs and checks if they are isomorphic.
     *
     * @param args command line arguments (not used)
     */



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] firstGraph = null;
        int[][] secondGraph = null;

        for (int graphIndex = 1; graphIndex <= 2; graphIndex++) {
            HashMap<String, Integer> vertexIndices = new HashMap<>();
            int vertexIndex = 0;

            System.out.print("Enter the number of vertices in graph " + graphIndex + ": ");
            int vertices = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the number of edges in graph " + graphIndex + ": ");
            int edges = Integer.parseInt(scanner.nextLine());

            System.out.println("\nAdjacency matrix for graph " + graphIndex);
            int[][] graph = new int[vertices][vertices];
            for (int i = 0; i < edges; i++) {
                System.out.println("Enter the vertices for edge " + (i + 1) + " (0-indexed):");
                String[] verticesForEdge = scanner.nextLine().split(" ");
                String vertex1 = verticesForEdge[0];
                String vertex2 = verticesForEdge[1];

                if (!vertexIndices.containsKey(vertex1)) {
                    vertexIndices.put(vertex1, vertexIndex++);
                }
                if (!vertexIndices.containsKey(vertex2)) {
                    vertexIndices.put(vertex2, vertexIndex++);
                }

                graph[vertexIndices.get(vertex1)][vertexIndices.get(vertex2)] = 1;
                graph[vertexIndices.get(vertex2)][vertexIndices.get(vertex1)] = 1;
            }

            if (graphIndex == 1) {
                firstGraph = graph;
            } else {
                secondGraph = graph;
            }
        }

        IsomorphicGraph validator = new IsomorphicGraph(firstGraph, secondGraph);
        System.out.println("Are the two graphs isomorphic? " + (validator.areGraphsIsomorphic() ? "Yes" : "No"));
    }

}