/*
 * BOJ 2178 미로탐색
 * N*M 미로가있다. 1은 길, 0은 벽이다.
 * 1.1 -> N,M 로 이동할때 최소 거리수는?
 * 이동은 인접한 칸만 된다.
 * 
 * 그냥 BFS같은데
 */
import java.util.*;
import java.io.*;
public class Main {
	
	static StringTokenizer st;
	static BufferedReader br;
	static int[][] map;
	static int rowSize,colSize;
	static boolean[][] visited;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		rowSize =Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		visited = new boolean[rowSize][colSize];
		map = new int[rowSize][colSize];
		
		for(int row = 0; row<rowSize;row++) {
			String line = br.readLine();
			for(int col = 0; col<colSize; col++) {
				map[row][col] = line.charAt(col)=='0'?0:1;
				visited[row][col] = map[row][col] ==0;
			}
		}
		
		System.out.println(bfs());
		
	}
	
	static int bfs() {
		int distance =0;
		ArrayDeque<int[]>queue = new ArrayDeque<>();//row col
		queue.add(new int[] {0,0,1});
		visited[0][0] = true;
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int row = cur[0];
			int col = cur[1];
			distance = cur[2];
			if(row==rowSize-1 && col==colSize-1) return distance;
			for(int dir = 0; dir<4;dir++) {
				int nr = row+dr[dir];
				int nc = col+dc[dir];
				if(nr==rowSize-1 && nc==colSize-1) return distance+1;
				if(nr<0||nr>=rowSize||nc<0||nc>=colSize||visited[nr][nc]) {
					continue;
				}
				visited[nr][nc] = true;
				queue.add(new int[] {nr,nc,distance+1});
			}
			
		}
		return distance;
	}
}
