package day4;
import java.io.*;
import java.util.*;

public class BOJ_14502_연구소_김은재 {
    static BufferedReader br;
    static StringTokenizer st;
    static int rowSize, colSize;
    static List<Virus> virusList;
    static long map;
    static int maxSafeZone;

    static class Virus {
        int row, col;
        public Virus(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        virusList = new ArrayList<>();
        map = 0;
        
        for (int row = 0; row < rowSize; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < colSize; col++) {
                int c = Integer.parseInt(st.nextToken());
                if (c == 2) {
                    virusList.add(new Virus(row, col));
                }
                if (c != 0) {
                    map |= (1L << (row * colSize + col));
                }
            }
        }
        
        // 가능한 모든 벽 배치 조합 탐색
        generateCombinations(0, 0, new int[3]);
        System.out.println(maxSafeZone);
    }

    // 벽 3개를 선택하는 모든 조합 생성
    static void generateCombinations(int start, int count, int[] selected) {
        if (count == 3) {
            long newMap = map;
            for (int i = 0; i < 3; i++) {
                newMap |= (1L << selected[i]); // 벽을 배치
            }
            int safeZone = simulateVirusSpread(newMap);
            maxSafeZone = Math.max(maxSafeZone, safeZone);
            return;
        }
        
        for (int pos = start; pos < rowSize * colSize; pos++) {
            // 빈 칸인 경우에만 벽 설치 가능
            if ((map & (1L << pos)) == 0) {
                selected[count] = pos;
                generateCombinations(pos + 1, count + 1, selected);
            }
        }
    }

    // 바이러스 확산 후 안전구역 개수 계산
    static int simulateVirusSpread(long virusMap) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (Virus v : virusList) {
            int startSpot = v.row * colSize + v.col;
            queue.offer(startSpot);
            virusMap |= (1L << startSpot);
        }

        while (!queue.isEmpty()) {
            int curSpot = queue.poll();
            int curRow = curSpot / colSize;
            int curCol = curSpot % colSize;
            int[] dRow = {-1, 1, 0, 0}; // 상하좌우 이동
            int[] dCol = {0, 0, -1, 1};

            for (int d = 0; d < 4; d++) {
                int newRow = curRow + dRow[d];
                int newCol = curCol + dCol[d];
                if (newRow < 0 || newRow >= rowSize || newCol < 0 || newCol >= colSize) continue;
                
                int newSpot = newRow * colSize + newCol;
                if ((virusMap & (1L << newSpot)) == 0) {
                    virusMap |= (1L << newSpot);
                    queue.offer(newSpot);
                }
            }
        }
        
        return rowSize * colSize - Long.bitCount(virusMap); // 전체 칸 수 - 바이러스 퍼진 칸
    }
}