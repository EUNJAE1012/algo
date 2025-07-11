
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * BOJ 14725 : 개미굴
 * tree형태의 개미굴의 구조를 알아보자.
 * root -> leaf 까지 값을 하나씩 알려준다.
 * 어떤 구조인지 종합해서 출력해라
 * 
 * 층은 최대 15개, 최대 리프노드의 수는 1000개
 * 
 * 자료형 각 방 : 
 * 방{
 * 방이름, 다음방목록}
 * 
 * Map<층,List<방이름>>
 * 
 * 
 * 
 */


public class Main {
	static BufferedReader br;
	static StringTokenizer st;
	static int N;
	static Set<room> map;
	static room root;
	
	static class room implements Comparable<room>{
		String name;
		Map<String,room> nextList;
		int depth;
		room(String name,  int depth){
			this.name = name;
			this.depth = depth;
			this.nextList = new HashMap<>();
		}
		@Override
		public int compareTo(room o) {
			return this.name.compareTo(o.name);
		}
		@Override
			public String toString() {
				return name+":"+nextList.toString();
			}
		String archi() {
				StringBuilder sb = new StringBuilder();
				for(int d = 0; d<depth;d++) {
					sb.append("--");
				}
				if(name!=null) {
					
				
				sb.append(name).append("\n");
				}
				if(nextList.size()!=0) {
					List<room> n = new ArrayList<>(nextList.values());
					Collections.sort(n);
					for(room tmp : n) {
						sb.append(tmp.archi());
					}
				}
				
				return sb.toString();
			}
	}
	
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new HashSet<>();
		root = new room(null,0);
		for(int robot = 0; robot<N; robot++) {
			st = new StringTokenizer(br.readLine());
			int depth = Integer.parseInt(st.nextToken());
			room before = root;
			for(int d = 0; d<depth;d++) {
				String name = st.nextToken();
				if(before.nextList.containsKey(name)) {
					room next = before.nextList.get(name);
					before = next;
				}else {
					room next = new room(name, d);
					before.nextList.put(name, next);
					before = next;
				}
			}
		}
		System.out.println(root.archi());
	}
	
}
