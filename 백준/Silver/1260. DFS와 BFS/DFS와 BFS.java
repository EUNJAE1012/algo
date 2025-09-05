/*
 * 
 */
import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static List<List<Integer>> graph; 
	static int NodeCnt,EdgeCnt,start;
    static boolean[] visited;
    static StringBuilder dfsResult;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		NodeCnt =Integer.parseInt(st.nextToken());
		EdgeCnt = Integer.parseInt(st.nextToken());
		start = Integer.parseInt(st.nextToken());
		graph = new ArrayList<>();

		for(int i = 0; i<=NodeCnt;i++) {
			graph.add(new ArrayList<>());
		}
		for(int i=0; i<EdgeCnt;i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			graph.get(from).add(to);
			graph.get(to).add(from);
		}
		
		for(int i = 1 ; i<=NodeCnt;i++) {
			Collections.sort(graph.get(i));
		}
		
        visited = new boolean[NodeCnt + 1];
        dfsResult = new StringBuilder();

		dfs(start);
		
		System.out.println(dfsResult.toString());
		
		bfs();
	}
	static void dfs(int node) {
		visited[node] = true;
		dfsResult.append(node).append(" ");

		for(int next : graph.get(node)) {
			if(!visited[next]) {
				dfs(next);
			}
		}
	}
	
	static void bfs() {
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(start);
		boolean[] visited = new boolean[NodeCnt+1];
		visited[start] =true;
		StringBuilder sb = new StringBuilder();
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			sb.append(cur).append(" ");
			for(int nxt : graph.get(cur)) {
				if(!visited[nxt]) {
					visited[nxt] = true;
					queue.add(nxt);
				}
			}
		}
		System.out.println(sb.toString());
		
	}
}
