package BOJ2473;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * BOJ 2473 : 세 용액
 * 1~10억 산성
 * -10억~-1 알칼리성
 * 3가지 용액을 합성하여 중성에 가까운 용액을 만들려고한다.
 * N : 전체 용액의 수 3~5000
 * 
 * 1. 용액을 정렬한다.
 * 2. 투포인터? 트리플 포인터? 방법을 이용한다.
 * 2.1 l, r, m 포인터 
 * 
 * 
 */
class Main{
	
	static BufferedReader br;
	static StringTokenizer st;
	static long minSum;
	static long sum;
	static int N;
	static int l,r;
	static int[] solutions;
	static int[] answer;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		answer= new int[3];
		solutions = new int[N];
		st = new StringTokenizer(br.readLine());
		minSum = Long.MAX_VALUE;
		for(int idx = 0; idx<N;idx++) {
			solutions[idx] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(solutions);
		
		//하나의 값을 고정하고, 나머지 두개로 투포인터 
		for(int fix = 0; fix<N-2; fix++) {
			l = fix + 1;
            r = N - 1;
			int fixNum = solutions[fix];
			while(l<r) {
				int left = solutions[l];
				int right =solutions[r];
//				System.out.println(left + " "+ +fixNum+ " "+right);
				long sum = (long)left+(long)right+(long)fixNum; //형변환을 넣어주지 않으면 오버플로우
				if(Math.abs(minSum)>Math.abs(sum)) {
					int[] temp = {fixNum,left,right};
					Arrays.sort(temp);
					answer[0] = temp[0];
					answer[1] = temp[1];
					answer[2] = temp[2];
					minSum=sum;
				}
				if(sum>0) {
					r--;

				}
				if(sum<0) {
					l++;

				}
				if(sum==0) break;
				
			}
			
			
		}
		
		System.out.println(answer[0]+ " "+ answer[1] + " "+ answer[2]);
	}
}