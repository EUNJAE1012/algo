
import java.io.*;
import java.util.*;

/*
 * BOJ 2533 : 사회망 서비스
 * SNS에서 우리는 건너건너 친구다.
 * 새로운 정보를 먼저 받아 들인 사람 =얼리어답터
 * 얼리어답터가 아닌 사람은, 자신의 모든 친구가 얼리어답터가 되면 전염된다.
 * 이 문제에서는 친구 관계가 사이클이 없는, 트리인 경우에만 고려한다.
 * 친구 관계가 주어질때, 모두를 얼리어답터로 만들기 위한 최소 인원 수를 구하라
 * 
 * 풀이 1
 * 내가 아무튼 루트임.
 * 내가 얼리가 아닌데 모두가 얼리가 되려면? 내 child가 모두 얼리여야함
 * 내가 얼리인데? 모두가 얼리가 되려면? 내 child가 얼리거나 아니거나 기왕이면 비용이 더 작은 쪽
 * 을 리프까지 반복
 * 
 * DFS DP
 * DP[i][0] : i번째 노드가 얼리가 아닐때 서브트리를 채울 최소 수
 * DP[i][1] : i번째 노드가 얼리가 일때  서브트리를 채울 최소 수
 * DFS(root)
 * visited[root] = true
 * dp[root][0] = 0;
 * dp[root][1] = 1;
 * for(child : rootChild){
 * if(visited[child] continue;	
 * dfs(child)
 * 	dp[root][0] += dp[child][1]
 *  dp[root][1] += min(dp[child][0],dp[child][1])
 * }
 * ans = min(dp[root][0],dp[root][1]);
 * 
 * 
 * 
 * 
 */
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N;//정점의 수	
	static int dp[][];
	static boolean visited[];
	static List<List<Integer>> graph; //친구 트리 

	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		graph = new ArrayList<>();
		dp = new int[N+1][2];
		visited = new boolean[N+1];
		
		for(int i = 0 ; i<=N; i++) {
			//1 index
			graph.add(new ArrayList<>());
		}
		
		for(int i = 1; i<=N-1;i++) {
			st = new StringTokenizer(br.readLine());
			int friend1 = Integer.parseInt(st.nextToken());
			int friend2 = Integer.parseInt(st.nextToken());
			graph.get(friend1).add(friend2);
			graph.get(friend2).add(friend1);
		}
		dfs(1);
		
		System.out.println(Math.min(dp[1][0], dp[1][1]));
	}
	static void dfs(int n) {
		visited[n] = true;
		dp[n][0] = 0;
		dp[n][1] = 1;
		for(int next : graph.get(n)) {
			if(visited[next]) continue;
			dfs(next);
			dp[n][0] += dp[next][1];
			dp[n][1] += Math.min(dp[next][0], dp[next][1]);

		}

	}
}
