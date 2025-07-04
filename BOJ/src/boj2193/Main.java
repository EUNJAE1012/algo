package boj2193;

import java.util.Arrays;
import java.util.Scanner;

/*
 * 이친수 
 * 1. 1로시작
 * 2. 1이 연속으로 나오지 않음
 * N자리 이친수의 갯수를 구하라.
 * 
 * 1 자리 이친수 1개  : 1
 * 2 자리 이친수 1개  : 10
 * 3 자리 이친수 2개  : 101 100
 * 4 자리 이친수 3개  : 1010 1000 1001
 * 5 자리 이친수 5개  : 10101 10100 10001 10000 10010
 * N자리의 이친수는 N-1번째의 이친 수 중 0으로 끝나는 것의 수x2 + 1로 끝나는 것의 수
 * 
 * 
 */
public class Main {
	static long dp[][];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		if(N<=2) {
			System.out.println(1);
			return;
		}
		dp = new long[N+1][2];
		dp[1][0] = 0;
		dp[1][1] = 1;
		dp[2][1] = 0;
		dp[2][0] = 1;
		
		for(int idx = 3; idx<=N; idx++) {
			dp[idx][0] = dp[idx-1][1] + dp[idx-1][0];
			dp[idx][1] = dp[idx-1][0];
//			System.out.println(Arrays.toString(dp[idx]));
		}
		
		
		
		System.out.println(dp[N][0]+dp[N][1]);
		
		
	}
}
