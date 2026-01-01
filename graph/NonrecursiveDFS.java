package graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class NonrecursiveDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    
    public NonrecursiveDFS(Graph G, int s) {
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    public void dfs(Graph G, int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        marked[s] = true;

        while (!stack.isEmpty()) {
            int v = stack.pop();

            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                    edgeTo[w] = v;
                }
            }

        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= marked.length) {
            throw new IllegalArgumentException("vertex invalid.");
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);

        if (!hasPathTo(v)) {
            return null;
        }

        Stack<Integer> path = new Stack<>();
        
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);

        List<Integer> result = new ArrayList<>();
        
        while (!path.isEmpty()) {
            result.add(path.pop());
        }
        
        return path;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph G = new Graph(sc);

        int s = sc.nextInt();
        NonrecursiveDFS dfs = new NonrecursiveDFS(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.print(s + " to " + v);

                for (int x : dfs.pathTo(v)) {
                    if (x == s) {
                        System.out.print(x);
                    }
                    else {
                        System.out.print("-" + x);
                    }
                }
                System.out.println();
            }
            else {
                System.out.print(s + " to " + v + ": not connected\n");
            }
        }
    }
}
