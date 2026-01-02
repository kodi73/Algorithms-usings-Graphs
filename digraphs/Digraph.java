package digraphs;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class Digraph
{
	private final int V;
	private LinkedList<Integer>[] adj;
	private int E;

	@SuppressWarnings("unchecked")
	public Digraph(int V)
	{
		if (V < 0)
		{
			throw new IllegalArgumentException("Number of vertices must be non-negative");
		}
		this.V = V;
		adj = new LinkedList[V];
		this.E = 0;

		for (int v = 0; v < V; v++)
		{
			adj[v] = new LinkedList<>();
		}
	}

	@SuppressWarnings("unchecked")
	public Digraph(Scanner in)
	{
		if (in == null)
		{
			throw new IllegalArgumentException("argument is null");
		}
		this.V = in.nextInt();
		if (V < 0)
		{
			throw new IllegalArgumentException("numbero of vertices in a Graph must be non-negative.");
		}
		adj = new LinkedList[V];
		
		for (int v = 0; v < V; v++)
		{
			adj[v] = new LinkedList<>();
		}

		//this.E = in.nextInt();
		int numberOfEdges = in.nextInt();
		this.E = 0;

		if (numberOfEdges < 0)
		{
			throw new IllegalArgumentException("number of edges must be non-negative");
		}

		for (int i = 0; i < numberOfEdges; i++)
		{
			int v = in.nextInt();
			int w = in.nextInt();
			validateVertex(v);
			validateVertex(w);
			addEdge(v, w);
		}
	}

	@SuppressWarnings("unchecked")
	public Digraph(Digraph G)
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
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
		}
	}

	public Iterable<Integer> adj(int v)
	{
		validateVertex(v);
		return adj[v];
	}

	public int degree(int v) // outdegree Not indegree
	{
		validateVertex(v);
		{
			return adj[v].size();
		}
	}

	// TODO: Implement reverse method
	public Digraph reverse() {
    	Digraph R = new Digraph(V);

    	for (int v = 0; v < V; v++) {
    		for (int w : adj[v]) {
            	R.addEdge(w, v);
        	}
    	}

    	return R;
	}

	// TODOL Implement indegree method

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(V + "vertices, " + E + " edges\n");
		for (int v = 0; v < V; v++)
		{
			sb.append(v + ": ");
			for (int w : adj[v])
			{
				sb.append(w + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
        // Test using the vertex-count constructor
        Digraph graph = new Digraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);
        System.out.println("Graph created using vertex-count constructor:");
        System.out.println(graph);

        // Test using the Scanner constructor
        String inputData = "5\n6\n0 1\n0 2\n1 2\n2 3\n3 4\n4 0\n";
        Scanner scanner = new Scanner(inputData);
        Digraph graphFromScanner = new Digraph(scanner);
        System.out.println("Graph created using Scanner constructor:");
        System.out.println(graphFromScanner);

        // Test using the copy constructor
        Digraph copiedGraph = new Digraph(graph);
        System.out.println("Graph created using copy constructor:");
        System.out.println(copiedGraph);
    }
}
