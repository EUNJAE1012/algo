/*
nxm 크기의 격자 미로
x,y -> r,c 로 이동
1. 바깥으로 못나감
2. 이동 거리는 총 k 여야 함 
3. 중복 방문 가능
4. 미로에서 탈출한 경로 문자열이 가장 빠른 순으로 탈출
5. 경로 문자열 = l 좌 r 우 u 상 d 하
경로가 좌상좌였으면 lul
d->l->r->u
하좌우상
*/
import java.util.*;

class Solution {
    // 사전순 우선순위: d(하) -> l(좌) -> r(우) -> u(상)
    static int[] dr = {1, 0, 0, -1}; // 하, 좌, 우, 상
    static int[] dc = {0, -1, 1, 0}; // 하, 좌, 우, 상
    static char[] directions = {'d', 'l', 'r', 'u'};
    
    static int n, m;
    static int targetRow, targetCol, targetCnt;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        this.n = n;
        this.m = m;
        this.targetRow = r - 1; // 0-indexed로 변환
        this.targetCol = c - 1;
        this.targetCnt = k;
        
        int startRow = x - 1; // 0-indexed로 변환
        int startCol = y - 1;
        
        // 최단 거리 계산
        int minDist = Math.abs(startRow - targetRow) + Math.abs(startCol - targetCol);
        
        // 불가능한 경우 체크
        if (k < minDist) return "impossible";
        if ((k - minDist) % 2 == 1) return "impossible";
        
        StringBuilder result = new StringBuilder();
        if (dfs(startRow, startCol, 0, result)) {
            return result.toString();
        }
        
        return "impossible";
    }
    
    static boolean dfs(int row, int col, int depth, StringBuilder path) {
        // 목표 도달
        if (depth == targetCnt) {
            return row == targetRow && col == targetCol;
        }
        
        // 현재 위치에서 목표까지의 최단 거리
        int distToTarget = Math.abs(row - targetRow) + Math.abs(col - targetCol);
        int remaining = targetCnt - depth;
        
        // 남은 이동 횟수로 목표에 도달할 수 없는 경우
        if (remaining < distToTarget) return false;
        
        // 남은 거리가 홀수인 경우 (왕복 불가능)
        if ((remaining - distToTarget) % 2 == 1) return false;
        
        // 4방향 탐색 (사전순)
        for (int i = 0; i < 4; i++) {
            int nextRow = row + dr[i];
            int nextCol = col + dc[i];
            
            // 경계 체크
            if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= m) {
                continue;
            }
            
            // 다음 위치에서도 목표 도달 가능한지 체크
            int nextDistToTarget = Math.abs(nextRow - targetRow) + Math.abs(nextCol - targetCol);
            int nextRemaining = remaining - 1;
            
            if (nextRemaining >= nextDistToTarget && (nextRemaining - nextDistToTarget) % 2 == 0) {
                path.append(directions[i]);
                if (dfs(nextRow, nextCol, depth + 1, path)) {
                    return true;
                }
                path.deleteCharAt(path.length() - 1); // 백트래킹
            }
        }
        
        return false;
    
    }
}