/*
    destination ->  강철부대 최단거리 = 강철부대 -> destination 최단거리
    destination에서 다익스트라 -> 모든 점으로부터의 거리 구함 
*/
import java.util.*;
class Solution {
    
    static ArrayDeque<Integer> queue;
    static long[] dist; //거리배열
    static List<List<Integer>> graph; //a to B 그래프
    public long[] solution(int n, int[][] roads, int[] sources, int destination) {
        dist = new long[n+1];
        Arrays.fill(dist,-1);
        graph = new ArrayList<>();
        for(int i = 0; i<=n;i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] road : roads){
            int from = road[0];
            int to = road[1];
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        dijkstra(destination);
        
        
        long[] answer = new long[sources.length];
        
        int idx =0;
        for(int start : sources){
            answer[idx++] = dist[start];
        }
        
        return answer;
    }
    
    static void dijkstra(int start){
        queue = new ArrayDeque<>();
        queue.add(start);
        dist[start] = 0;
        while(!queue.isEmpty()){
            int cur = queue.poll();
            for(int nxt : graph.get(cur)){
                if(dist[nxt]==-1||dist[nxt]>dist[cur]+1){
                    dist[nxt] = dist[cur]+1;
                   queue.add(nxt); 
                }
            }
        }
    }
}