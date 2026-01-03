package digraphs;

public class ReachableVertexDigraph {

    /**
     * @return vertex reachable from all others, or -1 if none exists
     */
    public static int findReachableVertex(Digraph G) {

        Digraph R = G.reverse();
        boolean[] marked = new boolean[G.V()];
        int candidate = -1;

        // Step 1: DFS on reversed graph, track last finished vertex
        for (int v = 0; v < R.V(); v++) {
            if (!marked[v]) {
                dfs(R, v, marked);
                candidate = v;
            }
        }

        // Step 2: Verify candidate
        boolean[] visited = new boolean[G.V()];
        dfs(R, candidate, visited);

        for (boolean v : visited) {
            if (!v) return -1;
        }

        return candidate;
    }

    private static void dfs(Digraph G, int v, boolean[] marked) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w, marked);
        }
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(5);
        g.addEdge(0, 4);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 4);

        int v = findReachableVertex(g);

        if (v == -1)
            System.out.println("No such vertex exists.");
        else
            System.out.println("Reachable vertex: " + v);
    }
}
