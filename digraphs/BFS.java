package digraphs;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS 
{
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    private int[] distTo;

    public BFS(Digraph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
	    distTo = new int[G.V()];
	    validateVertex(s);
	    this.s  = s;
	    bfs(G);
    }
    
    private void bfs(Digraph G)
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

    private void validateVertex(int v) 
    {
        int V = marked.length;
        
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) 
    {
        Digraph G = new Digraph(7);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 3);
        G.addEdge(1, 4);
        G.addEdge(2, 5);
        G.addEdge(2, 6);

        BFS b = new BFS(G, 0);
        b.bfs(G);

        for (int x : b.pathTo(6))
        {
            System.out.print(x + " ");
        }
    }
}
