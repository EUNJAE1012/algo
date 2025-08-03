import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * BOJ 9461: 파도반 수열
 * 삼각형이 나선모양으로 놓여져 있다.
 * 첫삼각형은 변의길이가 1인 정삼각형
 * 그 후 나선에서 가장 길이가 긴 변의 길이를 k 라 할때
 * 그 변에 길이가 k인 정삼각형을 붙인다.
 * n번째 삼각형의 변의 길이를 구하는 프로그램을 작성하라.
 *
 * 풀이 
 * wait a min..이게뭐지?
 * 각 삼각형은, 바로 직전의 삼각형과 가용한 다른 삼각형으로 생성된다.
 * 이때, 1번째 삼각형을 제외하곤 2번까지 가용될 수 있음
 * 예)
 * 2번째삼각형: 1번째 삼각형으로 생성 
 * 3번째삼각형: 2번째 삼각형으로 생성 
 * 4번째삼각형: 1,3번째 삼각형으로 생성 
 * 5번째삼각형: 4번째 삼각형으로 생성
 * 6번째 삼각형: 5,3번째 삼각형을 생성
 * 7번째 삼각형: 6,2
 * 8        : 7,1
 * 9        : 8,3
 * 10       :9,4
 * 11       :10,5 이 다음부터는 뒤에오는 숫자는 +1만되기만한다.
 * 
 * dp[6]까지는 고정치
 * dp[7]부터는 dp[i-1] + dp[i-5]
 *  
 * 
 */
public class Main {
	static BufferedReader br;
	static long[]  dp;
	static int T;
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		dp = new long[101];
		dp[1] = 1;
		dp[2] = 1;
		dp[3] = 1;
		dp[4] = 2;
		dp[5] = 2;
		dp[6] = 3;
		dp[7] = 4;
		dp[8] = 5;
		
		for(int i = 9; i<101;i++) {
			dp[i] = dp[i-1] + dp[i-5];
		}
		
		for(int tc=0; tc<T;tc++) {
			int n = Integer.parseInt(br.readLine());
			System.out.println(dp[n]);
		}
		
		
	}
}