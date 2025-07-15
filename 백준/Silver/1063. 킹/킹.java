
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * BOJ 1063: 킹
 * 8*8 체스판에 킹이 하나 놓여있다.
 * 그리고 돌도 하나 있다.
 * 킹이 돌의 위치로 움직이면 돌은 같은 방향으로 밀려난다.
 * N번 움직였을때, 킹과 돌의 위치를 구하라.
 * 판 밖으로 나가는 움직임은 무효이다.
 */
public class Main {
	
	static BufferedReader br;
	static StringTokenizer st;
	static int N;
	static class kingStone{
		char x;
		int y;
		
		public kingStone(char alpha, int c) {
			this.x = alpha;
			this.y =c;
		}
		
		@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				
				sb.append(x).append(y);
				return sb.toString();
			}
		
		public void move(String command, kingStone stone) {
    int dx = 0, dy = 0;
    switch (command) {
        case "R":  dx = 1;  break;
        case "L":  dx = -1; break;
        case "B":  dy = -1; break;
        case "T":  dy = 1;  break;
        case "RT": dx = 1; dy = 1;   break;
        case "LT": dx = -1; dy = 1;  break;
        case "RB": dx = 1; dy = -1;  break;
        case "LB": dx = -1; dy = -1; break;
    }

    int newKingX = x + dx;
    int newKingY = y + dy;
    // 1) 킹 경계 체크
    if (newKingX < 'A' || newKingX > 'H' || newKingY < 1 || newKingY > 8) {
        return;
    }
    // 2) 킹이 돌 위치로 가려 할 때만 돌을 밀기
    if (newKingX == stone.x && newKingY == stone.y) {
        int newStoneX = stone.x + dx;
        int newStoneY = stone.y + dy;
        // 돌 경계 체크
        if (newStoneX < 'A' || newStoneX > 'H' || newStoneY < 1 || newStoneY > 8) {
            return;
        }
        stone.x = (char) newStoneX;
        stone.y = newStoneY;
    }
    // 3) 킹 이동
    x = (char) newKingX;
    y = newKingY;
}

	}
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
		String kingStr = st.nextToken();
		
		kingStone king = new kingStone(kingStr.charAt(0), kingStr.charAt(1)-48);
		
		String stoneStr = st.nextToken();
		kingStone stone = new kingStone(stoneStr.charAt(0),stoneStr.charAt(1)-48);
		N = Integer.parseInt(st.nextToken());
		
		for(int move = 0; move<N;move++) {
			String command = br.readLine();
			king.move(command, stone);
		}
		System.out.println(king);
		System.out.println(stone);
	}
}
