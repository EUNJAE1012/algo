package BOJ6987;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 문제
 * 월드컵 조별리그는 같은 조 5명과 1번씩 경기를 치른다.
 * 각 나라의 승무패 결과가 가능한지, 가능하지 않은지 체크
 * 풀이
 * 조당 경기는 15게임이다. = 승 무 패 합이 30이어야 한다. 
 * 각 팀당 게임 수는 5인데, 6까지 입력이 된다고 한다. 왜 이렇게 냈는지 모르겠다.
 * 각 경기마다 팀을 미리 배정해준다. 1경기 A-B 2경기 A-C 3경기 A-D...
 * 각 경기마다 homeTeam이 승,무,패 하는 경우의 dfs를 모두 해본다.
 * 만약 주어진 승무패 횟수를 벗어난다면 백 트래킹
 * 15경기까지 확인했다면, 이상 없음
 * 
 */
public class BOJ_6987_월드컵_김은재 {
	static int[] win,lose,draw;
	static int[][] matchs;
	static int flag;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		//미리 배정된  경기들
		matchs = new int[15][2];
		int idx= 0;
		for(int homeTeam = 0; homeTeam<=4;homeTeam++) {
			for(int awayTeam =homeTeam+1; awayTeam<=5;awayTeam++) {
				//idx번 매치의 홈팀은 matches[idx][0]에, away팀은 matches[idx][1]에 저장
				matchs[idx][0] = homeTeam;
				matchs[idx][1] = awayTeam;
				idx++;
			}
		}
		//조별 경기
		for(int test_case = 1; test_case<=4;test_case++) {
			
			int totalWin =0;
			int totalDraw = 0;
			int totalLose = 0;
			
			flag =0;
			boolean error=false;
			
			win = new int[6];
			draw = new int[6];
			lose = new int[6];
			
			st = new StringTokenizer(br.readLine());
			int tmp;
			for(idx =0; idx<6;idx++) {
				
				win[idx] =Integer.parseInt(st.nextToken());
				totalWin += win[idx];
				
				draw[idx] = Integer.parseInt(st.nextToken());
				totalDraw += draw[idx];
				
				lose[idx] = Integer.parseInt(st.nextToken());
				totalLose += lose[idx];
				
				//이상현상- 한팀이 6경기 이상한 경우
				if(win[idx] + lose[idx] + draw[idx]!=5) {
					error = true;
					break;
				}
			}
			//이상현상, 승패 짝 안맞음, 무승부가 홀수임
			if((totalWin!=totalLose) || (totalDraw%2==1) || (totalWin+totalLose+totalDraw)!=30) {
				error =true;
			}
			
			//이상현상을 찾지 못하면, 경기를 해본다.
			if(!error) {
				dfs(0);
			}

			sb.append(flag+" ");
			
			
		}
		System.out.println(sb.toString());
	}
	
	
	static void dfs(int game) {
		if(flag==1) return;
		if(game==15) { //15게임을 다 본 경우
			flag =1; //가능한 경우다.
			return;
		}
		//게임을 더 해야 한다면
		//홈팀과 어웨이팀의 인덱스 꺼내오기
		int homeTeam = matchs[game][0];
		int awayTeam = matchs[game][1];
		// 승/ 패
		if(win[homeTeam]>0 && lose[awayTeam]>0) {//성립한다면
			win[homeTeam]-=1;
			lose[awayTeam]-=1;
			dfs(game+1); //다음경기로
			win[homeTeam]+=1;
			lose[awayTeam]+=1;
		}

		// 패/ 승
		if(lose[homeTeam]>0 && win[awayTeam]>0) {//성립한다면
			lose[homeTeam]-=1;
			win[awayTeam]-=1;
			dfs(game+1);
			lose[homeTeam]+=1;
			win[awayTeam]+=1;
		}
		
		// 무/ 무
		if(draw[homeTeam]>0 && draw[awayTeam]>0) {//성립한다면
			draw[homeTeam]-=1;
			draw[awayTeam]-=1;
			dfs(game+1);
			draw[homeTeam]+=1;
			draw[awayTeam]+=1;
		}
	}
}
