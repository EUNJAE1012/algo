

import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static int N, M, A, B;
    static List<List<Road>> graph;
    
    static class Road implements Comparable<Road> {
        int to;
        long weight;

        Road(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Road o) {
            return Long.compare(this.weight, o.weight);
        }
    }

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            long weight = Long.parseLong(st.nextToken());  // long으로 파싱
            
            graph.get(from).add(new Road(to, weight));
            graph.get(to).add(new Road(from, weight));
        }
        
        long[] distA = dijkstra(A);
        long[] distB = dijkstra(B);
        long shortest = distA[B];
        
        List<Integer> result = new ArrayList<>();
        for(int city = 1; city <= N; city++) {
            if(distA[city] != Long.MAX_VALUE && 
               distB[city] != Long.MAX_VALUE && 
               distA[city] + distB[city] == shortest) {
                result.add(city);
            }
        }
        
        Collections.sort(result);
        System.out.println(result.size());
        for(int i = 0; i < result.size(); i++) {
            if(i > 0) System.out.print(" ");
            System.out.print(result.get(i));
        }
        System.out.println();
    }

    static long[] dijkstra(int start) {
        PriorityQueue<Road> pq = new PriorityQueue<>();
        long[] dist = new long[N+1];
        boolean[] visited = new boolean[N+1];
        
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[start] = 0;
        pq.offer(new Road(start, 0));

        while(!pq.isEmpty()) {
            Road now = pq.poll();
            
            if(visited[now.to]) continue;
            visited[now.to] = true;
            
            for(Road nxt : graph.get(now.to)) {
                long cost = dist[now.to] + nxt.weight;
                if(cost < dist[nxt.to]) {
                    dist[nxt.to] = cost;
                    pq.offer(new Road(nxt.to, cost));
                }
            }
        }
        return dist;
    }
}