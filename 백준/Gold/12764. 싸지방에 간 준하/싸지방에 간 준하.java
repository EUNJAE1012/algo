
/*
 * BOJ 12764 싸지방에간 준하
 * 싸지방에 컴퓨터가 모자라다. 증설한다.
 * 컴퓨터 사용률에 따라 다른 성능의 컴퓨터를 설치한다.
 * 컴퓨터는 1번부터 순서대로 번호가 있다. 입장시 빈 자리중 가장 작은 자리에 앉는다.
 * 모두가 기다리지 않고 바로 쓸 수 있는 컴퓨터의 최소 갯수와, 사용자 수를 구하라
 * 
 * 입력 :
 * 사람의 수 N, 
 * 각 줄마다 이용 시작 시간 P, 종료시간 Q
 * 출력 :
 * 필요한 컴퓨터 수,
 * 각 자리마다 사용한 사람 수
 * 
 * 풀이 :
 * 사람은 입장시간 빠른순으로 정렬한다.
 * 자리는 종료시간 빠른순으로 pq두고, pq가 빌때까지 못넣었으면 새로 넣는다.
 * 넣을 수 있으면 종료시간을 뒤로 연장하고 map ++
 * 이렇게하니까, 빈자리중 가장 작은 번호가 아닌, 가장 빨리 비는 자리에 간다.
 * 매번 빈자리 목록을 갱신하고, 번호순으로 정렬되는 빈자리 pq을 만들어서 꺼내서 넣는다.
 * 
 * 
 * 
 */

import java.util.*;
import java.io.*;

public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N;
	static PriorityQueue<com> pq;
	static PriorityQueue<com> emptySeats;
	static List<com> list;
	static Map<Integer,Integer> map;
	
	static class com implements Comparable<com>{
		
		int in;
		int out;
		int idx;
		
		com(int in, int out){
			this.in = in;
			this.out = out;
			this.idx =0;
		}
		
		@Override
		public int compareTo(com o) {
			return Integer.compare(out, o.out);
		}
		
		@Override
			public String toString() {
				return "in"+in+" out"+out;
			}
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new HashMap<>();
		pq = new PriorityQueue<>();
		emptySeats = new PriorityQueue<>((a,b)->(Integer.compare(a.idx,b.idx)));
		list = new ArrayList<>();
		
		for(int idx =0 ;idx<N; idx++) {
			st = new StringTokenizer(br.readLine());
			int in = Integer.parseInt(st.nextToken());
			int out = Integer.parseInt(st.nextToken());
			com cur = new com(in,out);
			list.add(cur);
		}
		Collections.sort(list,(a,b)->(Integer.compare(a.in,b.in)));
		
		int id = 0; //좌석 번호
		for(com cur : list) {

			int currentTime = cur.in;

			
			//현재시간의 빈자리들 모두 확인
			while(!pq.isEmpty()) {
				com seat = pq.poll();
				if(seat.out <= currentTime) {
					emptySeats.add(seat);
				}
				else {
					pq.add(seat);
					break;
				}
			}
//			System.out.println("시간"+currentTime);
//			System.out.println("사용중자리"+pq);
//			System.out.println("빈자리"+emptySeats);
//			System.out.println(map);
			//재활용할 자리가 있다면
			if(!emptySeats.isEmpty()) {
				com recycle = emptySeats.poll();
				recycle.out = cur.out;
				map.put(recycle.idx, map.get(recycle.idx)+1);
				//다시 사용중 자리로
				pq.add(recycle);
				continue;
			}

			//자리 재활용 못했으면 새로 씀
			map.put(id,1);
			cur.idx = id++;
			pq.add(cur);

		}

		System.out.println(map.size());
		StringBuilder sb = new StringBuilder();
		for(int i =0 ;i<id;i++) {
			sb.append(map.get(i)).append(" ");
		}
		System.out.println(sb);
		
	}
}
