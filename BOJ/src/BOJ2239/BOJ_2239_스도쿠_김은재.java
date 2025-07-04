package BOJ2239;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*
 * 문제
 *  하다만 9*9 스도쿠가 들어올때, 완성시켜라
 *  
 * 풀이
 *  0의 좌표를 저장한다.
 *  각 0의 좌표에서, 값의 가능 여부를 판단해서 넣는다.
 *  조건 검사를 가로 검사, 세로 검사, 3x3 검사로 분할정복한다.
 *  조건 불만족시 백트래킹
 * 
 */
public class BOJ_2239_스도쿠_김은재 {
	
	static BufferedReader br;
	static StringBuilder sb;
	static List<int[]> zeroList;
	static char[][] sudoku;
	static boolean solved;
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		zeroList = new ArrayList<int[]>();
		sudoku = new char[9][9];
		for(int row = 0; row<9;row++) {
//			board[row] = Integer.parseInt(1+br.readLine());
			sudoku[row] = br.readLine().toCharArray();
			for(int col = 0; col<9;col++) {
				if(sudoku[row][col] =='0') {
					zeroList.add(new int[] {row,col});
				}
			}
		}
		solved=false;
		dfs(0);
		

	}
	static void printsudoku() {
		for(int row =0; row<9;row++) {
			for(int col=0;col<9;col++) {
				System.out.print(sudoku[row][col]);
			}
			System.out.println();
		}
	}
	
	static void dfs(int idx) {
		if(idx == zeroList.size()) {
			solved = true;
			printsudoku();
			return;
		}
		int row = zeroList.get(idx)[0];
		int col = zeroList.get(idx)[1];
		
		for(char num = '1';num<='9';num++) {
			if(isValid(row, col, num)) {
				sudoku[row][col] = num;
				dfs(idx+1);
				if( solved) return; //백트래킹
				sudoku[row][col] = '0';
			}
		}
		
	}
	static boolean isValid(int row, int col, char val) {
		return check3X3(row, col, val) && checkCol(row, val)&& checkRow(col, val);
	}
	
	// 0,1,2 -> 0 3,4,5 ->3 6,7,8 ->6
	static boolean check3X3(int row, int col,char val) {
		int startRow = (row/3 *3);
		int startCol = (col/3 *3);
		for(int r = startRow;r<startRow+3;r++) {
			for(int c = startCol;c<startCol+3;c++) {
				if(sudoku[r][c]==val) {
					return false;
				}
			}
		}
		return true;
		
	}
	static boolean checkRow(int col,char val) {
		for(int r=0;r<9;r++) {
			if(sudoku[r][col] == val) {
				return false;
			}
		}
		return true;
	}
	static boolean checkCol(int row,char val) {
		for(int c=0;c<9;c++) {
			if(sudoku[row][c] == val) {
				return false;
			}
		}
		return true;
	}
	
	
	//idx 열에 val 이 있는지,
//	static boolean checkRow(int idx,int val) {
//		int margin = (int) Math.pow(10, 10-idx);
//		for(int r = 0; r<9;r++) {
//			//col = 9, 10으로 나눈 나머지
//			//col = 1, 1000 000 000 으로 나눈 나머지
//			if(board[r]% margin == val) {
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	
//	//그 행에 val 이있는지, 
//	static boolean checkCol(int col,int val) {
//		for(int c = col; c>0;c/=10) {
//			if(c/10 == val) {
//				return false;
//			}
//		}
//		return true;
//	}
}
/*
 * 예제입력
103000509
002109400
000704000
300502006
060000050
700803004
000401000
009205800
804000107
*/
