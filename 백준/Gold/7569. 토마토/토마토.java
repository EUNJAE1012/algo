/*
 * BOJ7569 토마토
 * 
 * 익은 토마토의 6방향 토마토가 익는다.
 * 며칠후에 다익을까?
 * 
 */
import java.util.*;
import java.io.*;

public class Main {
	static int[][][]box;
	
	static BufferedReader br;
	static StringTokenizer st;
	static int xSize,ySize,zSize,cnt,total;
	static int[] dx = {-1,1,0,0,0,0};//상하좌우 위 아래
	static int[] dy = {0,0,-1,1,0,0};
	static int[] dz = {0,0,0,0,1,-1};
	static ArrayDeque<int[]> queue; //z,y,x,day
	static boolean[][][] visited;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		xSize = Integer.parseInt(st.nextToken());
		ySize = Integer.parseInt(st.nextToken());
		zSize =Integer.parseInt(st.nextToken());
		total = xSize*ySize*zSize;
		box = new int[zSize][ySize][xSize];
		visited= new boolean[zSize][ySize][xSize];
		queue = new ArrayDeque<>();
		for(int z =0;z<zSize;z++) {
			for(int y=0;y<ySize;y++) {
				st = new StringTokenizer(br.readLine());
				for(int x=0; x<xSize;x++) {
					int c = Integer.parseInt(st.nextToken());
					box[z][y][x] = c;
					if(c==1) {
						queue.add(new int[] {z,y,x,0});
						visited[z][y][x] = true;
						cnt++;
					}
					if(c==-1) {
						visited[z][y][x] = true;
						total--;
					}
				}
			}
		}
		if(cnt==0) {
			System.out.println(-1);
			return;
		}
		System.out.println(bfs());	
		
	}
	static int bfs() {
		int maxDay = 0;
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int z = cur[0];
			int y = cur[1];
			int x = cur[2];
			int time = cur[3];
			maxDay = Math.max(maxDay, time);
			for(int d= 0; d<6;d++) {
				int nz = z+dz[d];
				int ny = y+dy[d];
				int nx = x+dx[d];
				if(nz<0 || ny<0 || nx<0 || nz>=zSize||ny>=ySize||nx>=xSize||visited[nz][ny][nx]) {
					continue;
				}

				visited[nz][ny][nx] = true;
				cnt++;
				queue.add(new int[] {nz,ny,nx,time+1});
			}
		}
		if(cnt!=total) return -1;
		return maxDay;
	}
}