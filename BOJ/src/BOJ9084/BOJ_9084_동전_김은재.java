package BOJ9084;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 동전은 1 5 10 50 100 500 이 있다.
 * 금액이 주어질때
 * 그 금액을 만드는 모든 방법을 세보거라
 * 풀이
 * dp
 * dp 0 = 1 
 * 코인 가치 p : coins ->
 * j = p -> target
 * dp[j] += dp[j-p]   j-p 를 만드는 가지수만큼 더할 수 있음. 숟가락 얹기
 */
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int[] dp;
	static int coinCnt;
	static int[] coinList;
	static int targetPrice;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=T; tc++) {
			coinCnt = Integer.parseInt(br.readLine());
			coinList = new int[coinCnt];
			
			st = new StringTokenizer(br.readLine());
			for(int coinIdx =0; coinIdx<coinCnt;coinIdx++) {
				coinList[coinIdx] = Integer.parseInt(st.nextToken());
			}
			
			targetPrice = Integer.parseInt(br.readLine());
			
			dp = new int[targetPrice+1];
			dp[0] = 1; 
			
            for (int coinIdx = 0; coinIdx < coinCnt; coinIdx++) {
            	int price = coinList[coinIdx];
                for (int j = price; j <= targetPrice; j++) {
                    dp[j] += dp[j - price];
                }
            }

            System.out.println(dp[targetPrice]);
		}
	}
	
	
}
