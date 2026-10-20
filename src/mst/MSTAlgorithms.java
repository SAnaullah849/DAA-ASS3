package mst;

import dsu.DSU;
import graph.Edge;
import graph.Graph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MSTAlgorithms {
    public static class Result {
        public List<Edge> edges = new ArrayList<>();
        public long weight;
        public boolean connected;
        public long operations;
    }

    public static Result kruskal(Graph graph) {
        if (graph.directed) {
            throw new IllegalArgumentException("MST needs an undirected graph.");
        }

        List<Edge> sorted = new ArrayList<>(graph.edges);
        sorted.sort(Comparator.comparingInt(edge -> edge.weight));

        DSU dsu = new DSU(graph.vertices);
        Result result = new Result();

        for (Edge edge : sorted) {
            result.operations++;

            if (dsu.union(edge.from, edge.to)) {
                result.edges.add(edge);
                result.weight += edge.weight;

                if (result.edges.size() == graph.vertices - 1) {
                    break;
                }
            }
        }

        result.connected = result.edges.size() == graph.vertices - 1;
        return result;
    }

    public static Result prim(Graph graph) {
        if (graph.directed) {
            throw new IllegalArgumentException("MST needs an undirected graph.");
        }

        Result result = new Result();
        boolean[] visited = new boolean[graph.vertices];
        PriorityQueue<Edge> queue =
                new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));

        visited[0] = true;
        queue.addAll(graph.adjacency.get(0));

        while (!queue.isEmpty() && result.edges.size() < graph.vertices - 1) {
            Edge edge = queue.poll();
            result.operations++;

            if (visited[edge.to]) {
                continue;
            }

            visited[edge.to] = true;
            result.edges.add(edge);
            result.weight += edge.weight;

            for (Edge next : graph.adjacency.get(edge.to)) {
                if (!visited[next.to]) {
                    queue.add(next);
                }
            }
        }

        result.connected = result.edges.size() == graph.vertices - 1;
        return result;
    }
}
