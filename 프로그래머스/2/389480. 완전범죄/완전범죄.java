/*
A도둑과 B도둑이 팀을 이루어 모든 물건을 훔치려고 합니다. 단, 각 도둑이 물건을 훔칠 때 남기는 흔적이 누적되면 경찰에 붙잡히기 때문에, 두 도둑 중 누구도 경찰에 붙잡히지 않도록 흔적을 최소화해야 합니다.

물건을 훔칠 때 조건은 아래와 같습니다.

물건 i를 훔칠 때,
A도둑이 훔치면 info[i][0]개의 A에 대한 흔적을 남깁니다.
B도둑이 훔치면 info[i][1]개의 B에 대한 흔적을 남깁니다.
각 물건에 대해 A도둑과 B도둑이 남기는 흔적의 개수는 1 이상 3 이하입니다.
경찰에 붙잡히는 조건은 아래와 같습니다.

A도둑은 자신이 남긴 흔적의 누적 개수가 n개 이상이면 경찰에 붙잡힙니다.
B도둑은 자신이 남긴 흔적의 누적 개수가 m개 이상이면 경찰에 붙잡힙니다.
각 물건을 훔칠 때 생기는 흔적에 대한 정보를 담은 2차원 정수 배열 info, A도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 n, B도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 m이 매개변수로 주어집니다. 두 도둑 모두 경찰에 붙잡히지 않도록 모든 물건을 훔쳤을 때, A도둑이 남긴 흔적의 누적 개수의 최솟값을 return 하도록 solution 함수를 완성해 주세요. 만약 어떠한 방법으로도 두 도둑 모두 경찰에 붙잡히지 않게 할 수 없다면 -1을 return해 주세요.
1 ≤ info의 길이 ≤ 40
info[i]는 물건 i를 훔칠 때 생기는 흔적의 개수를 나타내며, [A에 대한 흔적 개수, B에 대한 흔적 개수]의 형태입니다.
1 ≤ 흔적 개수 ≤ 3
1 ≤ n ≤ 120
1 ≤ m ≤ 120

dfs backtracking => 시간초과
dp?
boolean dp[i][a][b] = i번째 물건까지 고려했을 때, A가 a만큼, B가 b만큼 흔적을 남겼을 때 가능한지

*/

class Solution {
    static int[][] info;
    static int n, m;
    static Integer[][][] dp;
    
    public int solution(int[][] info, int n, int m) {
        this.info = info;
        this.n = n;
        this.m = m;
        this.dp = new Integer[info.length][n][m];
        
        int result = solve(0, 0, 0);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    // dp[i][a][b] = i번째부터 끝까지 고려했을 때 A의 최소 흔적
    static int solve(int idx, int aTrace, int bTrace) {
        // 모든 물건 처리 완료
        if (idx == info.length) {
            return aTrace;
        }
        
        if (dp[idx][aTrace][bTrace] != null) {
            return dp[idx][aTrace][bTrace];
        }
        
        int result = Integer.MAX_VALUE;
        
        // A가 훔치는 경우
        if (aTrace + info[idx][0] < n) {
            result = Math.min(result, solve(idx + 1, aTrace + info[idx][0], bTrace));
        }
        
        // B가 훔치는 경우
        if (bTrace + info[idx][1] < m) {
            result = Math.min(result, solve(idx + 1, aTrace, bTrace + info[idx][1]));
        }
        
        return dp[idx][aTrace][bTrace] = result;
    }
}