import java.util.*;
import java.io.*;
/*BOJ 15773: N과M 9
 * N개의 자연수중 M개를 고른다. 중복 x
 * 조합..? dfs..?
 * 
 */
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int N,M;
	static int[] arr;
	static boolean[] visited;
	static int selected[];
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int idx= 0; idx<N; idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		visited = new boolean[N];
		selected = new int[M];
		
		dfs(0);
		
		System.out.println(sb.toString());
	}
	
	static void dfs(int depth) {
		if(depth == M) {
			for(int selectedIdx =0; selectedIdx<M; selectedIdx++) {
				sb.append(selected[selectedIdx]).append(" ");
			}
			sb.append("\n");
			return;
		}
		int prev = -1;
		
		for(int idx = 0; idx<N ;idx++) {
			if(visited[idx]) continue;
			if(arr[idx]==prev) continue;
			visited[idx]= true;
			selected[depth] = arr[idx];
			prev = arr[idx];
			dfs(depth+1);
			visited[idx]= false;
			}
	}
}
