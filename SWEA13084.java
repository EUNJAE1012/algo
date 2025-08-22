package SWEA13084;

/*
 * SWEA 13084 AI 로봇
 * 로봇N대가 있다. 로봇은 고유번호가 있다.
 * 로봇에는 지능 지수가 있다.
 * 센터에 대기중일때, 매시간 1씩 증가한다.
 * 로봇이 작업을 할때, 두가지 방식이 있다.
 * 1. 고지능 first 지능이 같다면 고유번호가 낮은
 * 2. 저지능 first 지능이 같으면 고유번호가 낮은
 * 
 * 작업중에 지능은 변화하지 않는다.
 * 복귀완료할때까지 새로운 작업에 투입되지 않는다.
 * 
 * 로봇이 고장나기도 한다. 고장나면 센터로 즉시 복귀 하고 작업하지 않는다.
 * 수리 완료시 지능은 0이 되고 다시 트레이닝한다.
 * 
 * heap 정렬기준 = 지능 = 누적지능+대기시간(기록시간에 반비례)
 * 
 * 
 */
import java.util.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
private final static int CALL_JOB = 100;
private final static int RETURN_JOB = 200;
private final static int BROKEN = 300;
private final static int REPAIR = 400;
private final static int CHECK = 500;

private static UserSolution usersolution = new UserSolution();

private static int run(BufferedReader br, int score) throws Exception {
    int N, Q;
    int wIDCnt = 1;
    int cTime, mNum, rID, wID, mOpt;
    int res = -1, ans;

    N = Integer.parseInt(br.readLine());
    usersolution.init(N);

    Q = Integer.parseInt(br.readLine());
    int errorline = 0;
    
    while (Q-- > 0) {
        errorline++;
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int cmd = Integer.parseInt(st.nextToken());

        switch (cmd) {
            case CALL_JOB:
                cTime = Integer.parseInt(st.nextToken());
                mNum = Integer.parseInt(st.nextToken());
                mOpt = Integer.parseInt(st.nextToken());
                res = usersolution.callJob(cTime, wIDCnt, mNum, mOpt);
                ans = Integer.parseInt(st.nextToken());
                
                System.err.println("Line " + errorline + " CALL_JOB: expected=" + ans + ", actual=" + res);
                
                if (ans != res) {
                    System.err.println("ERROR at Line " + errorline + ": CALL_JOB failed!");
                    score = 0;
                }
                wIDCnt++;
                break;
                
            case RETURN_JOB:
                cTime = Integer.parseInt(st.nextToken());
                wID = Integer.parseInt(st.nextToken());
                
                System.err.println("Line " + errorline + " RETURN_JOB: cTime=" + cTime + ", wID=" + wID);
                
                usersolution.returnJob(cTime, wID);
                break;
                
            case BROKEN:
                cTime = Integer.parseInt(st.nextToken());
                rID = Integer.parseInt(st.nextToken());
                
                System.err.println("Line " + errorline + " BROKEN: cTime=" + cTime + ", rID=" + rID);
                
                usersolution.broken(cTime, rID);
                break;
                
            case REPAIR:
                cTime = Integer.parseInt(st.nextToken());
                rID = Integer.parseInt(st.nextToken());
                
                System.err.println("Line " + errorline + " REPAIR: cTime=" + cTime + ", rID=" + rID);
                
                usersolution.repair(cTime, rID);
                break;
                
            case CHECK:
                cTime = Integer.parseInt(st.nextToken());
                rID = Integer.parseInt(st.nextToken());
                res = usersolution.check(cTime, rID);
                ans = Integer.parseInt(st.nextToken());
                
                System.err.println("Line " + errorline + " CHECK: expected=" + ans + ", actual=" + res + ", rID=" + rID);
                
                if (ans != res) {
                    System.err.println("ERROR at Line " + errorline + ": CHECK failed!");
                    score = 0;
                }
                break;
                
            default:
                System.err.println("ERROR at Line " + errorline + ": Unknown command " + cmd);
                score = 0;
                break;
        }
        
        if (score == 0) {
            System.err.println("Test failed at line " + errorline);
            break; // 첫 번째 에러에서 중단
        }
    }

    return score;
}
    
    public static void main(String[] args) throws Exception
    {
        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine(), " ");

        int TC = Integer.parseInt(line.nextToken());
        int Ans = Integer.parseInt(line.nextToken());
        
        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            System.out.println("#" + testcase + " " + run(br, Ans));
        }
        
        br.close();
    }
}

class UserSolution
{
	static PriorityQueue<robot> minHeap;
	static PriorityQueue<robot> maxHeap;
	static Map<Integer,List<robot>> workMap; //wid, returning robots;
	static List<robot> robotList;
	static int[] version;
	public static class robot{
		int num;
		int intel;
		int time; //로봇의 작업 가능 시간
		boolean isBusy;
		boolean isBroken;
		boolean is조퇴;
		int workID;
		int version;
		robot(int num){
			this.num = num;
			this.intel = 0;
			this.time = 0;
			this.isBusy = false;
			this.isBroken = false;
			this.workID = 0;
		}
		
		public void copy(robot o) {
			this.num = o.num;
			this.intel = o.intel;
			this.time = o.time;
			this.isBusy = o.isBusy;
			this.workID = o.workID;
		}
		
		@Override
			public String toString() {
				return String.format("%2d번 지능:%d,시간:%d", num,intel,time);
			}
		
	}
	
	public  void init(int N)
	{
		minHeap = new PriorityQueue<robot>((a,b) -> 
	    (a.intel - a.time != b.intel - b.time) ? 
	    Integer.compare(a.intel - a.time, b.intel - b.time) : 
	    Integer.compare(a.num, b.num)
	);maxHeap = new PriorityQueue<robot>((a,b) -> 
	    (a.intel - a.time != b.intel - b.time) ? 
	    Integer.compare(b.intel - b.time, a.intel - a.time) : 
	    Integer.compare(a.num, b.num)
	);
		workMap = new HashMap<>();
		robotList = new ArrayList<>();
		for(int i = 1; i<=N; i++) {
			robot cur = new robot(i);
			minHeap.add(cur);
			maxHeap.add(cur);
			robotList.add(cur);
		}
		version = new int[N+1];
		

	}
	
	public void printHeap() {
		
		System.out.println("minHeap"+minHeap);
		System.out.println("maxHeap"+maxHeap);
		
	}
	

	public int callJob(int cTime, int wID, int mNum, int mOpt)
	{
		// 목록에서 일할 로봇들을 뽑는다.
		int numSum = 0;
		int cnt = 0;
		List<robot> robots = new ArrayList<>();
		//고지능우선
		if(mOpt==0) {
			while(cnt<mNum) {
				robot candidate = maxHeap.poll();

				if(candidate.isBusy || candidate.isBroken || candidate.version != version[candidate.num]) {
					continue;
				}

				candidate.isBusy = true;
				candidate.workID = wID;
				candidate.intel = candidate.intel + cTime - candidate.time;
				candidate.time = cTime;
				numSum += candidate.num;
				cnt++;
				robots.add(candidate);
			}
		}
		//저지능우선
		else {
			while(cnt<mNum) {
				robot candidate = minHeap.poll();
				if(candidate.isBusy || candidate.isBroken || candidate.version != version[candidate.num]) {
					continue;
				}

				
				candidate.isBusy = true;
				candidate.workID = wID;
				candidate.intel = candidate.intel + cTime - candidate.time;
				candidate.time = cTime;
				numSum += candidate.num;
				cnt++;
				robots.add(candidate);
			}
		}
		
		//3.일 목록에 투입
		System.out.println(cTime+"투입 : "+robots);
		workMap.put(wID, robots);
		
		return numSum;
	}

	public void returnJob(int cTime, int wID)
	{
		List<robot> robots = workMap.get(wID);
		
		for(robot robot : robots) {
			if(robot.workID != wID)continue; // 조퇴한 경우는 제외
			robot newRobot = new robot(robot.num);
			newRobot.copy(robot);
			newRobot.version = ++version[robot.num];
			newRobot.time = cTime;
			newRobot.isBusy = false;
			newRobot.workID = -1;
			robotList.set(newRobot.num-1, newRobot);
			minHeap.add(newRobot);
			maxHeap.add(newRobot);
		}
		System.out.println(cTime+"복귀"+robots);
        workMap.remove(wID); 
	}

	public void broken(int cTime, int rID)
	{
		robot targetRobot = robotList.get(rID-1);
		if(targetRobot.isBroken) System.out.println(rID+"이미 고장난 로봇");
		else if(!targetRobot.isBusy) System.out.println(rID+"센터에 있는 로봇은 고장나지 않는다.");
		if(targetRobot.isBroken || !targetRobot.isBusy) return;
		System.out.println(rID+"고장");
		targetRobot.workID = -1;
		targetRobot.isBroken = true;
		targetRobot.isBusy = false;	
//		minHeap.remove(targetRobot);
//		maxHeap.remove(targetRobot);
	}

	public void repair(int cTime, int rID)
	{
		robot targetRobot = robotList.get(rID-1);

		if(!targetRobot.isBroken) {
			System.out.println(rID+"고장나지 않은 로봇은 수리할 수 없다.");
			return;
		}
		robot newRobot = new robot(rID);
		newRobot.copy(targetRobot);
		newRobot.time = cTime;
		newRobot.isBroken = false;
		newRobot.isBusy = false;
		newRobot.intel = 0;
		robotList.set(rID-1, newRobot);
		System.out.println(cTime+":"+rID+"수리");
		newRobot.version = ++version[targetRobot.num];
		minHeap.add(newRobot);
		maxHeap.add(newRobot);
	}

	public int check(int cTime, int rID)
	{
		robot targetRobot = robotList.get(rID-1);
		//고장이면 0, 일이면 작업id의 -1 , 대기중이면 지능지수
		if(targetRobot.isBroken) {
			return 0;
		}
		if(targetRobot.isBusy) {
			return -1*targetRobot.workID;
		}
		//고장도 아니고, 일하는 중도 아니다.
		//지능 지수를 계산해서 준다.
		int intel = targetRobot.intel + cTime - targetRobot.time;
		
		return intel;
	}
}