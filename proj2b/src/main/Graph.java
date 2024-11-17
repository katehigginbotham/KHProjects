package main;

import java.util.*;

public class Graph {
    private Map<Integer, Set<Integer>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public void addEdge(int from, int to) {
        adjList.putIfAbsent(from, new HashSet<>());
        adjList.get(from).add(to);
    }

    public Set<Integer> neighbors(int synsetId) {
        return adjList.getOrDefault(synsetId, new HashSet<>());
    }

    public Set<Integer> getAllNodes() {
        return adjList.keySet();
    }

    // Depth-first search (DFS) to find all reachable nodes from a synset
    public Set<Integer> dfs(int startNode) {
        Set<Integer> visited = new HashSet<>();
        dfsHelper(startNode, visited);
        return visited;
    }

    private void dfsHelper(int node, Set<Integer> visited) {
        if (!visited.contains(node)) {
            visited.add(node);
            for (int neighbor : neighbors(node)) {
                dfsHelper(neighbor, visited);
            }
        }
    }
}
