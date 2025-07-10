import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*BOJ 1647 : 도시 분할 계획
 * 어느 평화로운 마을은 N개의 집과 M개의 길로 이루어져있다.
 * N개 정점 M개 간선의 무방향 그래프이다.
 * 마을을 반갈할건데 분리된 마을은 집들이 서로 연결되어야 한다.
 * 즉 신장트리여야 한다.
 * 이왕이면 최소신장트리여야한다. 
 * 최소 비용을 구하라.
 * 
 * 그래프 하나를 두개의 독립된 최소 신장트리로 만든다..
 * N-2개의 간선을 선택하는 크루스칼을 하면 되지 않을까?
 * N-1개를 선택하면 전체 신장트리니까, 가장 비용이 큰 간선 하나를 짤라버리면?
 * 두개의 최소신장트리가 된다.
 * 
 * 신장트리 만드는 법(크루스칼) -> 
 * 1.간선을 낮은 가중치 순으로 정렬
 * 2.하나씩 사이클이 없게 뽑는다.
 * 3.사이클 검사는 유니온파인드
 * 
 */
public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int N,M; //정점수 간선수
	static List<edge> graph;
	static int[] parent;
	
	static class edge implements Comparable<edge>{
		int from;
		int to;
		int weight;
		
		edge(int from, int to, int weight){
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(edge o) {
			return Integer.compare(weight, o.weight);
		}
		
		@Override
		public String toString() {
			return from+"->"+to+" : "+weight;
		}
		
	}
	static void make() {
		parent = new int[N+1];
		for(int idx=1; idx<=N;idx++) {
			parent[idx] = idx;
		}
	}
	
	static int find(int a) {
		if(a==parent[a]) return a;
		return parent[a] = find(parent[a]);
		
	}
	
	static boolean union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);

		if(rootA ==rootB) return true;
		parent[rootA] = rootB;
		return false;
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		graph = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		make();
		for(int edgeIdx = 0; edgeIdx<M; edgeIdx++) {
			st = new StringTokenizer(br.readLine());
			int before = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			graph.add(new edge(before, to, weight));
			
		}
		Collections.sort(graph);

		int cnt = 0;
		int total=0;
		for(int edgeIdx =0; edgeIdx<M;edgeIdx++) {
			if(cnt==N-2) break;
			edge cur = graph.get(edgeIdx);
			int before = cur.from;
			int after = cur.to;
			int weight = cur.weight;

			if(!union(before,after)) {
//				System.out.println(before+" 연결 " + after + "비용 : "+weight);
				total+=weight;
				cnt++;
				
 			}

		}
		System.out.println(total);
	}
}