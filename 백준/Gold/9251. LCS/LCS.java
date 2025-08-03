
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * BOJ 9251 LCS
 * 두 문자열에서 최장 공통 부분 수열을 구하라
 * 
 * dp[i][j] : 첫문자열을 i, 두번째문자열을 j 까지 봤을 떄 LCS의 길이
 * 
 * for i:N 
 *   for j:N
 *     if string1[i] ==string2[j] 
 *        dp[i][j] = dp[i-1][j-1] +1;
 * 
 * 
 */

public class Main {
	
	static char[] string1;
	static char[] string2;
	static int[][] dp;
	static BufferedReader br;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		string1 = br.readLine().toCharArray();
		string2 = br.readLine().toCharArray();
		int N = string1.length;
		int N2 = string2.length;
		dp = new int[N+1][N2+1];
		
		for(int i = 1; i<=N;i++) {
			for(int j =1; j<=N2;j++) {
				if(string1[i-1]==string2[j-1]) {
					dp[i][j] = dp[i-1][j-1]+1;
				}
				else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
				
			}
		}
		
		System.out.println(dp[N][N2]);
	}
}
