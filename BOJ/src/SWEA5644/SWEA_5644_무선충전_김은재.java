package SWEA5644;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

/*
 * 문제
 * 10*10 영역의 지도가 주어진다.
 * 무선충전기의 충전범위가 C일대, 거리가 C이하면 무선충전기에 접속할 수있다.
 * 범위내의 충전기가 겹칠 경우, 한쪽을 골라서 쓸 수 있다.
 * 한 충전기에 여러 사람이 들어오면, 충전량을 N빵한다.
 * BC정보, 사용자 궤적이 주어질 때, 모든 사용자 충전량의 최대값을 구하라
 * 
 * 풀이
 * 
 * 
 * 
 * 필요한 로직
 * 
 * 1. 시간에 따라 사용자가 이동하는 로직
 *    다음 시간의 충전량은. 현재 시간의 충전량과 무관하다.
 *    즉 매 시간마다 최대의 충전량을 고르고, 합친다.
 * 
 * 
 * 2. 사용자가 범위내에 있는 AP를 감지하고, 선택하는 로직
 *     충전소가 겹치는 영역을 지날때, 분기를 나눈다.(DFS)
 *     겹치는 영역은, 충전소로부터 4방탐색을 거리만큼 진행해서, 미리 만들어 놓는다.
 * 
 * 
 */
public class SWEA_5644_무선충전_김은재 {
	
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	static int maxTime;
	static int bcCnt;
	static int maxCharge;

	static int dr[] = {0,-1,0,1,0};//정지 , 상 우 하 좌
	static int dc[] = {0,0,1,0,-1};
	static int[] userA;
	static int[] userB;
	static AP[] APArray;
	static List<AP> usableA;
	static List<AP> usableB;
	
	//충전기 클래스
	static class AP {
		int row;
		int col;
		int range;
		int chargeAmount;
//		boolean using;
		
		int returnCharge() {
			return this.chargeAmount;
		}
		
		public AP(int row, int col, int range, int chargeAmount) {
			super();
			this.row = row-1;
			this.col = col-1;
			this.range = range;
			this.chargeAmount = chargeAmount;
		}

//		@Override
//		public String toString() {
//			return  row + "," + col + "  chargeAmount: "+chargeAmount;
//		}
		
		
		
		
	}
	
	
	static int charge() {
		int charge =0;
		if(!usableA.isEmpty()) {
			for(AP apA : usableA) {
				
				int tmp = useAP(apA);
				//AB 둘다 있으면 A,B조합으로 최대 충전량
				for(AP apB :usableB) {
					if(apA.equals(apB)) {
//						System.out.println("이러시면 안되요");
						charge = Math.max(charge, tmp);
						continue;
					}
					int tmpB = useAP(apB);
//					System.out.println("AB 둘 다 접근 A : "+ tmp+" B : "+tmpB );
					charge = Math.max(tmp+tmpB,charge);

				}
				//B가 비었으면 A 최대 충전량이 최대 충전량
				if(usableB.isEmpty()) {
//					System.out.println("A만 접근가능");
					charge = Math.max(tmp, charge);
				}
			}
		}
		//A가 비었으면
		if(usableA.isEmpty()) {
			//B 최대 충전량이 최대 충전량
			for(AP apB : usableB) {
//				System.out.println("B만 접근가능   ");
				charge = Math.max(useAP(apB), charge);
			}
		}

//		for(AP ap : APArray) {
//			ap.using = false;
//		}
		
		return charge;
	}
	
	
	
	//시간에 따라 유저가 움직인다.
	static void move() {
		
		//0시간에도 충전을 할 수 있으면 한다.
		usableA = findUsable(0, 0);
		usableB = findUsable(9, 9);
		
		//0 시간대의 최대 충전량
		maxCharge += charge();
		
		int nARow = 0;
		int nACol = 0;
		int nBRow = 9;
		int nBCol = 9;
		
		for(int time =0;time<maxTime;time++) {
//			System.out.println("time: "+(time+1));
			nARow +=dr[userA[time]];
			nACol +=dc[userA[time]];
			//사용자가 지도밖으로 이동하는 경우는 없다.
			usableA = findUsable(nARow,nACol);
			
			nBRow +=dr[userB[time]];
			nBCol +=+dc[userB[time]];
			usableB = findUsable(nBRow, nBCol);
			
			int tmp = charge();
			maxCharge += tmp;
//			System.out.println(tmp+" 추가 :"+maxCharge);
		}
		
	}
	

	
	
	static int useAP(AP ap) {
		int chargeAmonut = ap.returnCharge();
//		ap.using = true;
		return chargeAmonut;
	}
	
	
	static List<AP> findUsable(int row, int col) {
		List<AP> usable = new ArrayList<>();
		
		for(AP ap : APArray) {
			if(distance(row,col,ap.row,ap.col)<=ap.range) {
				usable.add(ap);

			}
		}
		
//		System.out.printf("%d.%d에서 접근 가능한 수 :  ",row,col);
//		System.out.print(usable.size()+"     ");
//		System.out.println(usable);
		return usable;
		
	}
	
  	static void init() throws Exception{
		st = new StringTokenizer(br.readLine());
		maxTime = Integer.parseInt(st.nextToken());
		bcCnt = Integer.parseInt(st.nextToken());
		userA = new int[maxTime];
		userB = new int[maxTime];
		APArray = new AP[bcCnt];
		usableA = new ArrayList<>();
		usableB = new ArrayList<>();
		maxCharge = 0;

		//1번유저 경로 0.0 스타트
		st = new StringTokenizer(br.readLine());
		for(int i =0; i<maxTime; i++) {
			userA[i] = Integer.parseInt(st.nextToken());
		}
		
		//2번유저 경로 9.9 스타트
		st = new StringTokenizer(br.readLine());
		for(int i =0; i<maxTime; i++) {
			userB[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int idx =0; idx<bcCnt;idx++) {
			st = new StringTokenizer(br.readLine());
			int col = Integer.parseInt(st.nextToken());
			int row = Integer.parseInt(st.nextToken());
			int range = Integer.parseInt(st.nextToken());
			int chargeAmount = Integer.parseInt(st.nextToken());
			APArray[idx] = new AP(row, col, range, chargeAmount);
			
			
		}
	}
	
	
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc<=T;tc++) {
			init();
			
			move();
			
			sb.append("#").append(tc).append(" ").append(maxCharge).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	
	
	//거리
	static int distance(int r1,int c1, int r2, int c2) {
		return (Math.abs(c1-c2) + Math.abs(r1-r2));
		
	}
}




