package BOJ1106;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Arrays;

import java.util.StringTokenizer;

/*
 * BOJ1106 : 호텔
 * 어느 도시에 홍보를 하면 Cost 원의 가격으로 Customer의  고객수를 늘릴 수 있다.
 * 최소 C명의 고객을 더 받기 위해 투자해야하는 광고 비용의 최솟값을 구하라.
 * C : 목표 증원 고객 0< <=1000
 * N : 홍보할 수 있는 도시의 수 <=20
 * Cost,Customer : 비용/ 증가고객 <=100
 * 
 * 
 * 
 */
public class Main {
	
	static BufferedReader br;
	static int C,N,cost,customer,ans;
	static StringTokenizer st;
	static int MAX;
	static final int INF = 1_000_001;
	static int[] minCost;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		
		ans = INF;
		

		st = new StringTokenizer(br.readLine());
		C = Integer.parseInt(st.nextToken());
		MAX = C+100; // C보다 100명 초과 가격까지 봐야한다. (최대)
		N = Integer.parseInt(st.nextToken());
		
		
		minCost = new int[MAX];
		Arrays.fill(minCost, INF); //-1로 채우고 뒤집기? 고려해보기
		minCost[0] = 0;
		
		
		for(int cityIndex = 0; cityIndex<N;cityIndex++) {
			st = new StringTokenizer(br.readLine());
			cost = Integer.parseInt(st.nextToken());
			customer =  Integer.parseInt(st.nextToken());
			
			for(int customerCnt = customer; customerCnt<MAX;customerCnt++) {
				int prev = minCost[customerCnt-customer];
				if(prev+cost< minCost[customerCnt]) minCost[customerCnt] =prev+cost;					
			}
			
		}
		
		//최솟값 찾기
		for(int cnt = C; cnt <MAX; cnt++) {
			if(minCost[cnt] < ans) ans = minCost[cnt];
		}
		
		
		System.out.println(ans);
	}
}
