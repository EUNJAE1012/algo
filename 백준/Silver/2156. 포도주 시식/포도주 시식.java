/*
 * BOJ 2156 포도주 시식
 * 포도주가 일렬로 놓여있다. 
 * 1. 포도주 잔을 선택하면, 원샷하고 제자리에 둔다
 * 2. 연속을 놓인 3잔을 모두 마실 수는 없다.
 * 각 포도주 잔에 들어있는 포도주의 양이 주어질 때, 가장 많이 마시는 방법은?
 * 최대로 마실 수 있는 포도주의 양을 구하라
 * 
 * 풀이 전형적인 다이나믹프로그래밍
 * wine[n] : n번째 잔에 들어있는 포도주의 양
 * dp[n][i] : n번째 잔까지 봤을때 마실 수 있는 최대 양 
 * i 연속 횟수 최대 2 
 * 즉 i=0 이번것을 선택하지 않음 
 *   i=1 n-1 미선택, n 선택
 *   i=2 n-1 선택, n 선택  = n-2미선택 
                
 * dp[n][0] = max(dp[n-1][0],dp[n-1][1],dp[n-1][2])
 * dp[n][1] = max(dp[n-1][0])+wine[n]
 * dp[n][2] = max(dp[n-1][1])+wine[n]
 * 1번째잔,2번째잔, 3번째잔까지 선택했을때 최대양
 * 
 * 
 * 
 */

import java.io.*;

public class Main {
	static BufferedReader br;
	static int[] wine;
	static int[][] dp;
	static int N;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		wine = new int[N];
		dp = new int[N][3];
		for(int idx=0;idx<N;idx++) {
			wine[idx] = Integer.parseInt(br.readLine());
			
		}
		dp[0][0] = 0;
		dp[0][1] = wine[0];
		dp[0][2] = 0;
		for(int idx=1; idx<N;idx++) {
			dp[idx][0] = Math.max(Math.max(dp[idx-1][0], dp[idx-1][1]),dp[idx-1][2]);
			dp[idx][1] = wine[idx] + dp[idx-1][0];
			dp[idx][2] = wine[idx] + dp[idx-1][1];
		}
		
		int ans = Math.max(Math.max(dp[N-1][0], dp[N-1][1]),dp[N-1][2]);
		
		System.out.println(ans);
		
		
	}
}