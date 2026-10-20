package connectivity;

import graph.Edge;
import graph.Graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ConnectivityAlgorithms {
    public static class TopologicalResult {
        public List<Integer> order = new ArrayList<>();
        public boolean acyclic;
    }

    public static TopologicalResult topologicalSort(Graph graph) {
        if (!graph.directed) {
            throw new IllegalArgumentException("Topological sort needs a directed graph.");
        }

        int[] indegree = new int[graph.vertices];

        for (Edge edge : graph.edges) {
            indegree[edge.to]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int v = 0; v < graph.vertices; v++) {
            if (indegree[v] == 0) {
                queue.add(v);
            }
        }

        TopologicalResult result = new TopologicalResult();

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            result.order.add(vertex);

            for (Edge edge : graph.adjacency.get(vertex)) {
                indegree[edge.to]--;
                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        result.acyclic = result.order.size() == graph.vertices;
        return result;
    }

    public static List<List<Integer>> kosaraju(Graph graph) {
        if (!graph.directed) {
            throw new IllegalArgumentException("SCC needs a directed graph.");
        }

        boolean[] visited = new boolean[graph.vertices];
        List<Integer> order = new ArrayList<>();

        for (int v = 0; v < graph.vertices; v++) {
            if (!visited[v]) {
                finishDfs(graph, v, visited, order);
            }
        }

        Graph reversed = new Graph(graph.vertices, true);
        for (Edge edge : graph.edges) {
            reversed.addEdge(edge.to, edge.from, edge.weight);
        }

        Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();

        for (int i = order.size() - 1; i >= 0; i--) {
            int v = order.get(i);
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                collectDfs(reversed, v, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private static void finishDfs(Graph graph, int v, boolean[] visited, List<Integer> order) {
        visited[v] = true;
        for (Edge edge : graph.adjacency.get(v)) {
            if (!visited[edge.to]) {
                finishDfs(graph, edge.to, visited, order);
            }
        }
        order.add(v);
    }

    private static void collectDfs(Graph graph, int v, boolean[] visited, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (Edge edge : graph.adjacency.get(v)) {
            if (!visited[edge.to]) {
                collectDfs(graph, edge.to, visited, component);
            }
        }
    }
}
