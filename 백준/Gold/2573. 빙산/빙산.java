/*
 * BOJ 2573 빙산
 * 북극의 빙산이 녹는다. 2차원 배열로, 각 칸에 빙산의 높이가 저장된다..
 * 바다는 0이다.
 * 동서남북 4방향에 인접한 바다의 수만큼 빙하가 녹는다.
 * 한덩어리의 빙산이, 두덩어리 이사응로 분리되는 최초의 시간을 구하라
 * 
 * 풀이 
 * 초기에 각 바다를 돌며, 인접한, 즉 녹을 빙하들을 Queue에 넣는다. visited처리해서 중복처리하지 않는다.
 * 0,0은 무조건 바다니까 0,0부터 BFS를 하면 된다.
 * Queue를 순회하며 각 빙하를 녹인다.
 * 	 이때 빙하가 0이 되면, 4방의 빙하들을 nextTimeQueue에 추가로 넣는다.
 * 	 이때 빙하가 0이 아니라면, nextTimeQueue에 넣는다.
 * 
 * Queue순회가 끝나면, 빙하가 두동강 났는지 확인해야한다.
 *   빙하하나 꺼내서BFS, 전체빙하수와 비교
 *   
 *   thisTimeQueue = nextTimeQueue;
 * 
 * 
 */
import java.io.*;
import java.util.*;
public class Main {
	
	static StringTokenizer st;
	static BufferedReader br;
	static int[][] map;
	static int[] dc = {0,0,-1,1};
	static int[] dr = {-1,1,0,0};
	static int iceCount,rowSize,colSize;
	static LinkedList<Pos> thisTimeQueue;
	static LinkedList<Pos> nextTimeQueue;
	static boolean[][] visited;
	static Pos ice;
	static class Pos{
		int row;
		int col;
		Pos(int row, int col){
			this.row = row;
			this.col = col;
		}
		@Override
			public String toString() {
				return row+","+col;
			}
	}
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		thisTimeQueue = new LinkedList<>();
		nextTimeQueue = new LinkedList<>();
		map = new int[rowSize][colSize];
		visited = new boolean[rowSize][colSize];
		for(int row = 0; row<rowSize;row++) {
			st = new StringTokenizer(br.readLine());
			for(int col= 0; col<colSize; col++) {
				int c =  Integer.parseInt(st.nextToken());
				if(c!=0) iceCount++;
				map[row][col] = c;
			}
		}
		int time = 0;

		while(true) {
			setup();
			melting();
			iceBreaking();
			time++;
//			printMap();
			if(isBroken(time)) {
				return;
			}
		}
		
	}
	//바다인접빙하에 대해서, 녹임 작업을 진행한다.
	static void melting() {
//		System.out.println("녹을녀석들"+thisTimeQueue);
		while(!thisTimeQueue.isEmpty()) {
			Pos cur = thisTimeQueue.poll();
			int cnt =0;
			//사방의 바다수를 세고
			for(int d =0; d<4;d++) {
				int nr = cur.row+dr[d];
				int nc = cur.col+dc[d];
				if(nr<0||nc<0||nr>=rowSize||nc>=colSize)continue;

				if(map[nr][nc]==0) {
					cnt++;
				}
			}
			//만약 와장창이라면
			if(map[cur.row][cur.col] <= cnt) {
					//사형선고
					nextTimeQueue.add(cur);
				}
			else {
				map[cur.row][cur.col] -= cnt;
			}

		}
		
	}
	
	static void printMap() {
		for(int r = 0; r<rowSize;r++) {
			System.out.println(Arrays.toString(map[r]));
		}
		System.out.println();
	}
	
	// 얼음 녹이기
	static void iceBreaking() {
//		System.out.println("다녹은녀석들 "+nextTimeQueue);
		while(!nextTimeQueue.isEmpty()) {
			Pos cur = nextTimeQueue.poll();
			map[cur.row][cur.col] = 0;
			iceCount--;
		}
	}
	
	//모든 바다에대해서 BFS해서, 바다인접빙하를 Queue에 담는다.
	static void setup() {
		Queue<Pos> queue = new ArrayDeque<>();
		visited = new boolean[rowSize][colSize];
		for(int row = 0; row<rowSize;row++) {
			for(int col=0; col<colSize;col++) {
				if(map[row][col]!=0 || visited[row][col]) continue;
				visited[row][col] = true;
				queue.add(new Pos(row,col));
				while(!queue.isEmpty()) {
					Pos cur = queue.poll();
					for(int d = 0; d<4;d++) {
						int nr = cur.row+dr[d];
						int nc = cur.col+dc[d];
						if(nr<0||nc<0||nr>=rowSize||nc>=colSize||visited[nr][nc])continue;
						visited[nr][nc] = true;
						Pos nxt = new Pos(nr,nc);
						if(map[nr][nc]==0)
						{
							queue.add(nxt);
						}
						else {
							thisTimeQueue.add(nxt);
						}
						
					}
				}
				
			}
		}
	}
	

	
	static boolean isBroken(int time) {
		int row=-1,col=-1;
		for(int r = 0; r<rowSize;r++) {
			for(int c= 0;c<colSize;c++) {
				if(map[r][c]!=0) {
					row =r;
					col =c;
				}
			}
		}
		if(row==-1||col==-1) {
			System.out.println(0);
			return true;
		}
		int cnt = 0;
		boolean[][] visited2 = new boolean[rowSize][colSize];
		visited2[row][col] = true;
		Queue<Pos> queue = new ArrayDeque<>();
		queue.add(new Pos(row,col));
		
		while(!queue.isEmpty()) {
			Pos cur = queue.poll();
			cnt++;
			for(int d = 0; d<4;d++) {
				int nr = cur.row+dr[d];
				int nc = cur.col+dc[d];
				if(nr<0||nc<0||nr>=rowSize||nc>=colSize||visited2[nr][nc]||map[nr][nc]==0)continue;
				visited2[nr][nc] = true;
				
				queue.add(new Pos(nr,nc));
			}
		}
		if(cnt!=iceCount) {
			System.out.println(time);
		}
		return !(cnt==iceCount);
	}
}