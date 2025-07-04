package Day1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//문제
//NXM 사무실을 K개의 CCTV로 감시한다.
//CCTV는 5종류다.
//1방 감시 카메라(1), 직선 감시 카메라(2), 직각 감시카메라(3), 3방 감시 카메라(4), 4방 감시 카메라(5)
//바라보는 방향을 끝까지 볼 수 있지만 벽(6)을 투시할 수는 없다.
//CCTV는 회전 시킬 수 있다.
//CCTV를 적당히 회전시켜서, 사각지대의 최소크기를 구하라
//풀이
//N,M<=8
//이게 뭐징..?
//CCTV 가 8개 이하고, 방의 크기도 작으니 완탐을 해도 되지 않나 조합으루 CCTV들 다 돌려가면서, 8^4 = = 2^16 가지의 경우의 수..

import java.util.*;
public class BOJ_15683_감시_김은재 {
 
 static BufferedReader br;
 static StringTokenizer st;
 static int[][] board;
 static int rowSize,colSize,minCnt;
 static List<CCTV>cctvList;
 static int[] dr = {1,-1,0,0}; //상하좌우
 static int[] dc = {0,0,-1,1};
 static int[][][] cctvTypeDirection = {
     {},
     {{0},{1},{2},{3}}, //1번 cctv 0번 방향들 = cctvTypeDirection[1][0] 
     {{0,1},{2,3}}, //2번 cctv 방향들
     {{0,2},{0,3},{1,2},{1,3}}, //3번 cctv 방향들
     {{1,2,3},{0,2,3},{0,1,3},{0,1,2}},
     {{1,2,3,0}}
     };
 
 static class CCTV{
	 int row;
	 int col;
	 int type;
	 int direction;
	 
	 public CCTV(int row, int col, int type) {
	     this.row = row;
	     this.col = col;
	     this.type = type;
	     this.direction =0;
	 }
 
 }
 
 
 public static void main(String[] args) throws Exception{
	 br = new BufferedReader(new InputStreamReader(System.in));
	 st = new StringTokenizer(br.readLine());
	 rowSize = Integer.parseInt(st.nextToken());
	 colSize = Integer.parseInt(st.nextToken());
	 board = new int[rowSize][colSize];
	 cctvList = new ArrayList<>();
	 minCnt = Integer.MAX_VALUE;
 
	 for(int row = 0; row<rowSize;row++) {
	     st = new StringTokenizer(br.readLine());
	     for(int col =0; col<colSize;col++) {
	         int c = Integer.parseInt(st.nextToken());
	         board[row][col] = c;
	         if(c!=0 && c!=6) {
	             cctvList.add(new CCTV(row,col,c));
	         }
	         
	     }
	 }
	 
	 comb(0);
	 
	 System.out.println(minCnt);
 }
	 
 	static void comb(int selectedCnt) {
 		if(selectedCnt == cctvList.size()) { //기저조건 모든 cctv확인
// 			printboard(board);
// 			System.out.println(count());
 		
 			minCnt = Math.min(minCnt, count());
 			return;
 		}
 		CCTV cc = cctvList.get(selectedCnt); //cctv 선택
 		for(int[] directions : cctvTypeDirection[cc.type]) { //가능한 모든 방향에 대해서
 			int[][] tmp = backup(board);
 			for(int direction : directions) { //방향을 전부 다 보고 온다.
	         watch(cc,direction);
 			}
	         comb(selectedCnt+1);
	         board=tmp; //백업
 		}
 
}
 
 	/*
 	 * 깊은복사
 	 */
	 static int[][] backup(int[][] board){
		 int[][] arr2 = new int[board.length][];
		 for(int row =0; row<board.length;row++) {
		     arr2[row] = board[row].clone();
		 }
	 return arr2;
	 }
 
	 
 	static void watch(CCTV cctv,int direction) {
         int nr = cctv.row+ dr[direction];
         int nc = cctv.col+ dc[direction];
         while(nr>=0 && nc>=0 && nr<rowSize && nc<colSize && board[nr][nc]!=6) {
             if(board[nr][nc]==0) {
                 board[nr][nc] =-1;
             }
             nr = nr+ dr[direction];
             nc = nc+ dc[direction];
         }
 	}
 
 	static void printboard(int[][]board) {
		 System.out.println();
		 for(int row =0; row<board.length;row++) {
		     System.out.println(Arrays.toString(board[row]));
		 }
		 System.out.println();
	 }  
 
	static int count() {
		 int cnt=0;
		 for(int row = 0; row<rowSize;row++) {
		     for(int col=0; col<colSize;col++) {
		         if(board[row][col]==0) {
		             cnt++;
		         }
		     }
		 }
		 return cnt;
	}
 
}