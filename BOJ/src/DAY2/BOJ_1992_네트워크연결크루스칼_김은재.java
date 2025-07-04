package DAY2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * 문제
 * 컴퓨터 to 컴퓨터로 모든 컴퓨터의 최소 신장 트리를 만드려고 한다.
 * 풀이
 * 크루스칼 알고리즘을 이용한다.
 * 1. 그래프를 간선 그래프로 나타낸다.
 * 2. 간선 그래프를 가중치 순으로 정렬한다.
 * 3. 사이클이 생기지 않도록 간선 (N-1)개 선택한다.
 * 3.1 사이클 검사는 union-find 알고리즘을 사용한다.
 */

import java.util.*;
public class BOJ_1992_네트워크연결크루스칼_김은재 {
	
	static int computerCnt,edgeCnt;
	static BufferedReader br;
	static StringTokenizer st;
	static List<Edge>edges;
	static int[] parents;
	static int[] rank;
	
	static class Edge implements Comparable<Edge>{
		int from;
		int to;
		int weight;
		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.weight, o.weight);
		}
		
	}
	static void make() {
		parents = new int[computerCnt+1];
		rank = new int[computerCnt+1];
		for(int idx =1; idx<=computerCnt;idx++) {
			parents[idx] = idx;

		}
	}
	static int find(int e1) {
		if(e1==parents[e1]) return e1;
		return parents[e1]=find(parents[e1]);
	}
	static boolean isSame(int e1, int e2) {
		int root1 = find(e1);
		int root2 = find(e2);
		if(root1 == root2) return true;
		if(rank[root1]>rank[root2]) {
			parents[root2] = root1;
			return false;
		}
		parents[root1] =root2;
		if(rank[root1]==rank[root2]) {
			rank[root2] +=1;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		computerCnt = Integer.parseInt(br.readLine());
		edgeCnt = Integer.parseInt(br.readLine());
		edges = new ArrayList<>();
		for(int idx =0; idx<edgeCnt;idx++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			edges.add(new Edge(from, to, weight));
		}
		Collections.sort(edges);
		
		make();
		int total = 0;
		int cnt = 0;
		for(int idx = 0; idx<edgeCnt;idx++) {
			if(cnt==computerCnt-1) {
				break;
			}
			Edge curEdge = edges.get(idx);
			if(isSame(curEdge.from, curEdge.to))continue;
			total+=curEdge.weight; 
			cnt++;
		}
		System.out.println(total);
	}
}
