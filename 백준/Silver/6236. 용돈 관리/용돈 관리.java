/*
 * BOJ 6236 : 용돈관리
 * N일동안 사용할 금액을 정했다.
 * M번만 통장에서 인출한다. K원을 인출해서 하루 생활비가 되면, 그대로쓰고
 * 모자라면 남은 금액은 통장에 넣고 다시 K원을 인출한다.
 * M번을 맞추기 위해 쓸 돈보다 남아도 다 집어넣고 다시 K원을 인출한다.
 * (N<=10만, M<=N K<=1만)
 * K의 최솟값은? 
 * @@의 최솟값 = 바이너리서치
 * 일단, K는 하루치 예산의 최대값보다는 커야한다. 돈을 인출하기 전에 항상 남은 돈을 집어 넣기 때문.(최대지갑금액 K)
 *  M=N일때는 K가 하루치예산의 최대값과 동일하다.
 *  M<N이 라면, 연속되는 값이 작은 부분들을 찾아서, 인출 횟수를 줄여야한다.
 *   
 * 
 */
import java.io.*;
import java.util.*;

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int days,withdrawCnt,minMoney;
	static int[] budget;
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		days = Integer.parseInt(st.nextToken());
		withdrawCnt = Integer.parseInt(st.nextToken());
		
		budget = new int[days+1];
		minMoney=0;
		for(int day=1; day<=days;day++) {
			int money = Integer.parseInt(br.readLine());
			budget[day] = money;
			minMoney = Math.max(minMoney, money);
		}	
		System.out.println(binarySearch());
	}
	
	static int binarySearch() {
		int l=minMoney;
		int r=1_000_000_000; //만약m=1일때, 하루에 N10만*하루1만 =10억을 감당해야함
		int m= (l+r)>>1;
		while(l<=r) {
			m = (l+r)>>1;
			//지금 예산으로 생존이 되면
			if(simulate(m)) {
				//예산을 줄이세요
				r= m-1;
			}
			//생존이 안되면 예산을 늘려요
			else {
				l = m+1;
			}
		}
		return l;
	}
	
	
	//money를 withdrawCnt만큼 인출하면, 생존이 될까?
	static boolean simulate(int money) {
		
		int 잔고 = 0;
		int withdraw = 0;
		for(int day = 1;day<=days; day++) {
			//돈이 모자라면 무조건 인출
			if(잔고<budget[day]) {
				//더이상 인출할 수 없다면 불가 판정
				if(withdraw == withdrawCnt) return false;
				잔고= money;
				withdraw++;
				잔고-= budget[day];
				continue;
			}
			//돈이 모자라지 않다면, 그냥 차감
			잔고-=budget[day];
		}
		
		
		return true;
	}
}
