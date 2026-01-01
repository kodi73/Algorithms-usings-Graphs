package graph;

import java.util.*;

public class DiameterAndCentreOfGraph {
    private int s; // starting point
    private boolean[] marked; // prevents revisiting of vertices
    private int[] edgeTo; // stores edge details in bfs
    private int[] distTo; // stores distance 

    DiameterAndCentreOfGraph(Graph G) {
        this.s = 0;
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.distTo = new int[G.V()];
    }

    private int[] bfs(Graph G, int s) {
        Arrays.fill(marked, false);
        Arrays.fill(edgeTo, -1);
        Arrays.fill(distTo, -1);
        Queue<Integer> q = new LinkedList<>();
        // bfs starts here
        q.add(s);
        marked[s] = true;
        distTo[s] = 0;

        while (!q.isEmpty()) {
            int v = q.poll();

            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.offer(w);
                }
            }
        }

        int maxDistance = 0; 
        int nodeMaxDistance = 0;

        for (int i = 0; i < marked.length; i++) {
            if (distTo[i] > maxDistance) {
                maxDistance = distTo[i];
                nodeMaxDistance = i;
            }
        }

        return new int[] {nodeMaxDistance, maxDistance};
    }

    private int[] diameter(Graph G) {
        int[] distanceFromStoEnd = bfs(G, s);
        int[] distanceFromEndToEnd = bfs(G, distanceFromStoEnd[0]);

        return new int[] {distanceFromStoEnd[0], distanceFromEndToEnd[0], distanceFromEndToEnd[1]};
    }

    private int[] center(Graph G, int[] diameter) {
        int start = diameter[0];
        int end = diameter[1];
        List<Integer> path = new ArrayList<>();
        
        for (int x = end; x != -1; x = edgeTo[x]) {
            path.add(x);

            if (x == start) {
                break;
            }
        }

        Collections.reverse(path);
        int n = path.size();

        if (n % 2 == 0) {
            return new int[] {path.get(n / 2 - 1), path.get(n / 2)};
        }
        else {
            return new int[] {path.get(n / 2)};
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(10);
        G.addEdge(0, 1); 
        G.addEdge(1, 2); 
        G.addEdge(2, 3); 
        G.addEdge(2, 9); 
        G.addEdge(2, 4); 
        G.addEdge(4, 5); 
        G.addEdge(1, 6); 
        G.addEdge(6, 7); 
        G.addEdge(6, 8);

        DiameterAndCentreOfGraph dc = new DiameterAndCentreOfGraph(G);
        int[] diameter = dc.diameter(G);
        int[] center = dc.center(G, diameter);

        System.out.println("Diameter is " + diameter[2] + " from vertex " + diameter[0] + " to vertex " + diameter[1]);
        if (center.length == 2) {
            System.out.println("Center is between vertices " + center[0] + " and " + center[1]);
        }
        else {
            System.out.println("Center is at vertex " + center[0]);
        }
    }   
}
