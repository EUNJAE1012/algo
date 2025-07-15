/*
 * BOJ 2531 : 회전초밥
 * 1.k개 연속으로 먹으면 할인된 가격으로 제공
 * 2.초밥 종류가 쓰인 쿠폰을 발행하고, 1번 행사에 참가하면 이 쿠폰에 적힌 초밥 하나를 무료로 제공 (없으면 만들어줌)
 * 최대한 다양한 초밥을 먹으려고 한다.
 * 
 * 
 * 벨트 위에는 같은 초밥이 여러개 있을지도?
 * 
 * 
 * 풀이
 * 슬라이딩 윈도우인가? dp인가?
 * 
 * 접시수는 최대3만, 초밥의 종류는 최대 3000개, 초밥연속수 3000개, 쿠폰 번호 c
 * 
 * int[] sushi[n] n번째 초밥을 먹은갯수? 체크
 * 임의의 한 점에서 k개를 세고, 1개를 먹고 하나를 빼고.. 를 반복하며 체크
 * 
 */

import java.io.*;
import java.util.*;
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N,d,k,c;
	static int[] rail;   //회전레일
	static int[]  sushi; //n초밥을 먹은 갯수 저장
	static int cnt,maxCnt;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		sushi = new int[d+1];
		rail = new int[N];
		
		//입력
		for(int idx = 0; idx<N;idx++) {
			rail[idx] = Integer.parseInt(br.readLine());
		}
		
		//무료쿠폰
		sushi[c] = 1;
		cnt++;
		
		//첫 레일
		for(int idx = 0; idx<k;idx++) {
			int sushiType = rail[idx];
			if(sushi[sushiType]++==0) cnt++;
		}
		maxCnt = cnt;
		for(int idx = 0 ; idx<N;idx++) {
			int outSushi = rail[idx];
			int inSushi = rail[(idx+k) %N];
			if(--sushi[outSushi]==0) cnt--;
			if(sushi[inSushi]++==0) cnt++;
			maxCnt = Math.max(maxCnt, cnt);
		}
		
		System.out.println(maxCnt);
		
	}
}
