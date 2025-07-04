package DAY2;


/*
 * 문제
 * NxN 도시에 집, 치킨집, 빈칸이 있다. (1-indeX)
 * 집의 치킨 거리= 집과 가장 가까운 치킨집까지의 거리 (|r1-r2| +|c1-c2|)
 * 도시의 치킨 거리 = 모든 집의 치킨 거리의 합
 * M개의 치킨집만 남기고 폐점 시킬 것이다. ㅠㅠ
 * 치킨 거리의 최소 값을 구하라.
 * 
 * 풀이
 * 1. 집-치킨집 , 즉 치킨 거리를 간선으로 나타낸다.
 * 2. 간선을 오름차순으로, 치킨거리 작은 순으로 정렬한다.
 * 3. 치킨집 M개를 선택하고, 나머지를 망하게 한다 (조합)
 * 	3.1 간선 리스트를 순회하며, 망한 치킨집 && 이미 방문한 집은 거르고 고른다.
 * 	3.2 모든 집을 방문할때까지 4번을 반복
 * 	3.3 모든 집을 방문한 뒤, 그때의 치킨거리를 비교하며 최솟값을 구한다.
 * 
 * 
 *  
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

import java.util.Collections;

public class BOJ_15686_치킨배달_김은재 {

	static BufferedReader br;
	static StringTokenizer st;
	static int chickenMax, citySize, minCityChickenDistance;
	static boolean[] visitedHouse,openedChicken;
	static List<Chicken> chickenList;
	static List<House> houseList;
	static List<Edge> edgeList;
	
	static class Edge implements Comparable<Edge>{
		int chicken;
		int house;
		int weight;
		
		public Edge(int chicken, int house, int weight) {
			this.chicken = chicken;
			this.house = house;
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge [chicken=" + chicken + ", house=" + house + ", weight=" + weight + "]";
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.weight, o.weight);
		}
		
	}
	
	static class Chicken{
		int row;
		int col;
		public Chicken(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		int getChickenDistance(House house) {
			return Math.abs(this.row-house.row)+Math.abs(this.col-house.col);
		}
		
	}
	
	static class House{
		int row;
		int col;
		public House(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
	}
	
	public static void main(String[] args) throws Exception{

		init();
		
		//모든 치킨집에 대해 -> 모든 집에 대한 치킨 거리를 구한다.
		for(int chickenIdx =0 ; chickenIdx < chickenList.size();chickenIdx++) {
			Chicken chicken = chickenList.get(chickenIdx);
			for(int  houseIdx=0; houseIdx< houseList.size();houseIdx++) {
				edgeList.add(new Edge(chickenIdx, houseIdx, chicken.getChickenDistance(houseList.get(houseIdx))));
			}
		}
		//오름차순 정렬
		Collections.sort(edgeList);
		
		
		comb(0,0);

		System.out.println(minCityChickenDistance);
		
	}
	
	// 조합, 치킨집 chickenMax개 만큼 선택 -> 도시의 치킨거리
	static void comb(int selectedCnt,int elementIdx) {
		if(selectedCnt == chickenMax) { //기저조건- 치킨집 다고름

			int total=0; //도시의 치킨 거리
			int houseCnt=0; //최소 거리를 찾은 집 수
			for(int edgeIdx=0; edgeIdx<edgeList.size();edgeIdx++) {
				if(houseCnt==houseList.size()) break; //집 다봤으면 철수
				Edge curEdge  = edgeList.get(edgeIdx);
				//이미 고른집 or 망한 치킨집이면 안보고 다음 간선
				if(visitedHouse[curEdge.house]||!openedChicken[curEdge.chicken]) continue; 

				houseCnt++;
				visitedHouse[curEdge.house]= true;
				total += curEdge.weight;
			}
			visitedHouse = new boolean[houseList.size()];
			minCityChickenDistance=Math.min(minCityChickenDistance, total);
			return;
		}
		if(elementIdx == chickenList.size()) { //기저조건2- 모든치킨집 다봄
			return;
		}
		//이번 치킨집을 폐업시키지 않거나
		openedChicken[elementIdx] = true;
		comb(selectedCnt+1,elementIdx+1);
		
		//폐업시키거나
		openedChicken[elementIdx] = false;
		comb(selectedCnt,elementIdx+1);
		
		
	}
	
	
	static void init() throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		citySize = Integer.parseInt(st.nextToken());
		chickenMax = Integer.parseInt(st.nextToken());
		minCityChickenDistance = Integer.MAX_VALUE;

		chickenList = new ArrayList<>();
		houseList = new ArrayList<>();
		edgeList = new ArrayList<>();
		
		for(int row = 1; row<=citySize;row++) {
			st = new StringTokenizer(br.readLine());
			for(int col=1; col<=citySize;col++) {
				int c = Integer.parseInt(st.nextToken());
				if(c==1) {
					houseList.add(new House(row, col));
					continue;
				}
				if(c==2) {
					chickenList.add(new Chicken(row, col));
				}
			}
		}
		visitedHouse = new boolean[houseList.size()];
		openedChicken = new boolean[chickenList.size()];
	}
}
