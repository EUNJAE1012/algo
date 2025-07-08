package BOJ10942;

import java.util.*;
import java.io.*;

/*
 * BOJ 10942 : 팰린드롬?
 * N개의 수를 칠판에 적는다.
 * M번 질문한다.
 * S to E 가 팰린드롬인가??
 * 정답을 구하라. Yes =1 No = 0
 * N<2000, M < 1000000, 
 * 
 * Stack 사용 -> 메모리초과
 * 2차원 DP 사용
 * dp[a][b] = a->b 까지 팰린드롬임?을 나타내는 boolean
 * 
 * 1. b=a 인 경우 항상 true [1], [2] ,[3]
 * 2. b=a+1 인 경우 n[a] == n[b] 이면 true [1,1],[2,2] 
 * 3. b=a+@ 인 경우 n[a] == n[b] 이고 dp[a+1][b-1] 이면 true [2,3,2]  [1,2,2,1]  
 * 
 * 
 */

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int[] board;
	static int N,M;
	static boolean[][] dp;
	
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		board = new int[N+1];
		sb = new StringBuilder();
		dp = new boolean[N+1][N+1];
		st = new StringTokenizer(br.readLine());
		
		for(int idx = 1 ; idx <= N; idx++) {
			board[idx] = Integer.parseInt(st.nextToken());
		}
		M = Integer.parseInt(br.readLine());
		
		for(int len = 1; len<=N;len++) {
			for(int start = 1;start+len-1<=N;start++) {
				int end = start+len-1;
				if(len ==1) { //1. 
					dp[start][end] = true;
					continue;
				}
				if(len ==2) { //2.
					dp[start][end] = (board[start] == board[end]);
					continue;
				}
				//3글자 이상
				dp[start][end] = (board[start] == board[end] && dp[start+1][end-1]);
			}
		}
		
		
		for(int q = 0; q<M ; q++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());

			if(dp[start][end]) {
				sb.append(1);
			}
			else {
				sb.append(0);
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
