/*
 * BOJ 27113 잠입
 * 적기지에 침투한다.
 * 적기지는 N*M인 2차원 그리드이고, 기지 입구인 1.1에서 출발해서 N.M까지 이동한다.
 * 레이저 센서가 행과 행 사이의 경계에 설치되어있다.
 * 왼쪽 or 오른쪽으로 레이저를 발사한다.
 * 벽이나 다른 레이저 센서에 닿으면 멈추며, 한경계에 최대2개까지 설치된다.
 * 레이저를 밟으면 실패한다.
 * 그뿐만이 아니다.
 * 1.1에서 출발후 1초뒤에 방범로봇이 입구로 뒤따라 들어온다.
 * 움직이거나 움직이지 않는다. 로봇은 레이저를 밟아도 문제없다.
 * 같은 칸에서 만나면 실패한다.
 * 목표지점인 N,M까지 도달할 수 있는지 출력하라.
 * 
 * 풀이 :  BFS + 시뮬레이션
 * 로봇은 1초뒤부터 매 초 존재 가능 지점이 1칸씩 늘어난다.
 * => 우리는 한순간도 멈출 수 없다. 무조건 우측,혹은 하단으로 이동해야 한다.
 * 그리디하게, 하단이동이 가능하다면 하단, 아니라면 우측으로 이동한다.
 * 가로 이동을 가로막는것은 없다. 최하단 지점에 도달했다면 그것은 이미 성공
 * 
 */
import java.io.*;
import java.util.*;
public class Main {
	static BufferedReader br;
	static int rowSize,colSize;
	static int raserCnt;


	static boolean flag;
	static StringTokenizer st;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());

		
		int x = 1;
		int y = 1;
		for(int row = 1; row<rowSize;row++) {
			st = new StringTokenizer(br.readLine());
			raserCnt = Integer.parseInt(st.nextToken());
			List<Integer> raserCols = new ArrayList<>();
			List<Character> directions = new ArrayList<>();
			for(int raserIdx =0; raserIdx<raserCnt;raserIdx++) {
				raserCols.add(Integer.parseInt(st.nextToken()));
				directions.add(st.nextToken().charAt(0));
				
			}
			//x이상 크면서, 지나갈 수 있는 가장 작은 col 값을 반환  
			if(raserCnt !=0) {
				x = makeRaser(x,raserCols,directions);
				if(x>colSize || x==-1) {
					System.out.println("NO");
					return;
				}
			}

		}

		System.out.println("YES");

		
	}
	
	//x이상 크면서, 지나갈 수 있는 가장 작은 col 값을 반환 
	static int makeRaser(int x,List<Integer> raserCols, List<Character> directions) {

		if(raserCols.size()==1) { //레이저가 하나야
			int raserCol = raserCols.get(0);
			char direction = directions.get(0);
			if(direction =='L') {
				if(raserCol < x) {
					return x;
				}
				else {
					return raserCol+1;
				}
			}
			else {
				if(raserCol <= x) {
					return -1;
				}
				else {
					return x;
				}
			}		
		}
		
		//레이저가 두개
		int raserStart = raserCols.get(0);
		int raserEnd = raserCols.get(1);
		char direction1 = directions.get(0);
		char direction2 = directions.get(1);

		if(direction1 != direction2) { //레이저가 서로 반대 방향을 본다.
			//레이저가 서로 마주보는 경우 >--<
			if(direction1 =='R'){
//				System.out.println("레이저가 마주봐용");
				if(x>=raserStart) {
					return raserEnd+1;
				}
				else {
					return x;
				}
			}
			// -< >- 레이저가 엇갈린 경우
			else {
				
				
				if(x>=raserEnd) { //우측레이저보다 더간경우, 못감
					return -1;
				}
				else if(x<=raserStart) { //좌측레이저보다 왼쪽에 있다면, 좌측레이저+1칸, 이된다면!
					return (raserStart+1<raserEnd)?raserStart+1:-1;
				}
				else { //사이에있으면 그냥 내려오기
					return x;
				}
			}
			
		}
		else { //레이저가 같은 방향을 본다
			if(direction1 =='L') {
				return raserEnd+1;
			}
			else {
				if(x<raserStart) {
					return x;
				}
				else {
					return -1;
				}
			}
		}

	}
}