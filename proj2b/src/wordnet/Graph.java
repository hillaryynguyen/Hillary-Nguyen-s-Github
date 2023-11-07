package wordnet;

import java.util.*;

public class Graph<T> {
    private Map<T, Set<T>> edges = new HashMap<>();

    public void addNode(T node) {
        edges.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(T from, T to) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }

    public Set<T> getAllConnectedNodes(T start) {
        Set<T> connectedNodes = new HashSet<>();
        dfs(start, connectedNodes);
        return connectedNodes;
    }

    private void dfs(T node, Set<T> connectedNodes) {
        // Mark the node as visited by adding it to the set
        connectedNodes.add(node);
        // For every connected node, if it's not already visited, continue the DFS
        if (edges.containsKey(node)) {
            for (T neighbor : edges.get(node)) {
                if (!connectedNodes.contains(neighbor)) {
                    dfs(neighbor, connectedNodes);
                }
            }
        }
    }
}
