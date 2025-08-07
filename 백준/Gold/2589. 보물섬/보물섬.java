
/*
 * 보물섬
 * 보물섬지도는 직사각형이며 n*m칸이다.
 * 각 칸은 육지(L) 혹은 바다(W)이다.  상하좌우로 이웃한 육지로만 이동가능
 * 한칸이동 = 1시간 , 보물은 서로 최단 거리로 이동하기 가장 먼 육지 두곳에 놓여있다.
 * 육지를 나타내는 두 곳 사이를 최단 거리로 이동하려면 같은 곳 두번 못간다.
 * 보물간의 이동 시간을 구하라.
 * 
 * 풀이
 * 가장 BFS 거리가 먼 두 육지의 거리를 구하는 문제이다.
 * 플로이드 워셜로 모든 칸 to 모든 칸의 거리를 구한다.
 * 가장 큰 값을 반환한다.
 * 걍 모든점에서 BFS를 돌려볼까..?
 * => 시간초과나면 dp로 선회해보자
 * 각 칸을 고유 번호를 준다.
 * 첫번째칸은 정석 bfs -> dp[start][connected] 초기화
 * 전체칸을 방문처리하며 순회 dp[cur][visited]? 
 * dp[before][after] = 
 * 
 */
import java.io.*;
import java.util.*;
public class Main {
	
	static StringTokenizer st;
	static BufferedReader br;
	static char[][] map;
	static boolean[][] visited;
	static int[] dc = {0,0,-1,1};
	static int[] dr = {-1,1,0,0};
	static int rowSize,colSize,maxDistance;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		map = new char[rowSize][colSize];
		visited = new boolean[rowSize][colSize];
		for(int row = 0; row<rowSize;row++) {
			map[row] = br.readLine().toCharArray();	
		}
		
		for(int row=0;row<rowSize;row++) {
			for(int col=0; col<colSize;col++) {
				if(map[row][col]=='W') {
					continue;
				}
				int landID = row*rowSize+col;
				bfs(row,col);
			}
		}
		
		System.out.println(maxDistance);
		
	}
	static void bfs(int row, int col) {
		visited = new boolean[rowSize][colSize];
		visited[row][col] = true;
		ArrayDeque<int[]>queue = new ArrayDeque<>();
		queue.add(new int[] {row,col,0});
		while(!queue.isEmpty()) {
			int[] cur =queue.poll();
			int curRow = cur[0];
			int curCol = cur[1];
			int curDistance = cur[2];
			for(int d = 0; d<4;d++) {
				int nxtRow = curRow+dr[d];
				int nxtCol = curCol+dc[d];
				int nxtDistance = curDistance+1;
				if(nxtRow<0||nxtCol<0||nxtRow>=rowSize||nxtCol>=colSize||visited[nxtRow][nxtCol]
						||map[nxtRow][nxtCol]!='L')continue;
				visited[nxtRow][nxtCol]= true;
				maxDistance = Math.max(nxtDistance, maxDistance);
				queue.add(new int[] {nxtRow,nxtCol,nxtDistance});
			}
		}
		
//		System.out.println(String.format("%d , %d => %d", row,col,maxDistance));
	
	}
}
