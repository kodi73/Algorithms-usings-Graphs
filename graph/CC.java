package graph;
import java.util.Scanner;
import java.util.Stack;

public class CC 
{
    private boolean[] marked;
    private int[] id;
    private int count;

    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        count = 0;

        for (int v = 0; v < G.V(); v++)
        {
            if (!marked[v])
            {
                dfs(G, v);
                count++;
            }
        }
    }

    private int count()
    {
        return count;
    }

    private int id(int v)
    {
        validateVertex(v);
        return id[v];
    }

    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        id[v] = count;
        
        for (int w : G.adj(v))
        {
            if (!marked[w])
            {
                dfs(G, w);
            }
        }
    }

    private void validateVertex(int v)
    {
        int V = marked.length;
        if (v < 0 || v >= V)
        {
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V-1));
        }
    }
}