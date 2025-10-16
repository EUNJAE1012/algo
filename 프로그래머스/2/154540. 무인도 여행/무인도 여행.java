/*
격자에
'X' = 바다, 숫자 = 무인도
상하좌우 붙어있으면 같은 무인도
숫자는 무인도의 식량이다. 합만큼 생존가능

유니온-파인드로 섬을 정의한다.
list로 각 섬마다 식량의 수를 저장한다.
정렬후 Array로 변경하여 반환한다.

*/
import java.util.*;
class Solution {
    static int[] parents;
    static boolean[][] visited;
    static int[][] map;
    static List<Integer> foodOfIsland;
    static int[] dr = {-1,1,0,0}; //상하좌우
    static int[] dc = {0,0,-1,1};
    static int rowSize,colSize;
    public int[] solution(String[] maps) {
        rowSize = maps.length;
        colSize = maps[0].length();
        make(rowSize*colSize);
        //지도 채우기
        for(int row = 0; row<rowSize;row++){
            String line = maps[row];
            for(int col = 0; col<colSize;col++){
                char c = line.charAt(col);
                // System.out.print(c);
                if(c=='X') {
                    map[row][col] = 0;
                    visited[row][col] = true;
                    continue;
                }
                map[row][col] = c-'0'; 
            }
        }
        for(int row = 0; row<rowSize;row++){
            for(int col = 0; col<colSize;col++){
                int idx = row*colSize + col;
                if(visited[row][col]) continue;
                bfs(row,col);
            }
        }
        Collections.sort(foodOfIsland);
        int[] answer = new int[foodOfIsland.size()];
        if(foodOfIsland.size()==0) return new int[] {-1};
        int i = 0;
        for(int c : foodOfIsland){
            answer[i++] = c;
        }
        return answer;
    }
    static void bfs(int row, int col){
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {row,col});
        visited[row][col] = true;
        int sum = 0;
        int cnt = 0;
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];
            // System.out.println("방문 :"+r+","+c+","+map[r][c]);
            sum+=map[r][c];
            cnt++;
            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];
                if(nr<0||nc<0||nr>=rowSize||nc>=colSize
                   || visited[nr][nc]) continue;
                visited[nr][nc] = true;
                queue.add(new int[] {nr,nc});
            }
        }
        // System.out.println("방문한 섬의 수 : "+cnt+" 총 "+sum);
        foodOfIsland.add(sum);
    }
    
    static void make(int size){
        foodOfIsland = new ArrayList<>();
        map = new int[rowSize][colSize];
        parents = new int[size];
        visited = new boolean[rowSize][colSize];
        for(int idx=0; idx<size;idx++){
            parents[idx] = idx;
        }
    }
    static int find(int a){
        if(parents[a]==a) return a;
        return parents[a] = find(parents[a]);
    }
    
    static boolean union(int a, int b){
        int rootA = find(a);
        int rootB = find(b);
        if(rootA == rootB) return false; 
        parents[rootB] = rootA; 
        return true;
    }
}