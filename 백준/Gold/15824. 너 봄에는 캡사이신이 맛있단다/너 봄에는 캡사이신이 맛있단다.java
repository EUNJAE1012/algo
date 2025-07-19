/*
 * BOJ 15824 너 봄에는 캡사이신이 맛있단다
 * 메뉴가 N개 있는 식당이 있다.
 * 각 메뉴는 스코빌지수를 갖는다. 
 * 여러 메뉴를 시켰을때, 가장 작은 스코빌 지수와 가장 큰 스코빌 지수의 차이만큼 주헌고통지수를 얻는다.
 * 모든 조합을 먹었을때, 누적된 주헌 고통지수의 합은?
 * 
 * 풀이:
 * 메뉴를 1개만 시키면, 고통 지수는 0이다.
 * 즉 메뉴를 2개 이상 고른 경우를 고려한다.
 * 모든 조합을 확인하는 건 시간상 문제가 있다. N<=300000
 * 그렇다면..
 * 각 음식이 최대가 되는 경우와, 최소가 되는 경우의 수를 구한다.
 * 
 * 1. 메뉴판을 정렬한다.
 * 2. i번째 음식은 , 2^(i)번 최대값이 된다. (1개만 고르는 경우는 퉁쳐진다.)
 * 3. i번째 음식은 , 2^(N-i-1)번 최소값이 된다.
 * 4. sum += 2^(i-1)-2^(N-i-1)
 */

import java.io.*;
import java.util.*;

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static final int MOD = 1_000_000_007;
	static int N,left,right;
	static long sum;
	static int[] arr;
	static long[] pow2;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		
		st = new StringTokenizer(br.readLine());
		for(int idx = 0; idx<N;idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		// 미리 2^i 값을 계산해 둔다 mod 포함
        pow2 = new long[N];
        pow2[0] = 1;
        for (int i = 1; i < N; i++) {
            pow2[i] = (pow2[i - 1] << 1) % MOD;
        }

		
		for(int idx=0;idx<N;idx++) {
			long max = (arr[idx] * pow2[idx])% MOD;
			long min = (arr[idx] * pow2[N-idx-1]) % MOD;
			sum = (sum + max - min+MOD)%MOD;//음수보정 필요함
		}

		
		
		System.out.println(sum);

	}
}
