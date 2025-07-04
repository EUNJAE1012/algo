package BOJ17472;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 문제
 * NXM 지도에서 섬들이 놓여있다.
 * 섬들을 다리로 연결한다.
 * 섬 to 섬을 다리로 직접 연결하면, 섬과 섬이 연결된 것이다.
 * 다리는 직선만 된다. 교차해도 된다.
 * 다리의 길이는 2 이상이여야 한다.
 * 
 * 풀이
 * 각 섬을 노드, 다리를 간선이라 할때,
 * 최소 신장 트리를 만들어야 한다 (단 다리는 2이상)
 * 가능한 다리의 경우를 모두 찾고, 노드로 나타내서 크루스칼 알고리즘을 사용한다.
 * 
 * 1. 섬 공간에서 1칸씩 이동하며, 연결된 땅을 하나의 섬으로 나타낸다.
 * 1.1 배열을 순회하며, 땅을 만날경우 섬을 생성하며 BFS 시작
 * 1.1.1 4방탐색 진행
 * 1.1.2 땅이 아닐 경우 스킵
 * 1.1.3 땅이면 방문처리 후 섬 객체에 좌표를 포함시킨다.
 * 1.1.4 그 땅에서 BFS이어가기
 * 
 * 2. 각 섬에서 다른섬까지 최소 직선거리 (단 2보다큰) 찾기
 * 2.1 찾았으면, 간선 리스트로 저장 (PriorityQueue 사용)
 * 
 * 
 * 3. 크루스칼로 간선 섬갯수-1개 선택
 * 3.1 간선리스트에서 하나씩 poll
 * 3.2 두 섬이 연결되지 않았다면, union(연결) 후 가중치 총합에 추가
 * 3.3 두 섬이 연결되었다면 건너뜀
 * 
 * 
 * 
 */
public class BOJ_17472_다리만들기2_김은재 {
	static BufferedReader br;
	static StringTokenizer st;
	static int rowSize, colSize,islandCnt;
	static boolean[][] isIsland;
	static int[][] map;
	static int[][] minDistance;
	static int[] parents;
	static List<Island> islandList;
	static PriorityQueue<Edge> edgeList;
	static Queue<Pos> queue;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	
	static class Edge implements Comparable<Edge>{
		int from;
		int to;
		int weight;
		public Edge(int from, int to, int weight) {
			this.to = to;
			this.from = from;
			this.weight = weight;
		}
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(weight, o.weight);
		}
		@Override
		public String toString() {
			return from + "->" + to + ": " + weight;
		}
		
	}
	static class Pos{
		int row;
		int col;
		
		public Pos(int row, int col) {
			this.row = row;
			this.col = col;
		
		}

		@Override
		public String toString() {
			return row + "," + col + "  ";
		}
		
	}
	
	static class Island{
		int no;
		List<Pos> landList;

		
		public Island(int no) {
			this.no = no;
			this.landList = new ArrayList<>();
		}
		@Override
		public String toString() {
			return "섬 번호 : "+no+landList.toString()+"\n";
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		//init
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine().trim());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		isIsland = new boolean[rowSize][colSize];
		islandList = new ArrayList<>();
		islandList.add(null);
		map = new int[rowSize][colSize];
		queue = new ArrayDeque<>();
		
		//input
		for(int row = 0; row<rowSize;row++) {
			st = new StringTokenizer(br.readLine().trim());
			for(int col=0; col<colSize;col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		int islandIdx = 1;
		for(int row = 0; row<rowSize;row++) {
			for(int col=0; col<colSize;col++) {
				//미개척지 발견?
				if(map[row][col]==1 && !isIsland[row][col]) {
					//신난다! 
					islandList.add(new Island(islandIdx));
					map[row][col] = islandIdx;
					bfs(row,col,islandIdx++);
					
				}
			}
		}
		islandCnt = islandList.size();
		minDistance = new int[islandCnt][islandCnt];
		for(islandIdx=1;islandIdx<islandCnt;islandIdx++) {
			Arrays.fill(minDistance[islandIdx], Integer.MAX_VALUE);
			minDistance[islandIdx][islandIdx]=0;
			minDistance[islandIdx][0] =0;
		}
//		System.out.println(islandList+","+islandCnt+" ");
		
		//섬의
		for(islandIdx=1; islandIdx<islandCnt; islandIdx++) {
			Island currentIsland = islandList.get(islandIdx);
			//각 땅에서
			for(Pos currentLand : currentIsland.landList) {
				//4방 탐색	
				for(int d = 0;d<4;d++) {
					//끝까지.
					for(int distance=1;distance<=10;distance++) {
					int nr = currentLand.row+distance*dr[d];
					int nc = currentLand.col+distance*dc[d];
					//범위바깥 or 같은섬 이면 다음방향
					if(nr<0 || nc<0 || nc>=colSize || nr>=rowSize || map[nr][nc]==islandIdx) break;
					//바다면 좀 더가봄
					if(map[nr][nc] == 0 ) continue;
					//다른섬이라면.. 최소 거리 갱신
					if(distance>=3) {
						minDistance[islandIdx][map[nr][nc]] = 
								Math.min(distance-1, minDistance[islandIdx][map[nr][nc]]);
					}
					//다른섬인데 거리가 2미만임.. 못감..
					break;

					}
				}

			}
		}
		
//		for(islandIdx=1;islandIdx<islandCnt;islandIdx++) {
//			System.out.println(Arrays.toString(minDistance[islandIdx]));
//		}
//		
		
		//크루스칼 -> 간선리스트 -> 정렬
		edgeList = new PriorityQueue<>();
		parents = new int[islandCnt];
		make();
		for(int from = 1;from<islandCnt;from++) {
			for(int to = from+1;to<islandCnt;to++) {
				if(minDistance[from][to]!=0 && minDistance[from][to]!=Integer.MAX_VALUE) {
					edgeList.add(new Edge(from, to, minDistance[from][to]));
				}
			}
		}
		
		int cnt =0;
		int total =0;
		while(!edgeList.isEmpty()&&cnt<islandCnt-2) {
			Edge e = edgeList.poll();
			if(!isConnected(e.from, e.to)) {
				cnt+=1;
				total+=e.weight;
			}
		}

		
		System.out.println(cnt!=islandCnt-2?-1:total);
		
		
	}
	static void make() {
		for (int i = 0; i < islandCnt; i++) parents[i] = i;
	}
	// find 함수 (경로 압축)
	static int find(int x) {
	    if (parents[x] == x) return x;
	    return parents[x] = find(parents[x]);
	}

	// union 함수
	static boolean isConnected(int a, int b) {
	    int rootA = find(a);
	    int rootB = find(b);
	    if (rootA == rootB) return true;
	    parents[rootB] = rootA;
	    return false;
    }

	static void bfs(int row, int col, int islandIdx) {
		queue.offer(new Pos(row, col));
		isIsland[row][col] = true;
		Island island = islandList.get(islandIdx);
		island.landList.add(new Pos(row,col));
		while(!queue.isEmpty()) {
			Pos cur = queue.poll();
			for(int d = 0; d<4;d++) {
				int nr = cur.row+dr[d];
				int nc = cur.col+dc[d];
				if(nr<0 || nc<0 || nr>=rowSize||nc>=colSize||
						map[nr][nc]==0 ||isIsland[nr][nc]) continue;
				isIsland[nr][nc] = true;
				map[nr][nc] = islandIdx;
				island.landList.add(new Pos(nr,nc));
				queue.offer(new Pos(nr,nc));
			}
			
		}
		
	}
	
}
