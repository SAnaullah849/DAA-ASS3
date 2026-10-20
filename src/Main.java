import graph.Graph;
import mst.MSTAlgorithms;
import connectivity.ConnectivityAlgorithms;
import shortestpath.Dijkstra;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

//        // Graph 1:  connected graph for MST and Dijkstra
//        Graph city = new Graph(5, false);
//        city.addEdge(0, 1, 4);
//        city.addEdge(0, 2, 2);
//        city.addEdge(1, 2, 1);
//        city.addEdge(1, 3, 5);
//        city.addEdge(2, 3, 8);
//        city.addEdge(2, 4, 10);
//        city.addEdge(3, 4, 2);

        // Graph 2: Disconnected graph and unreachable vertices
        // Graph city = new Graph(6, false);
        // city.addEdge(0, 1, 4);
        // city.addEdge(1, 2, 2);
        // city.addEdge(0, 2, 5);
        // city.addEdge(3, 4, 3);

        // Graph 3: Multiple valid minimum spanning trees
        // Graph city = new Graph(4, false);
        // city.addEdge(0, 1, 1);
        // city.addEdge(0, 2, 1);
        // city.addEdge(0, 3, 1);
        // city.addEdge(1, 2, 1);
        // city.addEdge(1, 3, 1);
        // city.addEdge(2, 3, 1);

        // Graph 4: Duplicate edge weights
        // Graph city = new Graph(5, false);
        // city.addEdge(0, 1, 2);
        // city.addEdge(0, 2, 2);
        // city.addEdge(1, 2, 2);
        // city.addEdge(1, 3, 4);
        // city.addEdge(2, 3, 4);
        // city.addEdge(3, 4, 1);

        // Graph 5: Single vertex
        // Graph city = new Graph(1, false);
//
//        // Graph 6: Directed acyclic graph for topological sorting
//         Graph city = new Graph(6, true);
//         city.addEdge(0, 1, 1);
//         city.addEdge(0, 2, 1);
//         city.addEdge(1, 3, 1);
//         city.addEdge(2, 3, 1);
//         city.addEdge(3, 4, 1);
//         city.addEdge(4, 5, 1);

        // Graph 7: Directed graph containing a cycle
        // Graph city = new Graph(4, true);
        // city.addEdge(0, 1, 1);
        // city.addEdge(1, 2, 1);
        // city.addEdge(2, 0, 1);
        // city.addEdge(2, 3, 1);

         //Graph 8: Strongly connected components (SCC)
         Graph city = new Graph(7, true);
         city.addEdge(0, 1, 1);
         city.addEdge(1, 2, 1);
         city.addEdge(2, 0, 1);
         city.addEdge(2, 3, 1);
         city.addEdge(3, 4, 1);
         city.addEdge(4, 5, 1);
         city.addEdge(5, 3, 1);
         city.addEdge(5, 6, 1);


        if (!city.directed) {
            MSTAlgorithms.Result kruskal = MSTAlgorithms.kruskal(city);
            MSTAlgorithms.Result prim = MSTAlgorithms.prim(city);

            System.out.println("Kruskal MST weight: " + kruskal.weight);
            System.out.println("Prim MST weight: " + prim.weight);
            System.out.println("Connected: " + kruskal.connected);
            System.out.println("Kruskal operations: " + kruskal.operations);
            System.out.println("Prim operations: " + prim.operations);

            Dijkstra.Result shortest = Dijkstra.shortestPaths(city, 0);
            System.out.println("Dijkstra distances from vertex 0: "
                    + Arrays.toString(shortest.distance));

            for (int v = 0; v < city.vertices; v++) {
                System.out.println("Path to " + v + ": " + shortest.pathTo(v));
            }
        }


        if (city.directed) {
            ConnectivityAlgorithms.TopologicalResult topo =
                    ConnectivityAlgorithms.topologicalSort(city);

            System.out.println("Directed graph is a DAG: " + topo.acyclic);
            System.out.println("Topological order: " + topo.order);
            System.out.println("Strongly connected components: "
                    + ConnectivityAlgorithms.kosaraju(city));
        }
    }
}