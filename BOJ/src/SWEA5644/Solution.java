package SWEA5644;

import java.util.*;

class AP {
    int row, col, range, chargeAmount;
    boolean using;

    public AP(int row, int col, int range, int chargeAmount) {
        this.row = row - 1;
        this.col = col - 1;
        this.range = range;
        this.chargeAmount = chargeAmount;
        this.using = false;
    }
}

public class Solution {
    static int[] dx = {0, -1, 0, 1, 0};
    static int[] dy = {0, 0, 1, 0, -1};
    static int M, A, charge;
    static int[] moveA, moveB;
    static int[][] pos;
    static List<AP> APArray;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int t = 1; t <= T; t++) {
            init(sc);
            charge += getMaxCharge(findUsable(pos[0][0], pos[0][1]), findUsable(pos[1][0], pos[1][1]));
            for (int i = 0; i < M; i++) {
                move(i);
                charge += getMaxCharge(findUsable(pos[0][0], pos[0][1]), findUsable(pos[1][0], pos[1][1]));
            }
            System.out.println("#" + t + " " + charge);
        }
    }

    static void init(Scanner sc) {
        M = sc.nextInt();
        A = sc.nextInt();
        moveA = new int[M];
        moveB = new int[M];
        for (int i = 0; i < M; i++) moveA[i] = sc.nextInt();
        for (int i = 0; i < M; i++) moveB[i] = sc.nextInt();
        pos = new int[][]{{0, 0}, {9, 9}};
        APArray = new ArrayList<>();
        charge = 0;
        for (int i = 0; i < A; i++) {
            APArray.add(new AP(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }
    }

    static void move(int time) {
        pos[0][0] += dx[moveA[time]];
        pos[0][1] += dy[moveA[time]];
        pos[1][0] += dx[moveB[time]];
        pos[1][1] += dy[moveB[time]];
    }

    static List<AP> findUsable(int r, int c) {
        List<AP> usable = new ArrayList<>();
        for (AP ap : APArray) {
            if (Math.abs(r - ap.row) + Math.abs(c - ap.col) <= ap.range) {
                usable.add(ap);
            }
        }
        return usable;
    }

    static int useAP(AP ap) {
        if (!ap.using) {
            ap.using = true;
            return ap.chargeAmount;
        }
        return 0;
    }

    static void resetAP() {
        for (AP ap : APArray) {
            ap.using = false;
        }
    }

    static int getMaxCharge(List<AP> usableA, List<AP> usableB) {
        int maxCharge = 0;
        for (AP apA : usableA) {
            int chargeA = useAP(apA);
            for (AP apB : usableB) {
                int chargeB = useAP(apB);
                maxCharge = Math.max(maxCharge, chargeA + chargeB);
                resetAP();
            }
        }
        for (AP apA : usableA) maxCharge = Math.max(maxCharge, useAP(apA));
        for (AP apB : usableB) maxCharge = Math.max(maxCharge, useAP(apB));
        resetAP();
        return maxCharge;
    }
}
