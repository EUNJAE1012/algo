
/*
 * BOJ 5430 AC
 * 정수 배열 연산용 언어 AC에는 두가지 함수 R과 D가있다.
 * R은 배열의 순서를 뒤집고, D는 첫번재 수를 버린다.
 * 배열이 비어있는데 D를 사용하면 에러난다.
 * 함수는 조합해서 사용할 수 있다. RDD는 배열을 뒤집은 다음 처음 두수를버린다.
 * 
 * 초기값과 결과가 주어졌을때 최종 결과를 구하라
 * 
 * 풀이
 * 그냥 Deque를 두고, lazy하게 처리한다.
 * R 이 입력되면 Head의 위치를 바꾸고
 * D 가 입력되면 Head의 값을 pop한다.
 * 
 */
import java.util.*;
import java.io.*;


public class Main {
	
	static class AC{
		int[] arr;
		int head; //1이면 정방향, -1이면 역방향
		int headPtr; //head에서 뽑은 횟수
		int tailPtr; //tail에서 뽑은 횟수
		boolean hasError;
		AC(int size){
			this.arr = new int[size];
			this.head = 1;
			this.headPtr =0;
			this.tailPtr = size-1;
		}
		
		void R() {

			head = head*-1;
		}
		
		void D() {

			if(headPtr>tailPtr) { //초기 들어있던거 이상으로 뽑음
				hasError = true;
				return;
			}
			
			//head가 1이라면 정방향이라면
			if(head==1) {
				//앞에서 버림
				arr[headPtr++] = 0;
			}
			//head가 1이 아니라면 
			else {
				//뒤에서 버림
				arr[tailPtr--] = 0;
			}
		}
		
		void print() {
			if(hasError) {
				System.out.println("error");
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			
			if (head == 1) { // 정방향
		        for (int i = headPtr; i <= tailPtr; i++) {
		            sb.append(arr[i]);
		            if (i != tailPtr) sb.append(',');
		        }
		    } else { // 역방향
		        for (int i = tailPtr; i >= headPtr; i--) {
		            sb.append(arr[i]);
		            if (i != headPtr) sb.append(',');
		        }
		    }
			sb.append(']');
			System.out.println(sb);
			
		}
		
	}
	
	static BufferedReader br;

	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc =0; tc<T; tc++) {
			char[] command = br.readLine().toCharArray();
			int N = Integer.parseInt(br.readLine());
			String arr = br.readLine();
			
			AC cur = new AC(N);
			
			arr = arr.substring(1, arr.length() - 1);
			
			if(arr.length()>0) {
			int[] intArr = Arrays.stream(arr.split(","))
	                  .map(String::trim)
	                  .mapToInt(Integer::parseInt)
	                  .toArray();
			cur.arr = intArr;
			}
			
			for(int cmd = 0; cmd<command.length;cmd++) {
				char c = command[cmd];
				if(c=='D') {
					cur.D();
				}
				else {
					cur.R();
				}
			}
			
			cur.print();
			
		}
		
	}
}
