package graph;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;

public class Graph 
{
    private final int V; // number of vertices
    private LinkedList<Integer>[] adj; // actual graph
    private int E; // number of Edges

    @SuppressWarnings("unchecked")
    public Graph(int V)
    {
        if (V < 0)
        {
            throw new IllegalArgumentException("Number of vertices must be non-negative.");
        }

        this.V = V;
        adj = new LinkedList[V];
        this.E = 0;

        for (int v = 0; v < V; v++) 
        {
            adj[v] = new LinkedList<>();
        }
    }

    // Build graph from standard input
    @SuppressWarnings("unchecked")
    public Graph(Scanner in)
    {
        if (in == null)
        {
            throw new IllegalArgumentException("argument is null");
        }

        System.out.print("Enter the number of vertices: ");
        this.V = in.nextInt();
        if (V < 0)
        {
            throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
        }
        adj = new LinkedList[V];

        for (int v = 0; v < V; v++)
        {
            adj[v] = new LinkedList<>();
        }
        System.out.print("\nEnter the number of edges: ");
        this.E = in.nextInt();
        if (E < 0) 
        {
            throw new IllegalArgumentException("number of edges must be non-negative");
        }

        System.out.println("\nEnter the edges of the graph:");

        for (int i = 0; i < E; i++)
        {
            int v = in.nextInt();
            int w = in.nextInt();
            addEdge(v, w);
        }
    }

    // Copy Constructor
    @SuppressWarnings("unchecked")
    public Graph(Graph G)
    {
        this.V = G.V();
        this.E = G.E();

        if (V < 0)
        {
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        }

        adj = new LinkedList[V];

        for (int v = 0; v < V; v++)
        {
            adj[v] = new LinkedList<>();
        }

        for (int v = 0; v < V; v++)
        {
            Stack<Integer> reverse = new Stack<>();
            for (int w : G.adj[v])
            {
                reverse.push(w);
            }
            for (int w : reverse)
            {
                adj[v].add(w);
            }
        }
    }

    public void addEdge(int v, int w)
    {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public int V()
    {
        return V;
    }

    public int E()
    {
        return E;
    }

    private void validateVertex(int v)
    {
        if (v < 0 || v >= V)
        {
            throw new IllegalArgumentException("vertex "+v+" is not between 0 and "+(V-1));
        }
    }

    public Iterable<Integer> adj(int v)
    {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v)
    {
        validateVertex(v);
        {
            return adj[v].size();
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(V+" vertices, "+E+" edges\n");
        for (int v = 0; v < V; v++) 
        {
            sb.append(v+": ");
            for (int w : adj[v])
            {
                sb.append(w+" ");
            }   
            sb.append("\n");
        }
        return sb.toString();
    }

    public void removeEdge(int v, int w) 
    {
        validateVertex(v);
        validateVertex(w);
        
        if (adj[v].remove((Integer) w)) 
        {
            adj[w].remove((Integer) v);
            E--;
        }
    }
}