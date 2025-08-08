
import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int N;
    static int[] solutions;

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        solutions = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            solutions[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(solutions);

        twoPointer();
    }

    static void twoPointer() {
        int left = 0;
        int right = N - 1;
        long minValue = Long.MAX_VALUE; // 절댓값 비교용
        int ansL = 0;
        int ansR = N - 1;

        while (left < right) {
            long sum = (long) solutions[left] + solutions[right]; // 오버플로우 방지
            if (Math.abs(sum) < minValue) {
                minValue = Math.abs(sum);
                ansL = left;
                ansR = right;
            }
            if (sum == 0) {
                break; // 합이 0이면 최적 해, 종료
            } else if (sum > 0) {
                right--; // 합이 양수면 더 작은 값으로
            } else {
                left++; // 합이 음수면 더 큰 값으로
            }
        }

        sb.append(solutions[ansL]).append(" ").append(solutions[ansR]);
        System.out.println(sb);
    }
}