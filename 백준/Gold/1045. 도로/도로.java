/*
 * BOJ 1045 도로
 * 0부터 N-1까지 N개의 도시와, 두 도시를 연결하는 도로들이 있다.
 * A도시 B도시가 (A<B) x도로로 연결되어 있고,
 * C,D가 y로 연결되어 있을대, (A,B) < (C,D) 이면 x의 우선순위가 더 높다. A,C중 작은쪽의 더 우선인 도로
 * ai!=bi인 가장 작은 양의 정수 i에서 ai<bi이면 (a...)<(b...)이다.
 * 도로의 집합은 하나 이상의 도로가 우선순위에 대한 내림차순을 정렬되어 있는 것이다.
 * 집합 사이에도 우선순위가 있다. 
 * 한 집합에 있는 도로만으로 임의의 도시 to 임의의 도시 이동이 될때, 그 집합은 연결되어 있다.
 * 즉, MST를 만들 수 있는 도로 목록이면 연결되어 있다는 것이다.
 * 
 *  M개의 도로가 주어질 때, 도로의 집합 중 연결되어있으며 우선순위가 가장 높은 것을 찾아라.
 *  
 * 풀이 
 * 연결되어 있다.= MST + @간선 몇개 더
 * 우선순위가 가장 높다 = 도로 
 * 
 * 
 *  
 *  
 */
import java.util.*;
import java.io.*;
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int N,M;
	static int[] map,parent,rank;
	static PriorityQueue<Edge> edges,nextEdges;

	static class Edge implements Comparable<Edge>{
		int from;
		int to;
		int min;
		int max;
		Edge(int from,int to){
			this.from = from;
			this.to = to;
			this.min = Math.min(from, to);
			this.max = Math.max(from, to);
		}
		@Override
		public int compareTo(Edge o) { //작은게 더 작은쪽이 우선
			if(this.min!=o.min)
			return Integer.compare(this.min, o.min);
			//같으면 큰게 작은쪽
			return Integer.compare(this.max, o.max);
		}
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken()); //집합이 포함하는 도로의 수
		
		
		map= new int[N]; //i를 끝점으로 갖는 수
		edges = new PriorityQueue<>();
		nextEdges = new PriorityQueue<>();
		
		for(int from = 0; from<N;from++) {
			String str = br.readLine();
			for(int to =from+1;to<N;to++) {
				char c = str.charAt(to);
				if(c=='Y') {
					edges.add(new Edge(from,to));
				}
			}
		}
		
		//도로가 M개보다 적다 = 정답 없다.
		if(edges.size()<M) {
			System.out.println(-1);
			return;
		}
		
		
		make();
		
		int cnt = 0;
		//일단 MST를 만든다.
		while(!edges.isEmpty()) {
			Edge cur = edges.poll();
			if(!hasCycle(cur.from,cur.to)) {
				map[cur.from]++;
				map[cur.to]++;
				cnt++;
			}
			else {
				nextEdges.add(cur);
			}
			if(cnt==N-1) { 
				break;
			}
		}
		
		if(cnt<N-1) { //MST 못만들었으면 fail
			System.out.println(-1);
			return;
		}
		
		else {
			//안고르고 버린것 + 아직 안고른 것 총 집합 후
			while(!nextEdges.isEmpty()) {
				edges.add(nextEdges.poll());
			}
			//M개가 될때까지 뽑기
			while(!edges.isEmpty() && cnt<M) {
				Edge cur = edges.poll();
				map[cur.from]++;
				map[cur.to]++;
				cnt++;
				if(cnt==M) {
					break;
				}
			}
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0 ; i< N;i++) {
				sb.append(map[i]).append(" ");
			}
			System.out.println(sb);
		}
		
		
	}
	
	static void make() {
		parent = new int[N];
		rank = new int[N];
		for(int i = 0; i<N;i++) {
			parent[i] = i;
		}
	}
	
	static int find(int a) {
		if(a==parent[a]) return a;
		return parent[a] = find(parent[a]);
	}
	
	static boolean hasCycle(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		if(rootA == rootB) return true; //이미 같다. 그것은 사이클
		
		if(rank[rootA]>rank[rootB]) {
			parent[rootB] = rootA;
			rank[rootA]++;
		}
		else {
			parent[rootA] = rootB;
			rank[rootB]++;
		}
		return false;
	}
	
	
}
