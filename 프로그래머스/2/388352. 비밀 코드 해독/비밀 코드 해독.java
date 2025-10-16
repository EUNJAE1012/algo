/*
비밀번호 1~n 서로 다른정수 5개 오름차순(조합)
m번 시도가능 
5개 정수 입력 -> 몇개가 포함되었는지 알려줌
예) 입력한 정수	시스템 응답(일치하는 개수)
[1, 2, 3, 4, 5]	2개
[6, 7, 8, 9, 10]	3개
[3, 7, 8, 9, 10]	4개
[2, 5, 7, 9, 10]	3개
[3, 4, 5, 6, 7]	3개
가능한 조합 : [3, 4, 7, 9, 10] [3, 5, 7, 8, 9] [3, 5, 7, 8, 10]
n= 10~30
m= 1~10
모든 조합에 대해서 
 각 조건을 하나씩 통과했을 때
 모든 조건을 만족하면 cnt++
*/
class Solution {
    static int maxNum;
    static int cnt;
    static boolean[] selected;
    static int[] selectedNum;
    static int m;
    static int[][] query;
    static int[] answer;
    public int solution(int n, int[][] q, int[] ans) {
        cnt = 0;
        query = q;
        answer = ans;
        m = q.length;
        selected = new boolean[n+1];
        selectedNum = new int[5];
        maxNum = n;
        comb(1,0);
        return cnt;
    }
    static boolean isValid(){
        for(int idx = 0; idx<m;idx++){
            int cnt = 0;
            for(int i = 0; i<5;i++){
                int select = selectedNum[i];
                for(int j = 0 ; j<5;j++){
                    int compare = query[idx][j];
                    if(select==compare)
                        cnt++;
                }
            }
            if(answer[idx]!=cnt) return false;
        }
        return true;
    }
    static void comb(int depth,int selectedCnt){
        //5개를 모두 선택한 경우
        if(selectedCnt==5){
            if(isValid()) cnt++;
            return;
        }
        //모든 숫자를 다 본 경우
        if(depth > maxNum){
            return;
        }
        //안 선택
        comb(depth+1,selectedCnt);
        //더 선택
        selected[depth] = true;
        selectedNum[selectedCnt] = depth;
        comb(depth+1,selectedCnt+1);
        selected[depth] = false;
    }
}