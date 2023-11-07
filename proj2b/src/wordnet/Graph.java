package wordnet;

import java.util.*;

public class Graph<T> {
    private final Map<T, Set<T>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addNode(T node) {
        adjacencyList.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(T source, T destination) {
        adjacencyList.computeIfAbsent(source, k -> new HashSet<>()).add(destination);
    }

    public Set<T> getConnectedNodes(T startNode) {
        Set<T> visited = new HashSet<>();
        performDepthFirstSearch(startNode, visited);
        return visited;
    }

    private void performDepthFirstSearch(T currentNode, Set<T> visited) {
        visited.add(currentNode);
        adjacencyList.getOrDefault(currentNode, Collections.emptySet()).forEach(neighbor -> {
            if (!visited.contains(neighbor)) {
                performDepthFirstSearch(neighbor, visited);
            }
        });
    }
}
