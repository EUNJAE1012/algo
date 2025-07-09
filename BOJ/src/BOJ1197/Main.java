package BOJ1197;

import java.util.*;
import java.io.*;

/* BOJ 1197 : 최소 스패닝 트리
그래프 주어짐 -> 최소 신장 트리 만들기
최소 신장 트리란, 양방향 그래프에서 가장 작은 가중치로 이루어진 신장트리
신장 트리란, 모든 정점이 연결된 가장 트리, 간선의 수는 정점의수 -1

크루스칼 알고리즘 :
1.각 간선의 가중치를 기준으로 정렬한다.
2. 간선을 V-1개 뽑을 때 까지 각 간선의 순서대로
2.1 사이클이 있는지 검사한다 -> 유니온 파인드
2.2 사이클이 없다면 간선 선택
2.3 사이클이 생긴다면 거르고 다음 간선


*/
class Main{

	static StringTokenizer st;
	static BufferedReader br;
	static int V,E;
	static List<edge> graph;
	static int[] parent;
	static class edge implements Comparable<edge>{
		
		int from;
		int to;
		int weight;
		
		edge(int a,int b,int c){
		this.from =a;
		this.to = b;
		this.weight =c;
		}

		@Override
		public int compareTo(edge e2){
			return Integer.compare(this.weight, e2.weight);
		}


	}
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		graph = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());	
		for(int i =0; i<E;i++){
			st = new StringTokenizer(br.readLine());
			int a = 	Integer.parseInt(st.nextToken());
			int b = 	Integer.parseInt(st.nextToken());
			int c = 	Integer.parseInt(st.nextToken());
			graph.add(new edge(a,b,c));
		}
		Collections.sort(graph);

	make();
	int cnt = 0; //선택한 간선의 수
	int idx = 0;
	int total = 0;
	for(idx = 0; idx<E;idx++){
		edge e = graph.get(idx);
		if(union(e.from,e.to)) //사이클?
		continue;
		//사이클이 아니라면
		cnt++;
		total += e.weight;
		if(cnt == V-1) break;
	}	
	System.out.println(total);
}
 
static void make(){
	parent = new int[V+1];
	for(int idx = 1; idx<=V;idx++){
	parent[idx] = idx;
	}
  }

static int find(int a){
	if(a == parent[a]) return a;
	return parent[a] =find(parent[a]);
   }

static boolean union(int a, int b){
	int rootA = find(a);
	int rootB = find(b);
	if(rootA==rootB) return true; //cycle
	parent[rootA] = rootB;

	return false;
}


}