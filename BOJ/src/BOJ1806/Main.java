package BOJ1806;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 10000이하의 자연수로 이루어진 N짜리 수열
 * 합이 S이상이 되는 연속된 부분, 그 부분들 중 가장 짧은 것의 길이를 구하라
 * 
 * 누적합이 S가 이상인  구간들 중 가장 짧은 것
 * 
 * 투포인터?
 * 
 * 1.l,r 포인터 모두 가장 왼쪽에서 출발
 * 2. while l<r<N
 *  2.1 if sum < S ? 
 *   2.1.1 r포인터를 우측으로 1칸
 *  2.2 else 
 *   2.2.1 최소 길이 갱신
 *   2.2.2 l포인터를 우측으로 1칸
 */

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int l,r,N,S,minLength;
	static int sum; //10만 x 1만 = 10억
	static int[] arr,cumulateSum;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		l = 0;
		r = 1;
		minLength = N+1;
		arr = new int[N];
		cumulateSum = new int[N+1];
		
		
		st = new StringTokenizer(br.readLine());
		for(int idx = 0; idx< N; idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
		}
		
		//누적합
		cumulateSum[0] = 0;

		for(int idx = 1; idx<=N;idx++) {
			cumulateSum[idx] = arr[idx-1] + cumulateSum[idx-1];
		}
		

		while(l<r && r<=N) {
			sum = cumulateSum[r] - cumulateSum[l];
//			System.out.println("  l : "+l+ "  r : "+r + " 구간합 : "+ sum);
			if(sum<S) {
				r++;
			}
			else {
				
				minLength= Math.min(minLength, r-l);
				l++;
			}
		}
		if(minLength == N+1) {
			System.out.println(0);
			return;
		}
		
		System.out.println(minLength);
	}
}
