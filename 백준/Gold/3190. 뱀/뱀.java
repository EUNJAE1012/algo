
/*
 * BOJ 3190 뱀
 * 지렁이게임 만든다. 뱀이 사과를 먹으면 1칸 길어진다.
 * 뱀이 벽이나 자기 몸에 박으면 죽는다.
 * N*N 보드, 0,0,r 길이 1 에서 시작한다.
 * 1.몸을 늘려 머리를 다음칸에 옮긴다. 
 * 2.벽이나 몸에 박으면 죽는다.
 * 3.1사과가 있으면 먹고, 꼬리가 유지된다.
 * 3.2사과가 없으면 꼬리가 사라진다.
 * 뱀의 이동경로가 주어질때 게임이 끝나는 시간을 구하여라
 * 
 * 풀이 
 * 그냥 시뮬레이션 돌려도 충분할 것 같지만, lazy하게 처리해보자.
 * cmd가 들어오면, 
 * 
 */
import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int[] dr = {0,1,0,-1};//우하좌상
	static int[] dc = {1,0,-1,0};
	static int mapSize,appleCnt,cmdCnt;
	static int[][] map;

	static class Snake{
		int row;
		int col;
		int len;
		int direction; //우하좌상
		int deathTime;
		int recordedTime;
		ArrayList<int[]> recording;
		
		Snake(){
			this.row = 0;
			this.col = 0;
			this.len = 0;
			this.direction = 0;
			this.deathTime = 0;
			this.recordedTime = 0;
			this.recording = new ArrayList<>();
			
		}
		
		void turn(char cmd) {
			if(cmd=='D') { //우회전
				//0>1>2>3
				direction = (direction+1)%4;
			}
			else { //좌회전
				//0->3->2->1
				direction = (direction+3)%4;
			}
		}
		//모든 명령이 끝난후, 죽을때까지 시간 계산
		void goAhead(int startTime) {
			int endTime = startTime;
			boolean die = false;
			
			
			ArrayDeque<int[]> bodyDeque = new ArrayDeque<>();
			for(int i=0; i<len; i++) {
			    int curTime = recordedTime -2-i; // 내 몸자취 인덱스 계산
			    if(curTime < 0 || curTime >= recording.size()) break; // 범위 체크
			    int[] ago = recording.get(curTime);
			    bodyDeque.add(ago);
			}
			
			
			while(row>=0 && col>=0 && row<mapSize && col<mapSize && !die) {
				endTime ++;
				row = row + dr[direction];
				col = col + dc[direction];
				
				for(int[] idx : bodyDeque) {
					if(idx[0]==row && idx[1]==col) {
						deathTime = endTime;
						return;
					}
				}
				bodyDeque.pollLast();
			}
			this.deathTime = endTime;
		}
		
		//lazy하게 처리-> 해당 시간까지 움직임을 몰아서 함
		boolean action(int time, char cmd) {
			int moveCnt = time - recordedTime;
			int apple = 0;
//			System.out.println(String.format("방향 : %d 위치 : (%d,%d) 길이 : %d 시간 : %d", direction,row,col,len,recordedTime));
			
//			System.out.println(time+","+cmd);
			
			ArrayDeque<int[]> bodyDeque = new ArrayDeque<>();
			for(int i=0; i<len; i++) {
			    int curTime = recordedTime -2-i; // 내 몸자취 인덱스 계산
			    if(curTime < 0 || curTime >= recording.size()) break; // 범위 체크
			    int[] ago = recording.get(curTime);
			    bodyDeque.add(ago);
			}
//			(bodyDeque).forEach((a)->System.out.println(Arrays.toString(a)));
			
			//벽검사 + 몸검사
			// 내 len만큼 머리가 지나온 길에서 꺼내온다 = 내 몸의 좌표들
			int nr=row,nc=col;
			for(int moveIdx = 1; moveIdx<=moveCnt;moveIdx++) {
				nr = row + dr[direction] * moveIdx;
				nc = col + dc[direction] * moveIdx;
				
				//벽에 박아서 죽었는가?
				if(nr<0 || nc<0 || nr>=mapSize|| nc>=mapSize) {
					deathTime = recordedTime + moveIdx;
//					System.out.println("벽사망"+nr+","+nc);
					return true;
				}
				if(map[nr][nc]==1) {
					apple++;
					map[nr][nc] =0;
				}
//				System.out.println((recordedTime+moveIdx)+"시간의 몸의 좌표 :");
//				for(Iterator<int[]> it = bodyDeque.iterator(); it.hasNext();) {
//					int[] idx = it.next();
//					System.out.println(Arrays.toString(idx));
//				}
				//자신의 몸에 박아서 죽었는가?
				for(int[] idx : bodyDeque) {
					if(idx[0]==nr && idx[1]==nc) {
						deathTime = recordedTime + moveIdx;
//						System.out.println("몸사망"+nr+","+nc);
						return true;
					}
				}
				
				//아니라면, 발자국 갱신
				bodyDeque.pollLast();
				recording.add(new int[] {nr,nc});
			}
			
			
			//안죽었다면, 정보갱신
			turn(cmd); //방향
			recordedTime = time; //시간
			len += apple; //길이
			row = nr; //좌표
			col = nc; 
//			System.out.println(String.format("방향 : %d 위치 : (%d,%d) 길이 : %d 시간 : %d", direction,row,col,len,recordedTime));
			return false;
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		mapSize = Integer.parseInt(br.readLine());
		appleCnt = Integer.parseInt(br.readLine());
		map = new int[mapSize][mapSize];
		for(int idx = 0; idx<appleCnt;idx++) {
			st = new StringTokenizer(br.readLine());
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			map[row-1][col-1] = 1; //사과표시 = 1
		}
		
		Snake snake = new Snake();
		boolean die = false;
		int time = 0;
		cmdCnt = Integer.parseInt(br.readLine());
		for(int idx =0 ; idx <cmdCnt;idx++) {
			st = new StringTokenizer(br.readLine());
			time = Integer.parseInt(st.nextToken());
			char cmd = st.nextToken().charAt(0);
			
			die = snake.action(time,cmd);
			if(die) {
				break;
			}
		}
		
		if(!die) {
			snake.goAhead(time);
		}
		
		System.out.println(snake.deathTime);
	}
	
}
