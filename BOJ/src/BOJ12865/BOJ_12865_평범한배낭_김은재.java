package BOJ12865;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 준서는 군대를 잘 다녀오려고 한다.
 * 잘 다녀오란다고 잘 다녀올 수 있는 곳은 아니지만
 * 
 * N개의 물건은 각각 W무게와 V가치를 갖는다.
 * K만큼 배낭에 넣을 수 있을 때, 최대 가치는?
 * 
 * 풀이
 * 전형적인 0-1 knapsack으로, 부분조합 혹은 dp로 풀 수 있다.
 * 
 * dp[n][w] = k  n번째물건까지 고려했을때, 무게w에서의 최대가치 k 
 * 를 1차원 dp로 압축
 * dp[w] = k
 * for n = 1 -> n = N:
 * 	for w = K -> weight[n]: 
 *     dp[w] = max(dp[w], dp[w-weight[n]]);
 * 
 * 
 */
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int maxWeight,itemCnt;
	static int[] valueOf,weightOf,dp;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		itemCnt = Integer.parseInt(st.nextToken());
		maxWeight = Integer.parseInt(st.nextToken());
		valueOf = new int[itemCnt];
		weightOf =new int[itemCnt];
		dp = new int[maxWeight+1];
		
		for(int idx =0; idx<itemCnt;idx++) {
			st = new StringTokenizer(br.readLine());
			weightOf[idx] = Integer.parseInt(st.nextToken());
			valueOf[idx] = Integer.parseInt(st.nextToken());
		}
		
		for(int idx =0; idx<itemCnt;idx++) {
			for(int weight = maxWeight; weight>=weightOf[idx];weight--) {
				dp[weight] = Math.max(dp[weight], 
						dp[weight-weightOf[idx]]+valueOf[idx]);
			}
		}
		System.out.println(dp[maxWeight]);
	}
}
