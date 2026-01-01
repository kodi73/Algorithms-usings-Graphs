package graph;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS 
{
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    private int[] distTo;

    public BFS(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
	    distTo = new int[G.V()];
	    validateVertex(s);
	    this.s  = s;
	    bfs(G, s);
    }
    
    private void bfs(Graph G, int s)
    {
        Queue<Integer> q = new LinkedList<>();
	    for (int v = 0; v < G.V(); v++)
            distTo[v] = Integer.MAX_VALUE;

	    distTo[s] = 0;

        q.offer(s);
        marked[s] = true;

        while (!q.isEmpty()) 
        {
            int v = q.poll();
            for (int w : G.adj(v))
            {
                if (!marked[w])
                {
                    q.offer(w);
		            distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    edgeTo[w] = v;
                }
            }
        }
    }

    public boolean hasPathTo(int v)
    {
	    validateVertex(v);
	    return marked[v];
    }

    public int distTo(int v)
    {
	    validateVertex(v);
	    return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) 
    {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

        // check optimality conditions for single source
    private boolean check(Graph G, int s) {

        // check that the distance of s = 0
        if (distTo[s] != 0) {
            System.out.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }

        // check that for each edge v-w dist[w] <= dist[v] + 1
        // provided v is reachable from s
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("distTo[" + v + "] = " + distTo[v]);
                    System.out.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
        // provided v is reachable from s
        for (int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                System.out.println("shortest path edge " + v + "-" + w);
                System.out.println("distTo[" + v + "] = " + distTo[v]);
                System.out.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    private void validateVertex(int v) 
    {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) 
    {
        Graph G = new Graph(7);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 3);
        G.addEdge(1, 4);
        G.addEdge(2, 5);
        G.addEdge(2, 6);

        BFS b = new BFS(G, 7);
        b.bfs(G, 0);
    }
}
