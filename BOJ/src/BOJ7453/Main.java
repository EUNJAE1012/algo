package BOJ7453;

import java.io.*;
import java.util.*;

/*
 * BOJ 7453 : 합이 0인 네 정수
 * 같은 크기의 정수 배열 A B C D 4개가 있다.
 * 각 정수는 2^28의 절대값 이하이며..
 * N 배열의 크기 ~4000
 * 4개의 정수의 합이 0 이 되는 쌍의 개수를 구하라
 * 
 * 풀이
 * A+B vs C+D 로 구분한다.
 * A+B, C+D의 기록(값, 횟수) 어떻게 저장할 것인가?
 * 1. 배열? 2^29 x 4 byte 는 안봐도 메모리초과
 * 2. Map<값, 횟수> => 시간 초과 발생
 * 3. 이분탐색 방식 
 * 3.1 16만 사이즈 배열x2에 각 값 저장 이분탐색으로 각 목표값 upper lower bound 찾기
 * => 통과는 되었으나 11초 넘게 소모 시간 초과 위험

 * 4. 투포인터 방식 
 * 4.1 A+B 16만 사이즈 배열, C+D 16만 사이즈 배열 생성
 * 4.2 각 배열을 정렬
 * 4.3 l 포인터는 AB 배열의 0, r 포인터는 CD 배열의 N*N-1 => l<N*N, r>=0
 * 4.3.1 l 포인터 값 + r 포인터 값 = 0?
 * 4.3.1.1 l의 값이 달라질때 까지 l++ , cnt1++
 * 4.3.1.2 r의 값이 달라질때 까지 r-- , cnt2++
 * 4.3.1.3 ans += cnt1* cnt2
 * 4.3.2 sum > 0 r-- 
 * 4.3.3 sum < 0 l++
 * 
 */
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N; //최대 4000
	static int[][] arrays;// A B C D
	static long[] AB, CD;

	static int ans; 
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		AB = new long[N*N];
		CD = new long[N*N];
		arrays = new int[4][N];

		ans = 0; 
		for(int row = 0; row  < N; row++) {
			st = new StringTokenizer(br.readLine());
			arrays[0][row] = Integer.parseInt(st.nextToken());
			arrays[1][row] = Integer.parseInt(st.nextToken());
			arrays[2][row] = Integer.parseInt(st.nextToken());
			arrays[3][row] = Integer.parseInt(st.nextToken());
		}
		
		int idx = 0;
		for(int idxA = 0; idxA<N;idxA++) {
			int numA = arrays[0][idxA];
			for(int idxB = 0 ; idxB<N;idxB++) {
				int numB = arrays[1][idxB];
				long sum = (long)numA+numB;
				AB[idx++] = sum;
			}
		}
		
		idx = 0;
		for(int idxC = 0; idxC<N;idxC++) {
			int numC = arrays[2][idxC];
			for(int idxD = 0 ; idxD<N;idxD++) {
				int numD = arrays[3][idxD];
				long sum = (long)numC+numD;
				CD[idx++] = sum;
			}
		}
		
		Arrays.sort(AB);
		Arrays.sort(CD);
		
		int l = 0;
		int r = N*N-1;
		while(l<N*N && r>=0) {
			long sum = AB[l]+CD[r];
			if(sum==0) {
				long abVal = AB[l];
				int cnt1 = 0;
				//AB에서 동일값 갯수 세기
				while(l<N*N && AB[l]==abVal) {
					l++;
					cnt1++;
				}
				
				long cdVal = CD[r];
				int cnt2 = 0;
				//CD에서 동일값 갯수 세기
				while(r>=0 && CD[r]==cdVal) {
					r--;
					cnt2++;
				}
				
				ans+= cnt1*cnt2;
				
			}
			if(sum>0)r--;
			if(sum<0)l++;
				
		}
		System.out.println(ans);
	}
		
	
}

