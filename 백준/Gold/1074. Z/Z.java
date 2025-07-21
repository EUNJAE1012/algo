import java.util.Scanner;

/*
 * 문제 : boj 1074 Z
 * 2^N x 2^N 배열을 Z모양으로 탐색한다. 왼위->오위->왼아->오아 순서대로 방문한다.
 * N이 주어질때, r,c칸을 몇번째로 방문하는지 출력하라
 * 풀이
 * 분할정복을 시도한다. 
 * 원본 배열에서, 4등분하여 1->2->3->4 사분면 순으로 방문한다.
 * 이때 방문한 사분면에서 없는것이 자명하다면, 사분면의 크기만큼 cnt++하고 보지 않는다.
 * 존재할 가능성이 있는 사분면에 도달하면, 해당 사분면에서 몇번째인지 확인하여 cnt에 ++한다
 * 
 * 
 */
public class Main {
	static int c, r, size;
	static boolean flag;
	static long cnt;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		size = sc.nextInt();
		r = sc.nextInt();
		c = sc.nextInt();
		cnt = 0;
		flag = false;
		Z(0, 0, size);
		System.out.println(cnt);
	}

	static void Z(int row, int col, int N) {
//		System.out.printf("%d   %d   size =%d\n", row, col, N);
		if (flag) {
			return;
		}
		int pow2n = pow(2, N);
		// 안봐도 되면 결과만 요약해서,
		if (col + pow2n - 1 < c || row + pow2n - 1 < r) {
			cnt += pow(pow2n, 2);
			return;
		}

		if (N <= 1) {
			cnt += (r - row) * pow2n + (c - col);
			flag = true;
			return;
		}
		int tmp = pow(2, N - 1);
		Z(row, col, N - 1);
		Z(row, col + tmp, N - 1);
		Z(row + tmp, col, N - 1);
		Z(row + tmp, col + tmp, N - 1);
	}

	static int pow(int n, int power) {
		if (power == 0)
			return 1;
		if (power == 1)
			return n;
		int half = pow(n, power / 2);
		return (power % 2 == 1) ? half * half * n : half * half;

	}
}
