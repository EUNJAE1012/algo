import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * BOJ 2357 : 최솟값과 최댓값
 * N개의 정수 수열 Xn 에서, [Xa , Xb] 중에서 제일 작은 수와 제일 큰 수를 찾아라.
 * 
 * 풀이
 *  세그먼트 트리
 * 
 * 
 */
public class Main {
	static int N,M,size,startIdx;
	static int[] arr;
	static int[] minTree,maxTree;
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		for(size =1;size<N*2;size*=2) {};
		startIdx =size>>1;
		
		minTree = new int[size];
		maxTree = new int[size];
		Arrays.fill(maxTree, 0);
		Arrays.fill(minTree, Integer.MAX_VALUE);
		for(int idx = 0; idx<N;idx++) {
			arr[idx] = Integer.parseInt(br.readLine());
			minTree[idx+startIdx] = arr[idx];
			maxTree[idx+startIdx] = arr[idx];
		}
		
		for(int idx = startIdx;idx>1;idx>>=1) {
			for(int col = idx; col<idx*2;col+=2) {
				minTree[col>>1] = Math.min(minTree[col], minTree[col+1]);
				maxTree[col>>1] = Math.max(maxTree[col], maxTree[col+1]);
			}
		}
			
		
		for(int query = 0; query<M;query++) {
			st = new StringTokenizer(br.readLine());
			int from= Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			sb.append(searchMin(from,to)).append(" ").append(searchMax(from,to)).append("\n");
		}
		System.out.println(sb);
	}
	
	static int searchMin(int from, int to) {
		int left = startIdx+from-1;
		int right = startIdx+to-1;
		int minValue = Integer.MAX_VALUE;
		while(left<=right) {
//			System.out.println("left: "+left+" v "+minTree[left]+" right : "+right+" v "+ minTree[right]);
			if((left & 1) == 1) {
				minValue = Math.min(minValue, minTree[left]);
				left++;
			}
			if((right & 1) == 0) {
				minValue = Math.min(minValue, minTree[right]);
				right--;
			}
			left>>=1;
			right>>=1;
		}
		return minValue;
	}
	
	
	static int searchMax(int from, int to) {
		int left = startIdx+from-1;
		int right = startIdx+to-1;
		int maxValue = Integer.MIN_VALUE;
		while(left<=right) {
//			System.out.println("left: "+left+" v "+minTree[left]+" right : "+right+" v "+ minTree[right]);
			if((left & 1) == 1) {
				maxValue = Math.max(maxValue, maxTree[left]);
				left++;
			}
			if((right & 1) == 0) {
				maxValue = Math.max(maxValue, maxTree[right]);
				right--;
			}
			left>>=1;
			right>>=1;
		}
		return maxValue;
	}
	
}