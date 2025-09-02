/*
주문 = 소문자알파벳 11글자 이하 = 26^11개의 수 표현 가능
주문서에는 모든 문자열이 적혀있음
글자수가 적은주문부터, 사전순으로 (a,b,c,d,e,f,...z , aa, ab, ac, ad...az,ba...zz)
주문서에서 몇개가 삭제됨
삭제된 후의 n번째 주문찾기

String -> Long으로 전환.. n보다 작은 string 세기(cnt)
그 후 n-cnt -> String 전환..

*/
import java.util.*;

class Solution {
    
    static long[] pow26;
    static List<Long> id;
    public String solution(long n, String[] bans) {
        long idx = n;
        id = new ArrayList<>();
        init();
        int i = 0;
        for(String str : bans){
            long ex = StringtoLong(str);
            id.add(ex);
        }
        Collections.sort(id);
        for(long cur : id){
            if(cur<=idx){
                idx++;
            }
        }

        String answer = LongtoString(idx);
        return answer;
    }
    
    static long StringtoLong(String str){
        long total = 0;
        for(int idx = 0; idx<str.length();idx++){
            char c = str.charAt(str.length()-idx-1);
            total += pow26[idx]* ((c-'a')+1);
        }
        return total;
    }
    
    // 1->a 26 -> z 27 -> aa     27=26*1+1
    static String LongtoString(long l){
        StringBuilder sb = new StringBuilder();
        long cur = l;
        long mod = 0;
        while(cur>0){
            long remainder = (cur - 1) % 26;
            char c = (char)('a' + remainder);
            cur = (cur - 1) / 26; 
            sb.append(c+"");
        }
        return sb.reverse().toString();
    }
    
    //26의 지수들 구하기
    static void init(){
        pow26 = new long[12];
        dfs(11);
    }
    
    static long dfs(int depth){
        if(depth==0){
            return pow26[0] = 1;
        }
        return pow26[depth] = dfs(depth-1)*26;
    }
    
    
}