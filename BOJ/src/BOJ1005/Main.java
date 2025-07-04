package BOJ1005;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * BOJ 1005 : ACM CRAFT
 * 건물 짓는 게임
 * 건물은 각 Delay 초 후에 건설된다.
 * 건물 짓는 순서가 주어질 때, 특정 건물을 가장 빨리 지을 수 있는 시간을 구하라.
 * 
 * N : 건물의 수 2~1000
 * K : 건설 순서 규칙 수 1~100000
 * Dn : n번째 건물의 건설 시간 0~100000
 * X,Y : X->Y 순서로 건설 가능
 * W : 목표 건물 번호
 * 
 * 위상정렬 + 분할정복 
 * 1. 각 노드를 연결한다.
 * 2. 목표 노드에서부터 선행노드로 간다.
 * 2.1 목표 노드의 선행노드가 없다면, 뒷 노드들에 자신의 건설시간을 더한다.
 * 2.2 
 */
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int N,K,W;
	static int minTime; //최대 10만x 1000 = 1억
	
	static class Node {
		int nodeNum;
		int delay;
		int priority;
		List<Node> prev; //이전노드들
		boolean visited;
		
		Node(int num,int delay){
			this.nodeNum = num;
			this.delay = delay;
			this.priority = 0;
			this.prev = new ArrayList<>();
			this.visited= false;
		}
		
		@Override
		public String toString() {
			return nodeNum+" : "+ prev.toString();
		}
		
		public int addTime() {
			
			if(visited) { //온적있으면 깊게 들어갈 필요 없음.
				return this.delay;
			}
//			System.out.println(nodeNum+"번의 선행노드를 구합니다.");
			//이전 노드 들 중 가장 시간이 큰 값 + 내 값을 반환
			int max = 0;
			for(int idx =0; idx<prev.size();idx++) {
				int temp = prev.get(idx).addTime();
				if(temp > max) max = temp;
			}
			this.delay += max;
			this.visited= true;
//			System.out.println(this+" ,총 시간 : "+delay);
			return this.delay;

		}
	}
	
	static void connect(int prevIdx, int afterIdx) {
		Node prev = nodes.get(prevIdx-1);
		Node after = nodes.get(afterIdx-1);
		after.prev.add(prev);
	}
	
	static List<Node> nodes;
	
	
	
	public static void main(String[] args) throws Exception{
		sb = new StringBuilder();
		br = new BufferedReader(new InputStreamReader(System.in));
		
		
		
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase =1 ; testCase<=T;testCase++) {
			
			nodes = new ArrayList<>();
			
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); //건물의 수
			K = Integer.parseInt(st.nextToken()); //규칙의 수
			
			
			//각 건물의 딜레이들
			st= new StringTokenizer(br.readLine());
			for(int nodeIdx = 1; nodeIdx<=N; nodeIdx++) {
				int delay = Integer.parseInt(st.nextToken());
				nodes.add(new Node(nodeIdx, delay));
			}
			
			//각 건물의 선후관계
			for(int i = 0 ; i<K;i++) {
				st = new StringTokenizer(br.readLine());
				int prev = Integer.parseInt(st.nextToken());
				int after =Integer.parseInt(st.nextToken());
				connect(prev, after);
			}
			
			W = Integer.parseInt(br.readLine());
			
			minTime = nodes.get(W-1).addTime();
			
			sb.append(minTime).append("\n");

			
		}
		
		System.out.println(sb.toString());
		
	}
}
