package BOJ27172;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * BOJ 27172 : 수나누기 게임
 * 게임 룰
 *  각 플레이어는 1~1,000,000사이의 수가 적힌 서로 다른 카드를 하나 받는다.
 *  매턴마다 다른 플레이어와 결투한다.
 *   서로의 카드를 보여주며
 *   내가 너의 약수면 내가 이겨
 *   아니면 무승부
 *  승리한 플레이어는 +1점, 패배한 플레이어는 -1점
 *  리그룰로 모든 플레이어와 한번씩 결투를 하면 게임 종료
 *  
 * 각 플레이어의 카드 점수가 주어진다.
 * 게임 종료된 후의 모든 플레이어의 점수를 구하라
 * 
 *  N 플레이어 수 2~100,000
 *  
 * 풀이
 * 1-> 무조건 지는 카드 -(N-1)점
 * 소수 -> 최소한 지지는 않는 카드
 * 
 * 1. 각 카드를 한번씩 본다.
 * 2. 카드의 모든 배수들에 대하여
 * 2.1 카드 목록에 존재한다면 
 * 2.1.1 내점수+1, 배수의점수-1 
 * 
 * 
 * 
 */
public class Main {

    static final int MAX = 1_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] cards = new int[N];
        boolean[] present = new boolean[MAX + 1];
        int[] score = new int[MAX + 1];

        /* 입력 */
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int v = Integer.parseInt(st.nextToken());
            cards[i] = v;
            present[v] = true;
        }

        /* 점수 계산 : 작은 수 → 배수만 탐색 */
        for (int v : cards) {
            for (int m = v * 2; m <= MAX; m += v) {
                if (present[m]) {     // v | m 인 경우만
                    score[v]++;       // v 승
                    score[m]--;       // m 패
                }
            }
        }

        /* 출력 */
        StringBuilder sb = new StringBuilder();
        for (int v : cards) sb.append(score[v]).append(' ');
        System.out.println(sb.toString().trim());
    }
}