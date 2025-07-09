package BOJ2623;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
/*
 * BOJ 2623 : 음악방송
 * 가수 N명의 출연 순서를 정한다.
 * 보조 PD M명이 각자의 담당 가수들의 순서를 정해온다.
 * 보조 PD 들이 가져온 순서를 만족하도록 전체 순서를 정하자.
 * M<=100, N<=1000
 * 답이 여럿이면 아무거나, 불가능하면 0을 출력
 * 
 * 위상정렬 이다 그죠?
 * 선행되어야하는 가수의 수를 저장하고 하나씩 빼기(bfs)
 * 
 */
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int M,N;
	static List<List<Integer>>singers;
	static int[] prior;
	static Queue<Integer>queue; 
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		prior = new int[N+1];
		singers = new ArrayList<>();
		for(int i = 0 ; i<=N;i++) { //1인덱스
			singers.add(new ArrayList<>());
		}
		
		M = Integer.parseInt(st.nextToken());
		
		
		for(int pdIdx = 0; pdIdx<M; pdIdx++) {
			st = new StringTokenizer(br.readLine());
			int c = Integer.parseInt(st.nextToken());
			int[] arr = new int[c];
			for(int i = 0 ; i<c;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			for(int i = 0 ; i<c-1;i++) {
				int before = arr[i];
				int after = arr[i+1];
				singers.get(before).add(after);
				prior[after]+=1;
			}
		}
		
		queue = new ArrayDeque<Integer>();
		
		for(int i = 1; i<=N;i++) {
			if(prior[i]==0) queue.add(i);
		}
		int cnt = 0;
		while(!queue.isEmpty()) {
			cnt++;
			int cur = queue.poll();
			for(int nxt : singers.get(cur)) {
				prior[nxt]-=1;
				if(prior[nxt]==0) {
					queue.add(nxt);
				}
			}
			sb.append(cur).append("\n");
		}
		if(cnt!=N)System.out.println(0);
		else System.out.println(sb);
	}
}
