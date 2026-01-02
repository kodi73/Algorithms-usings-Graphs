package digraphs;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class ShortestDirectedCycle {
    public static List<Integer> findShortestCycle(Digraph G) {
        int minCycleLength = Integer.MAX_VALUE;
        List<Integer> bestCycle = new ArrayList<>();

        for (int start = 0; start < G.V(); start++) {
            int[] distTo = new int[G.V()];
            int[] edgeTo = new int[G.V()];
            boolean[] marked = new boolean[G.V()];
            Arrays.fill(edgeTo, -1);
            Queue<Integer> q = new ArrayDeque<>();

            distTo[start] = 0;
            q.offer(start);
            marked[start] = true;

            while (!q.isEmpty()) {
                int v = q.poll();

                for (int w : G.adj(v)) {
                    if (w == start && distTo[v] + 1 < minCycleLength) {
                        minCycleLength = distTo[v] + 1;
                        bestCycle = reconstructCycle(start, v, edgeTo);
                    }

                    if (!marked[w]) {
                        marked[w] = true;
                        distTo[w] = distTo[v] + 1;
                        edgeTo[w] = v;
                        q.offer(w);
                    }
                }
            }

        }

        return bestCycle;
    }

    private static List<Integer> reconstructCycle(int start, int end, int[] edgeTo) {
        List<Integer> cycle = new ArrayList<>();
        int current = end;

        while (current != -1) {
            cycle.add(current);
            current = edgeTo[current];
        }

        Collections.reverse(cycle);
        cycle.add(start);

        return cycle;
    }
    public static void main(String[] args) {
        Digraph g = new Digraph(5);

        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0); // cycle of length 3
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        List<Integer> cycle = findShortestCycle(g);

        if (cycle.isEmpty()) {
            System.out.println("Graph is acyclic");
        } else {
            System.out.println("Shortest directed cycle: " + cycle);
        }
    }
}