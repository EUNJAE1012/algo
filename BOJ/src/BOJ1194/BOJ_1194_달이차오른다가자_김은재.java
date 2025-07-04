package BOJ1194;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 문제
 * 달이차오른다~~ 가자 미로 탈출하러 가자.
 * 
 * 빈칸 . 언제나 이동  가능
 * 벽 # 절대 이동 불가
 * 열쇠 언제나 이동 가능, 처음 진입시 열쇠 집기 (a,b,c,d,e,f) 6종류
 * 문 : 열쇠가 있으면 이동 가능 (A,B,C,D,E,F)
 * 민식이 : 0  
 * 
 * 풀이 
 * BFS
 * 기본적으로 방문 처리릃 하나, 열쇠 획득 시, visited 배열 초기화
 * 
 * 
 */
public class BOJ_1194_달이차오른다가자_김은재 {

    static int rowSize, colSize;
    static int[][] map;
    static boolean[][][] visited;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class Pos {
        int row, col, cnt, key;

        public Pos(int row, int col, int cnt, int key) {
            this.row = row;
            this.col = col;
            this.cnt = cnt;
            this.key = key;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        map = new int[rowSize][colSize];
        visited = new boolean[rowSize][colSize][64];
        Queue<Pos> queue = new ArrayDeque<>();

        for (int i = 0; i < rowSize; i++) {
            String line = br.readLine();
            for (int j = 0; j < colSize; j++) {
                char ch = line.charAt(j);
                if (ch == '#') map[i][j] = -1;
                else if (ch == '.') map[i][j] = 0;
                else if (ch == '0') {
                    map[i][j] = 0;
                    queue.offer(new Pos(i, j, 0, 0));
                    visited[i][j][0] = true;
                } else if (ch == '1') map[i][j] = 9; // 출구
                else if (ch >= 'a' && ch <= 'f') map[i][j] = ch - 'a' + 100; // 열쇠
                else if (ch >= 'A' && ch <= 'F') map[i][j] = ch - 'A' + 200; // 문
            }
        }

        System.out.println(bfs(queue));
    }

    static int bfs(Queue<Pos> queue) {
        while (!queue.isEmpty()) {
            Pos cur = queue.poll();

            int curVal = map[cur.row][cur.col];

            if (curVal == 9) return cur.cnt; // 출구 도착

            int newKey = cur.key;

            // 열쇠 획득
            if (curVal >= 100 && curVal <= 105) {
                newKey |= (1 << (curVal - 100));
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.row + dr[d];
                int nc = cur.col + dc[d];
                //범위 바깥
                if (nr < 0 || nc < 0 || nr >= rowSize || nc >= colSize) continue;
                int next = map[nr][nc];
                
                if (next == -1) continue; // 벽
                if (next >= 200 && next <= 205) { // 문인데 열쇠 없음
                    if ((newKey & (1 << (next - 200))) == 0) continue;
                }
                if (visited[nr][nc][newKey]) continue;

                visited[nr][nc][newKey] = true;
                queue.offer(new Pos(nr, nc, cur.cnt + 1, newKey));
            }
        }

        return -1;
    }
}