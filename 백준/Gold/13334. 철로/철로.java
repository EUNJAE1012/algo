
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


/* BOJ13334 철로
 * n명의 사람이 집과 사무실을 통근한다.
 * 모든 집과 사무실은 수평선 상의 다른 점에 위치한다.
 * 같은 칸에 있을 수도 있다.
 * 철로 상의 두 점을 연결하는 길이 d의 철로를 설치하여 기차를 운행할 때
 * 가장 많은 사람의 집과 사무실이 철로위에 놓이도록 할때 사람의 수를 구하라
 * 
 * 
 * 투포인터? 이분탐색? 슬라이딩윈도우? dp?
 * 
 * 집-사무실이 주어지는데, 만약 거리가 d보다 크다면 고려할필요 없음
 * d보다 작거나 같다면, 출발점과 거리를 저장
 *  start[i] i번째 사람의 시작점
 *  end[i] i번째 사람의 종료점
 *  possible[i] i번째 집->사무실 가능여부 초기값 모두 false
 * 
 * 1. 출발점이 작은 순서대로 정렬
 * 2. for(cursor 첫 출발점; -> cursor 마지막 출발점)
 *  2.1    for(i=cursor;i<=n;i++)
 *  2.1.1   if(start[i] <= cursor)
 *  2.1.1.1 if(possible[i]) continue 이전에 이미 가능한 상태였기 때문에 
 *  2.1.1.2 else( ) 
 *  2.1.1.2.1 if(end[i]<=cursor+d) possible[i]=true, cnt++
 * --이방법은 시간초과입니다~  
 *
 * 1. 리스트를 끝점이 작은 순서대로 정렬 
 * 2. 하나씩 뽑아서 구간을 결정 [x-d,x]
 * 2.1 시작점이 x-d보다 작은 애들은 다 쫒아냄
 * 2.2. 뽑은 애를 힙에 넣고
 * 2.3. 최댓값 갱신 (힙의크기)
 * 
 * 
 * 
 */

public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int N,maxCnt,d;
	static int[] start,end;
	static boolean[] possible;
	static List<StartPoint> startPoints;
	static PriorityQueue<Integer> queue;
	
	static class StartPoint implements Comparable<StartPoint>{
		int start;
		int end;
		int distance;
		
		public StartPoint(int home, int work) {
			if(home>work) {
				this.start = work;
				this.end = home;
			}
			else {
				this.start = home;
				this.end = work;
			}
			this.distance = this.end-this.start;
		}
		
		@Override
		public int compareTo(StartPoint o) {
			return Integer.compare(this.end, o.end);
			}
		
		@Override
			public String toString() {
				return start+"->"+end;
			}
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		start = new int[N+1];
		end = new int[N+1];
		startPoints = new ArrayList<Main.StartPoint>();
		queue = new PriorityQueue<Integer>();
		
		for(int i = 1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			start[i] = b;
			end[i] = a;

		}
		d = Integer.parseInt(br.readLine());
		
		for(int i = 1;i<=N;i++) {
			StartPoint sp = new StartPoint(start[i], end[i]);
			if(sp.distance<=d) {
				startPoints.add(sp);
			}
		}
		Collections.sort(startPoints);

		for(StartPoint sp : startPoints) { //queue에 들어가는 것은 시작점
			int upper = sp.end-d;
			while(!queue.isEmpty() && queue.peek()<upper) {
				queue.poll();
			}
			queue.add(sp.start);
			maxCnt = Math.max(maxCnt, queue.size());
		}
		
		
		
		System.out.println(maxCnt);
	}
	

}
