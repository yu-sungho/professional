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
	 * C : �Ÿ��� �� , B : �ٸ� ����, R : �Ÿ�
	 * North : ������ �Ÿ��� ����
	 * South : ������ �Ÿ��� ����
	 * nDp[C][B] : ������ �Ÿ� �� �ٸ� �ǳ� Ƚ���� ���� �� ����
	 * sDp[C][B] : ������ �Ÿ� �� �ٸ� �ǳ� Ƚ���� ���� �� ���� 
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
             * �迭 1���� �����ϱ� ���� ���� +1.
             * �ٸ��� �ǳʴ� Ƚ���� 0 ���� �ִ� B���� �̹Ƿ� B+1.
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
             * �ּҰ��� ���ϴ� �����̹Ƿ� 
             * ���ϰ� ������ ���� �����ϴ� �迭�� ���ؼ� Integer�� MAX�� �ʱ� ����
             */
            for(int l=1; l<=C; l++) {
                for(int m=0; m<=B; m++) {
                    nDp[l][m] = Integer.MAX_VALUE;
                    sDp[l][m] = Integer.MAX_VALUE;
                }
            }

            /*
             * �ּҰ� ���ϱ�
             */
            solve();

            
            /*
             * ������������ ���� ���ܿ��� ���� �ϹǷ�
             * sDp[C][B]���� ���Ѱ����� �ּҰ��� ã���� ��.
             * ������ ���� ������ġ���� �ٸ��� �ǳ�ȸ�� 0 ~ B������ �ּҰ� ���
             */
            long sum = Long.MAX_VALUE;
            for(int k=0; k<=B; k++) {
                sum = Math.min(sum, sDp[C][k]);
            }

            /*
             * ���� ����� : �ּҰ� + �����Ÿ����� - ������ ���� ���� ��ġ�� �Ÿ� 
             */
            System.out.println("#" + i + " " + (sum + (R - South[C])));

        }

    }

    private static void solve() {
    	/*
    	 * ù��° �ٸ������� �Ÿ����� ����
    	 * ������ ù��° : �ٸ��� �ǳ��� ��������̹Ƿ� B=0���� ����
    	 * ������ ù��° : �ٸ��� �ѹ� �ǳ� ����̹Ƿ� B=1�� ����
    	 */
        nDp[1][0] = North[1];
        sDp[1][1] = North[1];
        /*
         * ù��° �ٸ��� ���� ���� �ʱⰪ���� ������ ��������� 
         * 2��° �ٸ����� ������ ����
         */
        for(int i=2; i<=C; i++) {
            for(int j=0; j<=B; j++) {
            	/*
            	 * ���� �ϴ��� ���� �ٸ��� �ǳʴ� Ƚ���� ¦��
            	 */
                if(j % 2 == 0) {
                	//�ٸ��� �ѹ��� �ǳ��� �ʴ� ��쿡 ���� ��
                	//���� ��ġ�� �� : �� �� ��ġ�� �� + (���� ��ġ�� �Ÿ� - �� �� ��ġ�� �Ÿ�)
                    if(j == 0) {
                        nDp[i][j] = nDp[i-1][j] + (North[i] - North[i-1]);
                        continue;
                    }
                    //���� �̵��� ��ġ�� �ٸ��� �ǳ� Ƚ������ ������ ��쿡 ���� ��
                	//�� ���� �ϴ���ġ �� : �� �� ��ġ�� �� ������ ��(�ٸ��� j-1�� ��ġ�� ����) + (�� ���� ���� ��ġ�� - �� �� ��ġ�� �� ���� ��ġ��)
                    if(i == j) {
                        nDp[i][j] = sDp[i-1][j-1] + (South[i] - South[i-1]);
                        continue;
                    }
                    
                    //�� ���� ���
                    //���� ��ġ�� : MIN (�� �� ��ġ�� �� + (�� ���� ��ġ - �� �� ��ġ�� ��), �� ��ġ�� ���ܰ�(�ٸ��� j-1�� ��ġ�� ����) + (�� ��ġ�� ���ܰ� - ���� ��ġ�� ���ܰ�))
                    nDp[i][j] = Math.min(nDp[i-1][j] + (North[i] - North[i-1]), sDp[i-1][j-1] + (South[i] - South[i-1]));
                }
                /*
            	 * ���� ������ ��� �ٸ��� �ǳʴ� Ƚ���� Ȧ��
            	 */
                else {
                	//�ٸ��� �ѹ� �ǳ� ��쿡 ���� ��
                	//���� ��ġ �� : MIN(�� �� ��ġ�� �� + (���� ��ġ�� �Ÿ� - �� �� ��ġ�� �Ÿ�), ���� ��ġ�� �� �ϴ��� ��)
                    if(j == 1) {
                        sDp[i][j] = Math.min(sDp[i-1][j] + (South[i] - South[i-1]), nDp[i][j-1]);
                        continue;
                    }
                    //���� �̵��� ��ġ�� �ٸ��� �ǳ� Ƚ������ ������ ��쿡 ���� ��
                	//�� ���� ������ġ �� : �� �� ��ġ�� �� �ϴ��� ��(�ٸ��� j-1�� ��ġ�� ����) + (�� ���� �ϴ� ��ġ�� - �� �� ��ġ�� �� �ϴ� ��ġ��)
                    if(i == j) {
                        sDp[i][j] = nDp[i-1][j-1] + (North[i] - North[i-1]);
                        continue;
                    }
                    //�� ���� ���
                    //���� ��ġ�� : MIN (�� �� ��ġ�� �� + (�� ���� ��ġ - �� �� ��ġ�� ��), �� ��ġ�� �ϴܰ�(�ٸ��� j-1�� ��ġ�� ����) + (�� ��ġ�� �ϴܰ� - ���� ��ġ�� �ϴܰ�))
                    sDp[i][j] = Math.min(sDp[i-1][j] + (South[i] - South[i-1]), nDp[i-1][j-1] + (North[i] - North[i-1]));
                }
            }

        }



    }

}
