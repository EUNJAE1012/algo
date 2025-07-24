/*
 * BOJ 16234 인구이동
 * N*N 크기의 땅이 있다.
 * 각 땅에는 나라가 하나씩 있고 r행c열에는 A[r][c]명이 산다.
 * 다음 규칙을 따라 인구이동이 발생한다.
 * 1. 국경선을 맞대는 두 나라의 인구 차이가 L이상 R이하면 오늘 개방함
 * 2. 다 열렸으면 인구이동함
 * 3. 국경선이 열려 인접한 칸만으로 이동가능하면 연합이다.
 * 4. 연합의 인구는 엔빵해서 나눠갖는다. 소수점은 버린다.
 * 5. 다 나눴으면 문닫고 연합끝
 * 각 나라의 인구수가 주어졌을때 ,인구 이동이 며칠동안 발생하는지 구하라
 * 
 * 풀이
 * 연합하는 기준이 union-find 스럽다. 아 대놓고 union이구나.
 * 순차적으로 해보자. 언제까지? 인구차이가 L보다 작거나,, r보다 클때까지
 * 
 * 
 */
import java.io.*;
import java.util.*;

public class Main {
    static int N, L, R;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        int day = 0;

        while (true) {
            visited = new boolean[N][N];
            boolean moved = false;

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (!visited[r][c]) {
                        List<int[]> union = bfs(r, c);
                        if (union.size() > 1) {
                            moved = true;
                            int sum = 0;
                            for (int[] pos : union) {
                                sum += map[pos[0]][pos[1]];
                            }
                            int avg = sum / union.size();
                            for (int[] pos : union) {
                                map[pos[0]][pos[1]] = avg;
                            }
                        }
                    }
                }
            }

            if (!moved) break;
            day++;
        }

        System.out.println(day);
    }

    static List<int[]> bfs(int sr, int sc) {
        Queue<int[]> queue = new ArrayDeque<>();
        List<int[]> union = new ArrayList<>();

        queue.add(new int[]{sr, sc});
        union.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
                if (visited[nr][nc]) continue;

                int diff = Math.abs(map[r][c] - map[nr][nc]);
                if (diff >= L && diff <= R) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                    union.add(new int[]{nr, nc});
                }
            }
        }

        return union;
    }
}
