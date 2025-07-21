
/*
 * BOJ 2146: 다리만들기
 * 여러섬인 나라가 있다.
 * 섬과 섬을 잇는 다리중 가장 짧은 다리의 길이를 구하라.
 * 
 * 풀이
 * BFS + floodfill?union-find?
 * 
 * 1. 섬과 섬, 바다를 구분 짓는다. 
 * 2. 바다와 인접한 칸에 대하여, 
 * 	   4방탐색중 바다인 경우에만 확장 BFS -> 자신과 다른섬을 찾을때까지  
 * 
 */
import java.util.*;
import java.io.*;

public class Main {

	static BufferedReader br;
	static StringTokenizer st;
	static int[][] map;
	static boolean[][] visited;
	final static int[] dr={-1,1,0,0}; //상하좌우
	final static int[] dc= {0,0,-1,1};
	static int minLength;
	static int size;
	static int islandNumber;
	static ArrayDeque<int[]> queue;
	static int[] parent;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		size = Integer.parseInt(br.readLine());
		map = new int[size][size];
		islandNumber =1;
		minLength = 1000;
		for(int row = 0; row<size;row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col<size;col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		visited = new boolean[size][size];
		make();
		for(int row = 0; row<size; row++) {
			for(int col=0; col<size;col++) {
				if(map[row][col]==0) continue;
				findIsland(row,col);
			}
		}

//		System.out.println(Arrays.toString(parent));
		
		for(int row = 0; row<size; row++) {
			for(int col=0; col<size;col++) {
				if(map[row][col]==0) continue;
				findBridge(row,col);
			}
		}
		System.out.println(minLength);
	}
	
	static void findBridge(int row, int col) {
//		System.out.println(String.format("%d,%d에서 시작", row,col));
		queue = new ArrayDeque<>();
		queue.add(new int[] {row,col,0});
		visited = new boolean[size][size];
		visited[row][col] = true;
		int island1 = row*size+col;
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int curRow = cur[0];
			int curCol = cur[1];
			int curDistance = cur[2];
			
			for (int d = 0; d<4;d++) {
				int nxtRow =curRow+dr[d];
				int nxtCol =curCol+dc[d];
				//맵바깥 or 이미 본곳은 스킵
				if(nxtRow<0||nxtCol<0
						||nxtRow>=size||nxtCol>=size
			||visited[nxtRow][nxtCol]) {
					continue;
				}
				visited[nxtRow][nxtCol]= true;
				//바다면 더 가보기
				if(map[nxtRow][nxtCol]==0) {
					queue.add(new int[]{nxtRow, nxtCol,curDistance+1});
					continue;
				}
				//섬이면
//				System.out.println(String.format("%d,%d 방문 거리 %d",nxtRow,nxtCol,curDistance));
				int island2 = nxtRow*size+nxtCol;
//				System.out.println(island2+" 섬발견");
				//다른섬을 찾았나요?
				if(!isSameIsland(island1, island2,false)) {
//					System.out.println(island2+"다른섬이다!! 거리"+curDistance);
					minLength = Math.min(minLength, curDistance);
					return;
				}
//				System.out.println("그렇다 같은섬이다.");
				
			}
		}
	}

	//이 칸과 같은섬들을 모두 union해버린다.
	static void findIsland(int row, int col) {
		queue = new ArrayDeque<>();
		queue.add(new int[] {row,col});
		visited[row][col] = true;
		int island1 = row*size+col;
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int curRow = cur[0];
			int curCol = cur[1];
			
			for (int d = 0; d<4;d++) {
				int nxtRow =curRow+dr[d];
				int nxtCol =curCol+dc[d];
				//맵바깥 or 바다는 스킵
				if(nxtRow<0||nxtCol<0
						||nxtRow>=size||nxtCol>=size
						||map[nxtRow][nxtCol]==0
			||visited[nxtRow][nxtCol]) {
					continue;
				}
				visited[nxtRow][nxtCol]= true;
				int island2 = nxtRow*size+nxtCol;
				isSameIsland(island1, island2,true);
				queue.add(new int[]{nxtRow, nxtCol});
			}
		}
		
	}
	
	static void make() {
		parent = new int[size*size];
		for(int row=0;row<size;row++) {
			for(int col=0;col<size;col++) {
				if(map[row][col]==0) {
					parent[row*size+col] = -1;
					continue;
				}
				parent[row*size+col]= row*size+col;
			}
		}
	}
	static int find(int idx) {
		if(parent[idx]==idx) return idx;
		return parent[idx] = find(parent[idx]);
	}
	
	static int find2(int idx) {
		if(parent[idx]==idx) return idx;
		return find(parent[idx]);
	}
	static boolean isSameIsland(int a,int b,boolean c) {
//		System.out.println(a+"와"+b+"는 같은섬일까?");
		int rootA = c? find(a):find2(a);
		int rootB = c? find(b):find2(b);
		if(rootA==rootB) return true;
		if(c)parent[rootB] = rootA;
		
		
		return false;
	}
	
}
