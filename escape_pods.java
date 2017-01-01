package com.google.challenges; 
import java.util.*;

public class Answer {
  
    /*
    Find maximum flow of source s and sink t
    using Dinic's algorithm
    complexity: O(V^2 * E)
    
    note: please read Fork-Fullkerson algorithm in O(E * maxflow) 
    for a more straightforward concept of iteration in finding the augmenting path
    https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm
    */
    static class DinicAlgorithm {      
        static class Edge {
            // standard static Edge class for Graph class
            int t, rev, cap, flow;

            public Edge(int t, int rev, int cap) {
                this.t = t;
                this.rev = rev;
                this.cap = cap;
            }
        }     
      
        int nodes;
        List<Edge>[] graph;
      
        public DinicAlgorithm(int nodes) {
            this.nodes = nodes;
            graph = new List[nodes];
            for (int i = 0; i < nodes; i++)
                graph[i] = new ArrayList<Edge>();
        }

        public void addEdge(int u, int v, int weight) {
            graph[u].add(new Edge(v, graph[v].size(), weight));
            graph[v].add(new Edge(u, graph[u].size() - 1, 0));
        }

        /*    
        <summary>    
        1. Build a level graph
        2. Find an augmenting path from source to sink
        3. Find the bottleneck on augmenting path
        4. Find the augmenting path from bottleneck to sink
        5. Repeat step 3 and step 4 to construct a blocking flow until no augmenting path is found
        </summary>
        further explanation: http://emmanueljohn.me/2015/07/05/dinics_algorithm/
        other source: https://sites.google.com/site/indy256/algo/dinic_flow (mine is better)
        */
      
        // finding augmenting path from s to t
        boolean dinicBfs(int s, int t, int[] dist) {
            Arrays.fill(dist, -1);          
            dist[s] = 0;
            // initialize a queue, could be done with ArrayList
            int[] queue = new int[nodes];
            int size = 0;
            queue[size++] = s;
          
            for (int i = 0; i < size; i++) {
                int u = queue[i];
              
                for (Edge e : graph[u]) {
                    // if water can still flow through capacity of pipeline
                    if (dist[e.t] < 0 && e.flow < e.cap) {
                        dist[e.t] = dist[u] + 1;
                        queue[size++] = e.t;
                    }
                }
            }
          
            return dist[t] >= 0;
        }

        
        int dinicDfs(int[] ptr, int[] dist, int v, int u, int flow) {
            // terminal condition
            if (u == v) return flow;
            
            int increment;          
            Edge e;
            for (; ptr[u] < graph[u].size(); ptr[u]++) {
                e = graph[u].get(ptr[u]);
              
                // find the bottleneck (smallest pipeline)
                if (dist[e.t] == dist[u] + 1 && e.flow < e.cap) {
                    // find augmenting path from bottleneck to sink
                    increment = dinicDfs(ptr, dist, v, e.t, Math.min(flow, e.cap - e.flow));
                    
                    if (increment <= 0) continue;
                    // augmenting path is found
                    e.flow += increment;
                    graph[e.t].get(e.rev).flow -= increment;
                    return increment;
                }
            }
            // no path is found
            return 0;
        }

        // find maximum flow in network flow
        public int findMaxFlow(int s, int t) {
            int maxflow = 0;
            int[] dist = new int[nodes];
            
            int[] ptr;
            int increment;
            // if there exists an augmenting path from source to sink
            while (dinicBfs(s, t, dist)) {
                ptr = new int[nodes];
                do {
                    // construct a blocking flow
                    increment = dinicDfs(ptr, dist, t, s, Integer.MAX_VALUE);
                    maxflow += increment;
                }
                // terminate if there is no augmenting path
                while (increment > 0);
            }
            return maxflow;
        }
    }  
  
    public static int answer(int[] entrances, int[] exits, int[][] path) { 
        
        int n = path.length;        
        DinicAlgorithm networkFlow = new DinicAlgorithm(n + 2);
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < n; j++) 
                if (path[i][j] > 0) networkFlow.addEdge(i, j, path[i][j]);
        
        /*
        the problem is defined as multiple sources and sinks maximum flow,
        therefore, having a supersource and a supersink is necessary
        https://en.wikipedia.org/wiki/Maximum_flow_problem
        */
      
        // create edges from supersource to sources with weight equal to max
        for (int i = 0; i < entrances.length; i++)
            networkFlow.addEdge(n, entrances[i], Integer.MAX_VALUE);
    
        // create edges from sinks to supersink with weight equal to max
        for (int i = 0; i < exits.length; i++) 
            networkFlow.addEdge(exits[i], n + 1, Integer.MAX_VALUE);
      
        return networkFlow.findMaxFlow(n, n + 1);
    } 
}