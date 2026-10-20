package shortestpath;

import graph.Edge;
import graph.Graph;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    public static class Result {
        public long[] distance;
        public int[] previous;
        public long operations;

        public List<Integer> pathTo(int target) {
            if (distance[target] == Long.MAX_VALUE) {
                return Collections.emptyList();
            }

            LinkedList<Integer> path = new LinkedList<>();
            for (int v = target; v != -1; v = previous[v]) {
                path.addFirst(v);
            }
            return path;
        }
    }

    private static class State {
        int vertex;
        long distance;

        State(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static Result shortestPaths(Graph graph, int source) {
        int n = graph.vertices;
        Result result = new Result();
        result.distance = new long[n];
        result.previous = new int[n];

        Arrays.fill(result.distance, Long.MAX_VALUE);
        Arrays.fill(result.previous, -1);
        result.distance[source] = 0;

        PriorityQueue<State> queue =
                new PriorityQueue<>((a, b) -> Long.compare(a.distance, b.distance));
        queue.add(new State(source, 0));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.distance != result.distance[current.vertex]) {
                continue;
            }

            for (Edge edge : graph.adjacency.get(current.vertex)) {
                result.operations++;
                long newDistance = current.distance + edge.weight;

                if (newDistance < result.distance[edge.to]) {
                    result.distance[edge.to] = newDistance;
                    result.previous[edge.to] = current.vertex;
                    queue.add(new State(edge.to, newDistance));
                }
            }
        }

        return result;
    }
}
