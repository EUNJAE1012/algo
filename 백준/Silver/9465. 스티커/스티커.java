
/*
 * BOJ 9465 스티커 
 * 스티커 2n개 가있다. 2행 n열로 배치했다.
 * 스티커를 하나때면, 상하좌우의 스티커가 못 쓰게된다.
 * 어쩔 수 없이 스티커에 점수를 매기고, 최대의 점수가 되도록 쓰려고한다.
 * 점수표가 주어질 때, 점수의 최대 값은?
 * 
 * 풀이
 * 이번 열을 선택할 때 최대 값 = 이번칸 + 저번반대칸 or 이번전칸(붙인셈치기)
 *dp[col][currentChoice] = Math.max(
 *(stickers[currentChoice][col] +dp[col-1][currentChoice^1]) 
 *						,dp[col-1][currentChoice])
 */
import java.io.*;
import java.util.*;

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N;
	static int[][] stickers;
	static int[][] dp;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 0; tc<T;tc++) {
			N = Integer.parseInt(br.readLine());
			stickers = new int[2][N];
			dp = new int[N][2];
			for(int row = 0; row<2;row++) {
				st = new StringTokenizer(br.readLine());
				for(int col=0; col<N;col++) {
					stickers[row][col] = Integer.parseInt(st.nextToken());
				}
			}
			
			dp[0][0] = stickers[0][0];
			dp[0][1] = stickers[1][0];

			for(int col = 1 ; col<N;col++) {
				for(int currentChoice = 0; currentChoice<2; currentChoice++) {
					dp[col][currentChoice] = Math.max((stickers[currentChoice][col] +dp[col-1][currentChoice^1]) 
						,dp[col-1][currentChoice]);
				}

			}
			System.out.println(Math.max(dp[N-1][0], dp[N-1][1]));
		}
	}
}
