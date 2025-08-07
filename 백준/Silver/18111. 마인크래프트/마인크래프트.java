
/*
 * BOJ 18111 : 마인크래프트
 * N*M 집터에 평탄화를 한다. 인벤토리에 B개의 블록이 있고, 땅의 높이는 256칸이 최대이다.
 * 1. 좌표(i,j)의 가장 위 블록을 제거하여 인벤토리에 넣는다.
 * 2. 인벤토리의 블록하나를 꺼내 좌표 (i,j)의 가장 위에 둔다.
 * 1번은 2초, 2번은 1초 걸린다.
 * 평탄화가 완료되는 시간과 땅의 높이는?
 * 답이 여러개라면 가장 높은 땅을 기준으로 한다.
 * 
 * 최대높이에서 시작한다.
 * dfs(height, time)
 * basecase: 모든 블록의 높이가 같다. return
 * 1. 모든 블록을 한칸 덮을수 있다면? 덮는다.(255이하만)
 * 2. 모든 블록을 한칸 깐다.(0이상만)
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
	static int N,M,maxHeight,minTime;
	static long B;
	static int[][] map;
	static int[] blocks;
	static int needBlockCnt,lastHeight;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		B = Long.parseLong(st.nextToken());
		map = new int[N][M];
		blocks = new int[257];
		minTime = Integer.MAX_VALUE;
		for(int row = 0; row<N;row++) {
			st = new StringTokenizer(br.readLine());
			for(int col=0; col<M;col++) {
				int cur = Integer.parseInt(st.nextToken());
				blocks[cur] +=1;
				maxHeight = Math.max(maxHeight, cur);
				map[row][col] = cur;
			}
		}
		
		for(int height = 0; height<maxHeight;height++) {
			needBlockCnt+= (maxHeight-height )*blocks[height];
		}
		
		dfs(maxHeight,0,false);
		
		System.out.println(minTime+" "+lastHeight);
	}
	static void dfs(int height,int time,boolean cheat) {
		if(time>=minTime) return;
//		System.out.println(height+":"+time+","+Arrays.toString(blocks));
		//모든 블록의 높이가 같다면, 
		if(blocks[height]==N*M||cheat) {
			minTime=time;
			lastHeight = height;
			return;
		}
		int temp = blocks[height];
		//나머지를 현재칸에 맞추던가
		if(B>=needBlockCnt) {
			dfs(height,needBlockCnt*1+time,true);
		}
		
		//최대칸을 낮추거나
		if(height==0) return;
		B += blocks[height];
		needBlockCnt -= (N*M-blocks[height]);
		blocks[height-1] += blocks[height];
		blocks[height] = 0;
		dfs(height-1,time+2*temp,false);
//		needBlockCnt += N*M-blocks[height];
//		B -= blocks[height];
		
		
		
	}
}