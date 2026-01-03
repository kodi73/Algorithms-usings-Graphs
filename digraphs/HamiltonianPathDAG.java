package digraphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HamiltonianPathDAG {
    private boolean[] marked;
    private Stack<Integer> reversePostOrder;

    HamiltonianPathDAG(Digraph G) {
        reversePostOrder = new Stack<>();
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }

        reversePostOrder.push(v);
    }

    @SuppressWarnings("unchecked")
    private List<Integer> getHamiltonianPath(Digraph G) {
        List<Integer> topoOrder = new ArrayList<>();
        Stack<Integer> temp = (Stack<Integer>) reversePostOrder.clone();

        while (!temp.isEmpty()) {
            topoOrder.add(temp.pop());
        }

        for (int i = 0; i < topoOrder.size() - 1; i++) {
            int u = topoOrder.get(i);
            int v = topoOrder.get(i + 1);

            boolean edgeExists = false;
            for (int w : G.adj(u)) {
                if (w == v) {
                    edgeExists = true;
                    break;
                }
            }

            if (!edgeExists) {
                return List.of(); // no Hamiltonian path
            }
        }

        return topoOrder;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        HamiltonianPathDAG hm = new HamiltonianPathDAG(g);
        List<Integer> path = hm.getHamiltonianPath(g);

        if (path.isEmpty()) {
            System.out.println("No Hamiltonian path present.");
        } else {
            System.out.println("Hamiltonian Path of DAG is: " + path);
        }
    }
}
