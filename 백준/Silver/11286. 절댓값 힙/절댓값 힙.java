/*
 * BOJ 11286 절대값 힙
 * 0 이아닌 정수 x를 넣을 수 있다.
 * 0 이 입력되면, 절대 값이 가장 작은 값을 출력하고 배열에서 제거한다.
 * 절대값이 가장 작은 값이 여러개이면 가장 작은 수를 출력한다.
 * 절대값순으로 정렬, 
 * 
 */
import java.util.*;
import java.io.*;
public class Main {
	
	static BufferedReader br;
	static PriorityQueue<abs> absHeap;
	static class abs implements Comparable<abs>{
		
		int value;
		
		abs(int v){
			this.value = v;
		}
		
		@Override
		public int compareTo(abs o) {
			if(Math.abs(value)==Math.abs(o.value)) {
				return Integer.compare(value, o.value);
			}
			return Integer.compare(Math.abs(value),Math.abs(o.value));
		}
		
		@Override
		public String toString() {
			return value+"";
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		absHeap = new PriorityQueue<>();
		int N = Integer.parseInt(br.readLine());
		for(int idx=0; idx<N; idx++) {
			int c = Integer.parseInt(br.readLine());
			if(c!=0) {
				absHeap.add(new abs(c));
				continue;
			}

			System.out.println((absHeap.isEmpty())?0:absHeap.poll());
			
		}
		
		
	}
}