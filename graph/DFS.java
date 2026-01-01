package graph;
import java.util.Scanner;
import java.util.Stack;

public class DFS 
{
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    
    public DFS(Graph G, int s)
    {
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    public void dfs(Graph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v)) 
        {
            if (!marked[w])
            {
		        edgeTo[w] = v;
                dfs(G, w);
            }    
        }
    }
    
    private void validateVertex(int v)
    {
        int V = marked.length;
        if (v < 0 && v >= V)
        {
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V-1));
        }
    }
    
    public boolean hasPathTo(int v)
    {
        validateVertex(v);
        return marked[v];
    }
    
    public Iterable<Integer> pathTo(int v)
    {
        validateVertex(v);
        
        if (!hasPathTo(v)) 
        {
            return null;
        }
        
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
        {
            path.push(x);
        }
        path.push(s);
        return path;
    }
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(args[0]);
        Graph G = new Graph(sc);
        
        int s = Integer.parseInt(args[1]);
        DFS dfs = new DFS(G, s);
        
        for (int v = 0; v < G.V(); v++)
        {
            if (dfs.hasPathTo(v))
            {
                System.out.print(s + " to " + v);
                for (int x : dfs.pathTo(v))
                {
                    if (x == s)
                    {
                        System.out.print(x);
                    }
                    else
                    {
                        System.out.print("-" + x);
                    }
                }
              System.out.println();
            }
            else
            {
                System.out.print(s + " to " + v + ": not connected\n");
            }
        }
    }
}
