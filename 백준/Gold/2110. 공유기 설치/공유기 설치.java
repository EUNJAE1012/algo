import java.io.*;
import java.util.*;

/* BOJ 2110 : 공유기 설치
 * 도현이는 수직선 상에 집이 N개나 있다... 각각 Xn에 있다.
 * 공유기 C개를 설치하는데, 가능한 공유기간의 거리를 멀게 하고 싶다.
 * 가장 인접한 두 공유기 사이의 거리의 최대 값은?
 * 
 * 풀이
 * 최소 거리의 최대 값을 구한다.
 * 최대의 최소화 or 최소의 최대화 = 이분탐색
 * 거리를 기준으로 이분탐색한다.
 * 시작점 : l=X1 r=Xn m= l+r/2 으로 한다. (거리값의 중간)
 * 만약 m = x일때 
 * 놓을 수 있는 공유기의 수 > 전체 공유기의 수? 공유기가 들어갈 자리가 남는다? 
 * 거리를 더 넓힐 수 있다 l=m   
 * 놓을 수 있는 공유기의 수 < 전체 공유기의 수? 공유기가 들어갈 자리가 모자라다?
 * 거리를 더 좁혀야 한다 r=m-1
 * 놓을 수 있는 공유기의 수 = 전체 공유기의 수? 거리는 최대한 큰게 좋다. l=m
 *  
 *  
 * 
 * 
 */

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N,C,maxDistance;
	static int[] X;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		X = new int[N];
		for(int i = 0; i<N; i++) {
			X[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(X);
		System.out.println(binarySearch(1,X[N-1]-X[0]));
		
	}
	
	static int binarySearch(int l, int r) {
		int result = 0;
		//각 거리마다 확인
		while(l<=r) {
			int distance = (l+r) >>1;
			if(canInstall(distance)) {
				result =distance; //일단 저장
				l = distance+1;
			}
			else {
				r = distance-1;
			}
		}
		return result;

	}
	static boolean canInstall(int distance) {
		//첫번째 집은 무조건 설치한다 가정
		int cnt = 1;
		int gap = 0;
		for(int idx = 1; idx<N;idx++) {
			//이 전 공유기 설치장소와의 거리
			gap += X[idx] - X[idx-1];
			//특정 거리 이상, 놓을 수 있는 집
			if(gap>=distance) {
				//설치
				cnt++;
				if(cnt>=C) {
					return true;
				}
				//거리 누적값 초기화
				gap = 0;	
			}
		}
		return false;
	}
	
}
