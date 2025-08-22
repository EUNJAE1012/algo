package SWEA17433;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/* SWEA : 17433 성적조회
 * 성적 추가시 학생 ID,학년,성별,점수가 주어진다. 
 *  -> 그 그룹에서 가장 높은 성적ID 반환,동점시 가장 큰 ID(2만회)
 * 성적 삭제시 학생 ID가 주어진다.
 *  -> 그 그룹에서 가장 낮은 성적ID반환 동점시 가장 작은 ID, 비었으면 0 ->6만회이하
 * 성적 조회시, 학년(1~3) 성별(남/여), 특정 점수 가 주어진다
 *  -> 그 점수 이상의 학생중 가장 낮은 성적 ID 반환, 동점시 가장 작은 ID->6만회이하
 * 예) {1,3}학년, {여성}성별, 80점이상 입력
 * 
 * 
 * 10억학생 조회 성능을 올린다 = B Tree?
 * 인덱스 3개
 * 학년, 성별은 union find로 6개그룹으로 나누고 끝값끼리 연결?
 * ArrayList<student>[3][2]
 * 
 * 1. 애초에 삽입을 할때부터 이분탐색으로 성적순으로 넣어주자.
 * 
 * 
 * 
 */
class Solution{
	private final static int CMD_INIT = 100;
	private final static int CMD_ADD = 200;
	private final static int CMD_REMOVE = 300;
	private final static int CMD_QUERY = 400;

	private final static UserSolution usersolution = new UserSolution();

	private static void String2Char(char[] buf, String str) {
		for (int k = 0; k < str.length(); ++k)
			buf[k] = str.charAt(k);
		buf[str.length()] = '\0';
	}
	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int id, grade, score;
		int cmd, ans, ret;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					usersolution.init();
					okay = true;
					break;
				case CMD_ADD:
					char[] gender = new char[7];
					id = Integer.parseInt(st.nextToken());
					grade = Integer.parseInt(st.nextToken());
					String2Char(gender, st.nextToken());
					score = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					System.err.println(String.format("추가 id:%d,grade:%d,gender:%d,score:%d 정답:%d"
							, id,grade,(gender[0]=='f')?0:1,score,ans));
					ret = usersolution.add(id, grade, gender, score);
					System.err.println("결과 : "+ret);
					if (ret != ans) {
						okay = false;
						return okay;
					}
						
					break;
				case CMD_REMOVE:
					id = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					System.err.println("삭제 id : "+id+" 정답 : "+ans);
					ret = usersolution.remove(id);
					System.err.println("결과 : "+ret);
					if (ret != ans) {
						okay = false;
						return okay;
					}
						
					break;
				case CMD_QUERY:
					int gradeCnt, genderCnt;
					int[] gradeArr = new int[3];
					char[][] genderArr = new char[2][7];
					gradeCnt = Integer.parseInt(st.nextToken());
					for (int j = 0; j < gradeCnt; ++j) {
						gradeArr[j] = Integer.parseInt(st.nextToken());
					}
					genderCnt = Integer.parseInt(st.nextToken());
					for (int j = 0; j < genderCnt; ++j) {
						String2Char(genderArr[j], st.nextToken());
					}
					score = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					System.err.println(String.format("쿼리 학년 :%s 성별: %d %c, 기준점수 :%d, 정답 : %d",Arrays.toString(gradeArr),genderCnt,genderArr[0][0],score,ans));
					ret = usersolution.query(gradeCnt, gradeArr, genderCnt, genderArr, score);
					System.err.println("결과 : "+ret);
					if (ret != ans) {
						okay = false;
						return okay;
					}
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

//		System.setIn(new java.io.FileInputStream("C:\\Users\\SSAFY\\Downloads\\si.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
}

class UserSolution {
	
	ArrayList<Student>[][] students;
	Map<Integer,Student> studentMap;
	static class Student implements Comparable<Student>{
		int id;  //1~10억
		int grade; //1~3
		int gender; //0여성 1남성
		int score;//0~30만
		
		@Override
		public boolean equals(Object obj) {
			return id == ((Student)obj).id;
		}
		
		Student(int id,int grade,int gender, int score){
			this.id = id;
			this.grade = grade;
			this.gender = gender;
			this.score = score;
		}
		
		@Override
		public int compareTo(Student o) {
			return Integer.compare(grade, o.grade);
		}
		@Override
		public String toString() {
			return id+"("+score+") ";
		}
	}
	
	public void init() {
		studentMap = new HashMap<>();
		students = new ArrayList[3][2];
		for(int grade=0;grade<3;grade++) {
			for(int gender=0; gender<2;gender++) {
				students[grade][gender] = new ArrayList<>();
			}
		}
	}
	
	public int add(int mId, int mGrade, char[] mGender,int mScore) {
		int gender = (mGender[0]=='f')?0:1;
		Student student = new Student(mId, mGrade, gender, mScore);
		studentMap.put(mId, student);
		int index = binarySearch(mGrade,gender,mScore,mId);
		ArrayList<Student> group = students[mGrade-1][gender];
		group.add(index, student);
//		System.out.println("학년 : "+mGrade+" 성별: "+mGender[0]+group);
		return group.get(group.size()-1).id;
	}
	
	//insert 용 binarySearch insert될 index 반환
	public int binarySearch(int grade, int gender, int score,int id) {
		int index = -1;
		ArrayList<Student> group = students[grade-1][gender];
		int l = 0; 
		int r = group.size()-1;
		int m = (l+r)>>1;
		while(l<=r) {
			m = (l+r)>>1;
			Student current = group.get(m);
			int currentScore = current.score;
			//밑으로 가야함
			if(score<currentScore) {
				r=m-1;
			}
			//위로가야함
			else if(score>currentScore) {
				l=m+1;
			}
			//같은 점수 발견
			else {
			    // 같은 점수 구간에서 삽입할 ID보다 큰 첫 번째 위치 찾기
			    int left = m, right = m;
			    
			    // 같은 점수 구간의 시작점 찾기
			    while(left > 0 && group.get(left-1).score == score) {
			        left--;
			    }
			    
			    // 같은 점수 구간의 끝점 찾기  
			    while(right < group.size()-1 && group.get(right+1).score == score) {
			        right++;
			    }
			    
			    // left~right 구간에서 삽입할 ID보다 큰 첫 번째 ID 위치 찾기
			    for(int i = left; i <= right; i++) {
			        if(group.get(i).id > id) {
			            return i;
			        }
			    }
			    
			    // 모든 ID가 삽입할 ID보다 작으면 맨 뒤에 삽입
			    return right + 1;
			}
		}
		index=l;
		return index;
	}
	
	/*
	 *  * 성적 삭제시 학생 ID가 주어진다.
 *  -> 그 그룹에서 가장 낮은 성적ID반환 동점시 가장 작은 ID, 비었으면 0 ->6만회이하
	 */
	public int remove(int mId) {
		Student student = studentMap.get(mId);
		if(student==null) return 0; //기록 x = 0
		int gender = student.gender;
		int grade = student.grade;
		ArrayList<Student> group = students[grade-1][gender];
//		System.out.println(gender+"성별,학년"+grade);
//		System.out.println("삭제전 : "+group);
		group.remove(student); //이게맞나? 기록삭제
		studentMap.remove(mId); //기록 삭제2
//		System.out.println("삭제후 : "+group);
		int ans = (group.size()>0)? group.get(0).id:0;
		return ans;
	}

	int[] lowerBound(int grade, int gender, int upper,int lower) { //[0]upper 이상, lower 이하중 가장 작은 점수 ,못찾으면 -1  [1] = 그때의id 
//		System.out.println("lowerBound!"+(grade+1)+"학년의,"+gender+"애서  "+upper+"이상"+lower+"이하");
		int[] result = new int[2];
		ArrayList<Student> group = students[grade][gender];
		int l = 0;
		int r = group.size()-1;
		int m = (l+r)>>1;
		int index =-1;
		while(l<=r) {
			m = (l+r)>>1;
			Student cur = group.get(m);
			if(cur.score>lower) {
				r= m-1;
				continue;
			}
			if(cur.score<upper) {
				l= m+1;
				continue;
			}
			//일단 범위내엔 안착, 여기서 가장 작은 점수를 찾아야 함
			index = m;
			while(index>l) {
				//더 작은 값이 없다면 지금 값 반환
				//더 작은 값이 범위를 나간다면 지금 값 반환
				if(index==0 || group.get(index-1).score<upper) {
					return new int[] {group.get(index).score,group.get(index).id};
				}

				//아니라면 더 작은 값을 찾아 이동
				index--;
				
			}
			break;
		}
		result[0] = (index==-1)?-1:group.get(index).score;
		result[1] = (index==-1)?-1:group.get(index).id;
		return result;
	}
	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		
		int minId = 0;
		int minScore = 300001;
		for(int grade1 : mGrade) {
			int grade=grade1-1;
			if(grade1==0) continue;
			for(int i =0; i<mGenderCnt;i++) {
				int gen = 1;
				if(mGender[i][0]=='f') {
					gen= 0;
				}
				System.out.println(students[grade][gen]);
				int[] result = lowerBound(grade,gen,mScore,minScore);
				System.out.println(String.format("%d(%d)", result[1],result[0]));
				if(result[0] == -1) continue;
				if(minScore== result[0]) {
					minId = Math.min(minId, result[1]);
					continue;
				}
				else if (minScore > result[0]){
					minId = result[1];
					minScore =result[0];
				}

			}
		}
		
		
		return minId;
	}
	
	
}
