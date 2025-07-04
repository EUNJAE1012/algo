package BOJ3109;

import java.util.StringTokenizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * 문제
 * R*C 격자에서 첫째열의 가스관을 마지막열 빵집으로 연결한다.
 * 가스관은 - / \ 모양으로 둘 수있다.
 * 가스관은 각 칸에 하나이다. 건물'x'이 있는 칸은 지나갈 수 없다.
 * 최대 연결 가능한 파이프라인의 수는?
 * 풀이
 * 각 가스관(1열)에서 출발하여, 빵집까지 이동한다(DFS)
 * 초기 접근은 몇가지 경우의 수가 가능한 지를 구하는 문제인줄 알고 돌아갔다.
 * 하지만, 동시에 가능한 파이프라인 수만 구하면 된다.
 * 1. 각 행의 1열에서 시작한다.
 * 2. 3방탐색(우상, 우, 우하)를 확인하며, x가 없다면 이동(DFS),반복한다.
 * 2.1 이때 지나간 길은 X로 만든다. 
 * 지나온 길을 무조건 x로 만들어도 되는 이유 :
 목적지에 도달한다면 x가 되는것이 맞고, 목적지에 도달할 수 없는 위치라면 안가도 된다. 
 * 2.2 마지막열에 도달하면 cnt++;
 * 
 */
public class BOJ_3109_빵집_김은재{

	static int maxR, maxC, cnt;
	static char[][] board;
	static boolean[][] visited;
	static int[] dr = { -1, 0, 1 }; // 오위, 오, 오아
	static int[] dc = { 1, 1, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		cnt = 0;
		maxR = Integer.parseInt(st.nextToken());
		maxC = Integer.parseInt(st.nextToken());
		board = new char[maxR][maxC];

		for (int row = 0; row < maxR; row++) {
			board[row] = br.readLine().toCharArray();
		}

		for (int row = 0; row < maxR; row++) {
			if (dfs(row, 0)) { // 끝까지 갈 수있으면 true
				cnt++; // 횟수 추가
			}
		}

		System.out.println(cnt);
	}

	// 각 행에서 빵집까지 갈 수 있으면 true, 아니면 false 반환
	static boolean dfs(int row, int col) {
		if (col == maxC - 1) { // 빵집 도달시
			return true;
		}
		// 더 가야 한다면
		for (int d = 0; d < 3; d++) { // 3방탐색
			int nRow = row + dr[d];
			int nCol = col + dc[d];
			// 유효한 칸이라면
			if (isValid(nRow, nCol)) {
				// 방문하고
				board[nRow][nCol] = 'x';
				// 다음에서 끝까지 갈 수 있다면
				if (dfs(nRow, nCol)) {
					return true; // 나도 갈 수 있는 것
				}
			}
		}
		// 빵집 못갔으면 false
		return false;
	}
	// 각 행에서 빵집까지 갈 수 있으면 true, 아니면 false 반환
	static void dfs2(int row, int col) {
		if (col == maxC - 1) { // 빵집 도달시
			cnt+=1;
		}
		// 더 가야 한다면
		for (int d = 0; d < 3; d++) { // 3방탐색
			int nRow = row + dr[d];
			int nCol = col + dc[d];
			// 유효한 칸이라면
			if (isValid(nRow, nCol)) {
				// 방문하고
				board[nRow][nCol] = 'x';
				// 다음에서 끝까지 갈 수 있다면
				dfs2(nRow,nCol);
			}
		}
	}
	
	
	
	/*
	 * 해당 칸이 갈 수 있는지, 유효한지 검사
	 */
	static boolean isValid(int row, int col) {
		if (row < 0 || row >= maxR || board[row][col] == 'x') {
			return false;
		}
		return true;
	}
}
