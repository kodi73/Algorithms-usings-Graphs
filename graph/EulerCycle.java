package graph;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class EulerCycle {
    public static boolean hasEulerCycle(Graph G) {
        if (!isConnected(G)) {
            return false;
        }

        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    public static List<Integer> findEulerCycle(Graph G) {
        if (!hasEulerCycle(G)) {
            return Collections.emptyList();
        }

        Graph localGraph = new Graph(G);
        Stack<Integer> stack = new Stack<>();
        List<Integer> cycle = new ArrayList<>();
        
        stack.push(0);

        while (!stack.isEmpty()) {
            int v = stack.pop();

            while (localGraph.degree(v) != 0) {
                int w = localGraph.adj(v).iterator().next();
                localGraph.removeEdge(v, w);
                stack.push(v);
                v = w;
            }

            cycle.add(v);
        }

        Collections.reverse(cycle);

        return cycle;
    }

    private static boolean isConnected(Graph G) {
        boolean[] marked = new boolean[G.V()];
        int start = -1;

        for (int i = 0; i < G.V(); i++) {
            if (G.degree(i) > 0) {
                start = i;
                break;
            }
        }
        
        // Individual connected components may be there: inherently Euler Cycle
        if (start == -1) return true;

        Stack<Integer> stack = new Stack<>();

        stack.push(start);
        marked[start] = true;

        while (!stack.isEmpty()) {
            int v = stack.pop();

            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            }
        }

        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0 && !marked[v]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Graph G = new Graph(6);
        G.addEdge(0,1);
        G.addEdge(1,2);
        G.addEdge(2,0);
        G.addEdge(2,3);
        G.addEdge(3,4);
        G.addEdge(4,2);
        G.addEdge(4,5);
        G.addEdge(5,0);
        G.addEdge(0,4);

        if (EulerCycle.hasEulerCycle(G)) {
            List<Integer> cycle = findEulerCycle(G);
            System.out.println("Euler Cycle: " + cycle);
        }
        else {
            System.out.println("No Euler Cycle exists.");
        }
    }
}
