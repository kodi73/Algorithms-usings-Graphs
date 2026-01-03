package digraphs;

import java.util.Stack;

public class ReachableVertexDAG {

    private boolean[] marked;
    private Stack<Integer> reversePost;

    public ReachableVertexDAG(Digraph G) {
        marked = new boolean[G.V()];
        reversePost = new Stack<>();

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
        reversePost.push(v);
    }

    /**
     * @return vertex reachable from all others, or -1 if none exists
     */
    public int findReachableVertex(Digraph G) {

        // Candidate = last vertex in topological order
        int candidate = reversePost.pop();

        Digraph R = G.reverse();
        boolean[] visited = new boolean[G.V()];

        dfsReverse(R, candidate, visited);

        for (boolean v : visited) {
            if (!v) return -1;
        }

        return candidate;
    }

    private void dfsReverse(Digraph G, int v, boolean[] visited) {
        visited[v] = true;
        for (int w : G.adj(v)) {
            if (!visited[w]) dfsReverse(G, w, visited);
        }
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(4);
        g.addEdge(0, 3);
        g.addEdge(1, 3);
        g.addEdge(2, 3);

        ReachableVertexDAG r = new ReachableVertexDAG(g);
        int v = r.findReachableVertex(g);

        if (v == -1)
            System.out.println("No such vertex exists.");
        else
            System.out.println("Reachable vertex: " + v);
    }
}
