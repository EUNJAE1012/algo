/*
 * BOJ 2667 단지번호붙이기
 * 정사각형모양 집이 있따. 1은 집 0은 공터
 * 집이 연결됨 = 단지 대각선인정x
 * 각 단지마다 몇개의집인지 출력하라
 * 
 * floodfill?
 * 
 */
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static int[][] map;
	static List<Integer> dangi;
	static int dangiNum;
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int size;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		size = Integer.parseInt(br.readLine());
		map = new int[size][size];
		dangi = new ArrayList<>();
		for(int row = 0; row<size;row++) {
			String line = br.readLine();
			for(int col =0; col<size;col++) {
				map[row][col] = line.charAt(col)-'0';
			}
		}
		
		for(int row=0; row<size;row++) {
			for(int col=0; col<size;col++) {
				if(map[row][col]!=1) continue;
				map[row][col] +=1;
				
				dangi.add(1);
				dfs(row,col);
				dangiNum+=1;
			}
		}
		
		Collections.sort(dangi);
		System.out.println(dangi.size());
		for(int i=0;i<dangi.size();i++) {
			System.out.println(dangi.get(i));
		}
		
	}
	static void dfs(int row,int col) {
		for(int d = 0; d<4; d++) {
			int nr = row+dr[d];
			int nc = col+dc[d];
			if(nr<0||nc<0||nr>=size||nc>=size||map[nr][nc]!=1)continue;
			map[nr][nc] +=1;
			dangi.set(dangiNum,dangi.get(dangiNum)+1);
			dfs(nr,nc);
			
		}
	}
	
}
