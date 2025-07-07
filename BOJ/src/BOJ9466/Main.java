package BOJ9466;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * BOJ 9466 : 텀 프로젝트
 * 
 * 프로젝트 팀원 수 에는 제한이 없다. 전원이 한팀이여도 된다.
 * 각자 한명씩 함께하고 싶은 학생을 선택한다.
 * 혼자 하고 싶으면 자기 자신을 선택해도 된다.
 * 학생 r명이 있고 s1->s2->s3->...->sr->s1 인 경우만 한 팀만 존재한다.
 * 짝사랑은 팀이 되지 않는다.
 * 삼+각관계 는 팀이 된다...
 * n<=10만
 * 주어진 선택의 결과를 보고 팀이 없는 학생의 수를 구하라. 
 * 
 * 풀이
 * union -find 로 사이클을 형성하지 못한 학생 수를 구한다.  -> 시간초과남
 * 
 * 두번째 풀이
 * DFS -> 방문처리 + 방문중처리 로 사이클 감지  
 * 
 * 
 */
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int n; //학생 수
	static int[] visited;//0미방문 1방문중 2방문완료
	static int[] want;
	static int cnt;
	
	static void dfs(int cur) {
		visited[cur] = 1;
		int next = want[cur];
		if(visited[next]==0) { //아직 안가봤으면 더 가본다.
			dfs(next);
		}
		if(visited[next]==1) { //방문중이다? 사이클이다?
			for(int v = next; v!=cur; v= want[v]) {
				cnt--;
			}
			cnt--;
		}
		visited[cur] = 2; //방문 종료
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc<=T ; tc++) {
			n = Integer.parseInt(br.readLine());
			cnt = n;
			
			visited = new int[n+1];
			want = new int[n+1];
			
			st = new StringTokenizer(br.readLine());
			for(int idx = 1; idx<=n;idx++) {
				want[idx] = Integer.parseInt(st.nextToken());
				
			}
			
			for(int idx = 1; idx<=n;idx++) 
			{
				if(visited[idx]== 0) dfs(idx);
			}
			
			System.out.println(cnt);
		}

	}
	
}
