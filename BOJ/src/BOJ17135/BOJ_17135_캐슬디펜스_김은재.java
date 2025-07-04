package BOJ17135;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
/*
 * 문제
 * NxN격자판에 각 칸 마다 최대 하나의 적이 있다.
 * 격자판 바로 아래, N+1행에는 모든칸에 성이 있다.
 * 성에 궁수 3명을 배치한다. 턴마다 궁수는 적 하나를 공격한다.
 * 거리가 D이하이며, 가장 가깝고 왼쪽에 있는 적을 공격한다.
 * 거리는 |r1-r2| + |c1-c2|이다.
 * 한 적이 같은 궁수에게 공격당할 수 있다. 공격받으면 죽는다.
 * 궁수가 떄리면, 적이 한칸 밑으로 이동하는 턴이다. 
 * 궁수가 제거할 수 있는 최대 적의 수는 ?
 * 
 * 풀이
 * 필요한 로직
 * 1. 궁수 배치 로직
 * 궁수는 0~N-1번 인덱스 사이에 3곳에 배치된다.
 * = 조합으로 구현한다.
 * 
 * 2. 궁수 공격 로직
 * 거리가 짧은 것 부터 공격하므로, BFS 방식으로 거리순으로 (1~D까지) 탐색한다.
 * 이때 왼쪽부터, col인덱스가 작은 것부터 탐색하도록 한다.
 * 각각의 궁수가 몬스터를 공격하면, 몬스터가 죽는다. cnt++
 * 
 * 3. 몬스터 이동 로직
 * 궁수가 공격한 후에, 마지막 행에 있는 모든 몬스터를 삭제시키고,
 * 마지막 행을 제외한 모든 몬스터의 row ++
 * 
 * 4. 시간 경과 로직
 * 모든 몬스터가 사라질 때 까지 2번과 3번을 반복 실행하며 시간을 보내고, cnt를 출력한다.
 * 
 * 
 * 
 */
public class BOJ_17135_캐슬디펜스_김은재 {
	
	static BufferedReader br;
	static StringTokenizer st;
	
	//격자크기, 화살 사거리, 처치 횟수
	static int rowSize, colSize, arrowDistance,cnt;
	
	//게임보드
	static int[][] board;
	
	//궁수들의 좌표
	static int[] archers;
	
	//궁수들의 좌표들 저장할 리스트
	static List<int[]> archersList;
	
	//몬스터 초기 위치 저장 -> 배열 초기화
	static List<int[]> Monsters;
	
	static int[] dr = {0,-1,0}; //좌상우 3방탐색
	static int[] dc = {-1,0,1};
	public static void main(String[] args) throws Exception {

		//init
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		archersList = new ArrayList<>();
		archers = new int[3];
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		arrowDistance = Integer.parseInt(st.nextToken());
		Monsters = new ArrayList<int[]>();
		board = new int[rowSize][colSize];
		
		//입력
		for (int row = 0; row < rowSize; row++) {
			st = new StringTokenizer(br.readLine());
			for (int col = 0; col < colSize; col++) {
				board[row][col] = Integer.parseInt(st.nextToken());
				if(board[row][col]==1) {
					Monsters.add(new int[]{row,col});
				}
			}
		}

		int maxCnt =0;
		// 궁수 위치 조합 만들기
		makeArcher(0, 0);
		// 각 위치별로 쏴보기
		for (int i = 0; i < archersList.size(); i++) {
			//궁수 조합
			archers = archersList.get(i);
			//보드 원상복구
			repairBoard();
			
//			System.out.println(Arrays.toString(archers));
			cnt =0;

			while(isMonster()) {
				// 모든 몬스터가 사라질때까지, 게임을 반복
				shoot();
				move();
			}
			maxCnt = Math.max(cnt, maxCnt);
		}
		
		System.out.println(maxCnt);
	}

	//보드 원상복구
	static void repairBoard() {
		for(int i =0; i<Monsters.size();i++ ) {
			int tmp[] = Monsters.get(i);
			int r = tmp[0];
			int c = tmp[1];
			board[r][c] = 1;
		}
	}
	// 궁수의 위치는, 0~colSize-1까지에서 3개를 뽑는 조합이다.
	static void makeArcher(int elementIndex, int selectedIndex) {
		// 3명다뽑았으면 리스트에 저장
		if (selectedIndex == 3) {
//			System.out.println(Arrays.toString(archers)+"팀 생성");
			archersList.add(archers.clone());

			return;
		}
		if (elementIndex == colSize) {
			return;
		}
		// 다 안뽑았으면 더뽑
		// 뽑고 넘기기
		archers[selectedIndex] = elementIndex;
		makeArcher(elementIndex + 1, selectedIndex + 1);
		// 안뽑고 넘기기
		archers[selectedIndex] = -1;
		makeArcher(elementIndex + 1, selectedIndex);

	}

	// 궁수들이 활을쏜다.
	static void shoot() {
		//궁수들이 공격할 좌표목록
		Set<int[]> set = new HashSet<>();
		
		for (int archerIdx = 0; archerIdx < 3; archerIdx++) {
			//궁수의 x좌표
			int archerCol = archers[archerIdx];
			
			boolean[][] visited = new boolean[rowSize][colSize];
			
			//궁수로부터 BFS
			Queue<int[]> queue =  new ArrayDeque<>();
			queue.add(new int[] {rowSize-1,archerCol,1});
			while(!queue.isEmpty()) {
				int[] tmp = queue.poll();

				//범위바깥이면 안본다.
				int distance = tmp[2];
				if(distance>arrowDistance) {
					break;
				}
				//범위 안이면, 현재 좌표부터 본다.
				int currentCol = tmp[1];
				int currentRow = tmp[0];
				
				//몬스터가 있다면 락 온
				if(board[currentRow][currentCol]!=0) {
					set.add(new int[] {currentRow, currentCol});
					break;
				}
				// 없으면, 다음 거리를 좌 상 우 순서로 본다.
				for(int d=0;d<3;d++) {
					int nr = currentRow+dr[d];
					int nc = currentCol+dc[d];
					if(nr>=rowSize||nr<0||nc<0||nc>=colSize||visited[nr][nc]) {
						continue;
					}
					queue.add(new int[] {nr,nc,distance+1});
				}
			}
		}
		
		//중복 공격이 가능하므로, 점수 정산은 한번에 
		for(int tmp[] :  set) {
			int r = tmp[0];
			int c = tmp[1];
			if(board[r][c]==1) {
				cnt+=1;
				board[r][c]=0;
			}
			
		}
//		System.out.println("공격후 cnt : "+cnt);
	}
	
	
	//보드에 몬스터가 남아있는지
	static boolean isMonster() {
		for(int row = 0; row< rowSize;row++) {
			for(int col =0; col<colSize; col++) {
				if(board[row][col] ==1) {
//					System.out.println(row+","+col+" 에 몬스터 있음");
					return true;
				}
			}
		}
		return false;
	}
	
	// 몬스터가 내려온다.
	static void move() {
		// 끝에있는 애들부터 제거
		Arrays.fill(board[rowSize-1],0);
		// 나머지 애들도 1칸씩 이동
		for (int row = rowSize - 2; row >= 0; row--) {
			for (int col = 0; col < colSize; col++) {
				if (board[row][col] == 1) {
					board[row + 1][col] = 1;
					board[row][col] = 0;
				}
			}
		}
	}
}
