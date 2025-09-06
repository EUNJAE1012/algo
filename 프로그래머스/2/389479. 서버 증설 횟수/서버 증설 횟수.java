/*
동접 m명 추가당 서버가 1대 필요하다.
서버 증설시 k시간 동안 운영하고 반납한다.
하루에 몇번 서버를 증설해야할까?

dp[n][m] n시간대에 m번 증설시 가능?
*/
    
import java.util.*;
class Solution {
    
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        int[] maxPlayer = new int[24];
        Arrays.fill(maxPlayer,m);
        for(int time = 0;time<24;time++){
            while(players[time]>=maxPlayer[time]){
                // System.out.println(time+" 증설");
                answer++;
                for(int t = 0;t<k;t++){
                    if(time+t>=24)break;
                     maxPlayer[time+t]+=m;
                }
            }
        }
        
        return answer;
    }
}