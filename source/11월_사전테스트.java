//package prevTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by yusungho on 2016. 10. 21..
 */
public class Solution {

	/*
	 * C : 거리의 수 , B : 다리 개수, R : 거리
	 * North : 강북의 거리값 저장
	 * South : 강남의 거리값 저장
	 * nDp[C][B] : 강북의 거리 별 다리 건넌 횟수에 따른 값 저장
	 * sDp[C][B] : 강남의 거리 별 다리 건넌 횟수에 따른 값 저장 
	 */
    private static int C, B, R;		
    private static int[] North;	
    private static int[] South;	
    private static int[][] nDp;	
    private static int[][] sDp;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        for(int i=1; i<=T; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            C = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            /* 
             * 배열 1부터 시작하기 위해 개수 +1.
             * 다리를 건너는 횟수는 0 부터 최대 B까지 이므로 B+1.
             */
            North = new int[C+1];
            South = new int[C+1];
            nDp = new int[C+1][B+1];
            sDp = new int[C+1][B+1];
            StringTokenizer st_road = new StringTokenizer(br.readLine().trim());
            for(int j=1; j<=C; j++) {
                North[j] = Integer.parseInt(st_road.nextToken());
            }
            st_road = new StringTokenizer(br.readLine().trim());
            for(int k=1; k<=C; k++) {
                South[k] = Integer.parseInt(st_road.nextToken());
            }

            /*
             * 최소값을 구하는 문제이므로 
             * 강북과 강남의 값을 저장하는 배열에 대해서 Integer의 MAX값 초기 셋팅
             */
            for(int l=1; l<=C; l++) {
                for(int m=0; m<=B; m++) {
                    nDp[l][m] = Integer.MAX_VALUE;
                    sDp[l][m] = Integer.MAX_VALUE;
                }
            }

            /*
             * 최소값 구하기
             */
            solve();

            
            /*
             * 최종목적지가 강의 남단에서 가야 하므로
             * sDp[C][B]에서 구한값들의 최소값을 찾으면 됨.
             * 마지막 강의 남단위치에서 다리를 건넌회수 0 ~ B까지중 최소값 출력
             */
            long sum = Long.MAX_VALUE;
            for(int k=0; k<=B; k++) {
                sum = Math.min(sum, sDp[C][k]);
            }

            /*
             * 최종 결과값 : 최소값 + 최종거리에서 - 마지막 강의 남단 위치의 거리 
             */
            System.out.println("#" + i + " " + (sum + (R - South[C])));

        }

    }

    private static void solve() {
    	/*
    	 * 첫번째 다리에서의 거리값을 셋팅
    	 * 강북의 첫번째 : 다리를 건너지 않은경우이므로 B=0으로 셋팅
    	 * 강남의 첫번째 : 다리를 한번 건넌 경우이므로 B=1로 셋팅
    	 */
        nDp[1][0] = North[1];
        sDp[1][1] = North[1];
        /*
         * 첫번째 다리에 대한 값은 초기값으로 셋팅을 해줬으모로 
         * 2번째 다리부터 데이터 저장
         */
        for(int i=2; i<=C; i++) {
            for(int j=0; j<=B; j++) {
            	/*
            	 * 강의 북단의 경우는 다리를 건너는 횟수가 짝수
            	 */
                if(j % 2 == 0) {
                	//다리를 한번도 건너지 않는 경우에 대한 값
                	//현재 위치의 값 : 그 전 위치의 값 + (현재 위치의 거리 - 그 전 위치의 거리)
                    if(j == 0) {
                        nDp[i][j] = nDp[i-1][j] + (North[i] - North[i-1]);
                        continue;
                    }
                    //현재 이동한 위치와 다리를 건넌 횟수값이 동일한 경우에 대한 값
                	//현 강의 북단위치 값 : 그 전 위치의 강 남단의 값(다리를 j-1한 위치의 값임) + (현 강의 남단 위치값 - 그 전 위치의 강 남단 위치값)
                    if(i == j) {
                        nDp[i][j] = sDp[i-1][j-1] + (South[i] - South[i-1]);
                        continue;
                    }
                    
                    //그 외의 경우
                    //현재 위치값 : MIN (그 전 위치의 값 + (현 강의 위치 - 그 전 위치의 값), 전 위치의 남단값(다리를 j-1한 위치의 값임) + (현 위치의 남단값 - 이전 위치의 남단값))
                    nDp[i][j] = Math.min(nDp[i-1][j] + (North[i] - North[i-1]), sDp[i-1][j-1] + (South[i] - South[i-1]));
                }
                /*
            	 * 강의 남단의 경우 다리를 건너는 횟수가 홀수
            	 */
                else {
                	//다리를 한번 건넌 경우에 대한 값
                	//현재 위치 값 : MIN(그 전 위치의 값 + (현재 위치의 거리 - 그 전 위치의 거리), 현재 위치의 강 북단의 값)
                    if(j == 1) {
                        sDp[i][j] = Math.min(sDp[i-1][j] + (South[i] - South[i-1]), nDp[i][j-1]);
                        continue;
                    }
                    //현재 이동한 위치와 다리를 건넌 횟수값이 동일한 경우에 대한 값
                	//현 강의 남단위치 값 : 그 전 위치의 강 북단의 값(다리를 j-1한 위치의 값임) + (현 강의 북단 위치값 - 그 전 위치의 강 북단 위치값)
                    if(i == j) {
                        sDp[i][j] = nDp[i-1][j-1] + (North[i] - North[i-1]);
                        continue;
                    }
                    //그 외의 경우
                    //현재 위치값 : MIN (그 전 위치의 값 + (현 강의 위치 - 그 전 위치의 값), 전 위치의 북단값(다리를 j-1한 위치의 값임) + (현 위치의 북단값 - 이전 위치의 북단값))
                    sDp[i][j] = Math.min(sDp[i-1][j] + (South[i] - South[i-1]), nDp[i-1][j-1] + (North[i] - North[i-1]));
                }
            }

        }



    }

}
