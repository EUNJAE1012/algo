package BOJ9252;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/*
 * BOJ9252 : LCS 2
 *  최대 1000자의 문자열 2개가 주어진다. 
 *  양쪽 모두에 해당하는 부분 문자 열중 가장 긴 것을 찾아라.(꼭 연속 문자열은 아님)
 * 
 * 2차원 DP
 * 수열 A, 수열 B가 주어진다.
 * DP[i][j] 는 A[i], B[j] 까지 확인했을때의 LCS의 길이 (정확한 값은 따로 변수에 저장)
 * 
 * 즉 
 * A : ACAYKP
 * B : CAPCAK
 * 
 * DP = 
 *    A C A Y K P
 * C  0 1 1 1 1 1
 * A  0 1 2 2 2 2
 * P  0 1 2 2 2 3
 * C  0 2 2 2 2 3
 * A  0 2 3 3 3 3
 * K  0 2 3 3 4 4 
 * string = ACAK
 * 
 * A[i] = B[j] ? DP[i][j] = DP[i-1][j-1]+1
 * else DP[i][j] = Math.max(dp[i-1][j] , dp[i][j-1])
 */
public class Main {

    static String A, B;
    static int[][] dp;          // dp[i][j] = LCS length of B[0..i-1], A[0..j-1]
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        A = br.readLine();           // 열(가로)  ──> j
        B = br.readLine();           // 행(세로)  │   i

        int n = A.length();          // A의 길이
        int m = B.length();          // B의 길이
        dp = new int[m + 1][n + 1];  // 0번째 행·열 = 빈 문자열과의 LCS

        /* 1. 테이블 채우기 */
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (B.charAt(i - 1) == A.charAt(j - 1)) {   // 문자가 같다면 ↖ 대각선 +1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {                                    // 아니면 ↑,← 중 큰 값
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        /* 2. 길이 출력 */
        System.out.println(dp[m][n]);

        /* 3. LCS 문자열 복원(역추적) */
        if (dp[m][n] > 0) {
            backtrack(m, n);
            System.out.println(sb);
        }
    }

    /** (i,j) 위치에서 LCS 경로를 역추적해 sb에 추가 */
    static void backtrack(int i, int j) {
        if (i == 0 || j == 0) return;                       // 빈 문자열에 도달
        if (B.charAt(i - 1) == A.charAt(j - 1)) {           // 같은 문자면 ↖로 이동
            backtrack(i - 1, j - 1);
            sb.append(B.charAt(i - 1));                     // 또는 A.charAt(j - 1)
        } else {                                            // 값이 더 큰 쪽으로 이동
            if (dp[i - 1][j] >= dp[i][j - 1])
                backtrack(i - 1, j);
            else
                backtrack(i, j - 1);
        }
    }
}
