/*
컨테이너 nxm개
지게차가 특정 종류 출고 요청이 들어오면, 외부에 노출된 컨테이너를 모두 꺼낸다.
크레인이 외부에 노출되지 않은 컨테이너도 꺼낸다.
알파벳이 한번나오면 지게차, 크레인이 나오면 컨테이너다.
모든 요청완료 후 남은 컨테이너의 수는?

*/
import java.util.*;
class Solution {
    
    static class Pos{
        int row;
        int col;
        Pos(int row, int col){
            this.row = row;
            this.col = col;
        }
        
        }
    

    static char[][] warehouse;
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    static int cnt,m,n;
    public int solution(String[] storage, String[] requests) {
        n = storage.length;
        m = storage[0].length();
        
        warehouse = new char[n][m]; //row,col
        
        for(int row = 0; row<n; row++){
            for(int col=0; col<m;col++){
                warehouse[row][col] = storage[row].charAt(col);
            }
        } 
        cnt = n*m;
        for(String request : requests){
            if(request.length() == 1){
                //지게차 맵 전체 순회하며, 바깥인지 검사?
                jigae(request.charAt(0));
            }
            else{
                //크레인
                crain(request.charAt(0));
            }
            // for(int row = 0; row<n;row++){
            //     System.out.println(Arrays.toString(warehouse[row]));
            // }
            // System.out.println();
            
        }
        return cnt;
    }
    
    //내가 아웃사이더가 될때, 내 주변의 임시도 아웃사이더
    static void check(int row, int col){
        warehouse[row][col] = '.';
        for(int d = 0; d<4;d++){
            int nr = row+dr[d];
            int nc = col+dc[d];
            if(nr>=0&&nc>=0&&nr<n&&nc<m&&warehouse[nr][nc]=='-'){
                check(nr,nc);
            }
        }
    }
    
static void crain(char target){
    List<int[]> toRemoveNow = new ArrayList<>();
    List<int[]> toMarkTemp = new ArrayList<>();
    
    // 1단계: 제거할 것들 분류만
    for(int row = 0; row < n; row++){
        for(int col = 0; col < m; col++){
            if(warehouse[row][col] != target) continue;
            
            if(row == 0 || col == 0 || row == n-1 || col == m-1){
                toRemoveNow.add(new int[]{row, col});
                continue;
            }
            
            boolean isOutside = false;
            for(int d = 0; d < 4; d++){
                int nr = row + dr[d];
                int nc = col + dc[d];
                if(warehouse[nr][nc] == '.'){
                    toRemoveNow.add(new int[]{row, col});
                    isOutside = true;
                    break;
                }
            }
            
            if(!isOutside){
                toMarkTemp.add(new int[]{row, col});
            }
        }
    }
    
    // 2단계: 실제 처리
    for(int[] pos : toMarkTemp){
        cnt--;
        warehouse[pos[0]][pos[1]] = '-';
    }
    
    for(int[] pos : toRemoveNow){
        cnt--;
        check(pos[0], pos[1]);
    }
    

}
    
static void jigae(char target){
    List<int[]> toRemove = new ArrayList<>();
    for(int row=0;row<n;row++){
        for(int col=0;col<m;col++){
            if(warehouse[row][col]!=target)continue;
            boolean flag = false;
            
            // 가장자리 체크
            if(row==0||col==0||row==n-1||col==m-1){
                flag = true;
            }
            else{
                // 인접한 빈 공간 체크
                for(int d = 0; d<4;d++){
                    int nr = row+dr[d];
                    int nc = col+dc[d];
                    if(nr>=0&&nc>=0&&nr<n&&nc<m&&warehouse[nr][nc]=='.'){
                        flag = true;
                        break; // 하나라도 찾으면 즉시 종료
                    }
                }
            }
            if(flag){
                toRemove.add(new int[]{row,col});
            }
        }     
    }
    for(int[] cur : toRemove){
        int r = cur[0];
        int c = cur[1];
        cnt--;
        check(r,c);
    }
}
    
}

