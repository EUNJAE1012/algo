
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int N, M, K;
    static long[] Tree;
    static int startPoint;
    static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        sb = new StringBuilder();
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (startPoint = 1; startPoint < N; startPoint <<= 1) {}
        Tree = new long[startPoint << 1];
        Arrays.fill(Tree, 1);

        for (int i = 0; i < N; i++) {
            Tree[startPoint + i] = Long.parseLong(br.readLine());
        }

        for (int i = startPoint - 1; i > 0; i--) {
            Tree[i] = (Tree[i << 1] * Tree[(i << 1) | 1]) % MOD;
        }

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (command == 1) {
                update(a, b);
            } else {
                sb.append(query(a, b)).append("\n");
            }
        }

        System.out.print(sb);
    }

    static void update(int index, long value) {
        int idx = index + startPoint - 1;
        Tree[idx] = value;

        while (idx > 1) {
            idx >>= 1;
            Tree[idx] = (Tree[idx << 1] * Tree[(idx << 1) | 1]) % MOD;
        }
    }

    static long query(int left, int right) {
        int l = left + startPoint - 1;
        int r = right + startPoint - 1;
        long result = 1;

        while (l <= r) {
            if ((l & 1) == 1) result = (result * Tree[l++]) % MOD;
            if ((r & 1) == 0) result = (result * Tree[r--]) % MOD;
            l >>= 1;
            r >>= 1;
        }

        return result;
    }
}