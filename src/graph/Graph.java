package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public int vertices;
    public boolean directed;
    public List<Edge> edges = new ArrayList<>();
    public List<List<Edge>> adjacency = new ArrayList<>();

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        for (int i = 0; i < vertices; i++) {
            adjacency.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacency.get(from).add(edge);

        if (!directed && from != to) {
            adjacency.get(to).add(new Edge(to, from, weight));
        }
    }
}
