
import java.io.*;
import java.util.*;

/*
 * BOJ 20040 : 사이클 게임
 * 두명의 플레이어가 게임한다. 선플레이어가 홀수번째턴에, 후플레이어가 짝수번째턴에
 * 0~ n-1 개의 점이 평면상에 있다.
 * 어느 3점도 일직선에 있찌 않음
 * 매 차례 플레이어가 두점을 선택해서 선분을 긋는다. (교차가능)
 * 사이클이 완성되는 순간 게임이 종료된다.
 * 사이클 C는 플레이어가 그린 선분들의 부분집합, 모든선분을 한번씩만 지나서 출발점으로 돌아올 수 있다.
 * 
 * 최소신장트리 x 그냥 고리형 사이클임
 * 유니온파인드로 하나씩 확장, 출발점으로 연결되면?
 */

public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int[] parent;
	static int n; //점의 수
	static int m; //진행된 턴의 수
	
	static void make() {
		parent = new int[n+1];
		for(int i = 1; i<=n;i++) {
			parent[i] = i;
		}	
	}
	
	static int find(int a) {
		if (a==parent[a]) return a;
		return parent[a] = find(parent[a]);
	}
	
	static boolean isCycle(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		if(rootA==rootB) return true; //사이클이면 true
		parent[rootB] = rootA;
		return false;
	}
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		make();
		for(int game = 1; game<=m;game++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			if(isCycle(from, to)) {
				System.out.println(game);
				return;
			}

		}
		System.out.println(0);
	}
	
}
