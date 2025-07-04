package BOJ1365;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 전깃줄이 꼬여있다.
 * 최소로 잘라서, 꼬이지 않게 해라
 * 풀이
 * 최장증가 부분수열을 만들어야 한다.
 * dp[idx] = idx를 시작으로하는 최장 증가 수열 의 길이
 * dp[k] = x : k길이 증가 수열에서 k 자리에 올 수 있는 가장 작은 값 x
 * 이분탐색으로 각 요소의 자리를 찾는다.
 * 
 */
public class BOJ_1365_꼬인전깃줄_김은재 {

	static BufferedReader br;
	static StringTokenizer st;
	static int poleCnt;
	static int[] poles;
	static int[] dp;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		poleCnt = Integer.parseInt(br.readLine());
		poles = new int[poleCnt];
		dp = new int[poleCnt];
		st = new StringTokenizer(br.readLine());
		for(int idx = 0; idx<poleCnt;idx++) {
			poles[idx] = Integer.parseInt(st.nextToken());
		}
		

		int len = 0;
        for (int x : poles) {//배열에서 x가 들어가는 위치는
            int idx = Arrays.binarySearch(dp, 0, len, x);
            if (idx < 0) idx = -(idx + 1); // 삽입 위치
            dp[idx] = x;
            if (idx == len) len++; //끝에오는 경우= 가장 큰 숫자인경우 끝에 삽입
        }


        System.out.println(poleCnt-len);
    }
}
