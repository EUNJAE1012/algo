
import java.util.Scanner;

public class Main {
    static final int MOD = 10007;
    static final int MAX = 53; // 52까지 조합 필요

    static long[] factorial = new long[MAX];
    static long[] reverseFactorial = new long[MAX];
    static int N;
    static long sum;

    public static void main(String[] args) {
        input();
        init();
        for (int fourCardCnt = 1; fourCardCnt <= 13; fourCardCnt++) {
            int usedCard = fourCardCnt * 4;
            if (usedCard > N) break;

            long selectFourCard = comb(13, fourCardCnt);
            long selectOtherCard = comb(52 - usedCard, N - usedCard);
            long term = (selectFourCard * selectOtherCard) % MOD;

            if ((fourCardCnt & 1) == 1) {
                sum = (sum + term) % MOD; // 홀수일 때 +
            } else {
                sum = (sum - term + MOD) % MOD; // 짝수일 때 -, 음수 보정
            }
        }

        System.out.println(sum);
    }

    static void input() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
    }

    static void init() {
        factorial[0] = reverseFactorial[0] = 1;
        for (int i = 1; i < MAX; i++) {
            factorial[i] = (factorial[i - 1] * i) % MOD;
        }
        reverseFactorial[MAX - 1] = modInverse(factorial[MAX - 1]);
        for (int i = MAX - 2; i >= 1; i--) {
            reverseFactorial[i] = (reverseFactorial[i + 1] * (i + 1)) % MOD;
        }

        sum = 0;
    }

    static long comb(int n, int r) {
        if (r < 0 || r > n) return 0;
        return (((factorial[n] * reverseFactorial[r]) % MOD) * reverseFactorial[n - r]) % MOD;
    }

    static long modInverse(long a) {
        return pow(a, MOD - 2);
    }

    static long pow(long x, long exp) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * x) % MOD;
            x = (x * x) % MOD;
            exp >>= 1;
        }
        return res;
    }
}