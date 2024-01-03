import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

// 상수 정의 클래스
public class Constant {
	// 오염물질
	static final String[] pollut = { "이산화질소", "오존농도", "이산화탄소", "아황산가스", "미세먼지", "초미세먼지" };
	// 오염물질의 최대값
	//static final double pollut_max[] = {0.060, 0.060, 3, 0.050, 200, 130};
	//static final double pollut_max[] = {0.060, 0.050, 3, 0.045, 180, 110};
	static final double pollut_max[] = {0.200, 0.150, 15, 0.150, 180, 90};
	
	static final String header[] = { "지역", "날짜", Constant.pollut[0], Constant.pollut[1], Constant.pollut[2],
			Constant.pollut[3], Constant.pollut[4], Constant.pollut[5] };

	
	/*	데이터 상의 범위
	 * 이산화질소 : 0.000 ~ 0.150 // 0.06 
	 * 오존농도 : 0.000 ~ 0.300 // 0.06 기준치 
	 * 이산화탄소 : 0 ~ 3
	 * // 아황산가스 : 0.001 ~ 0.150 // 0.05 
	 * 미세먼지 : 0 ~ 200 int형 // 100 
	 * 초미세먼지 : 0 ~ 130 int형 // 35
	 */
	
	/*	권고기준 - 출처 : 한국환경공단.
	 * 파랑 - 녹색 - 노랑 - 빨강
	 * 이산화질소 : 0~0.030 좋음, 0.031~0.060 보통, 0.061~0.200 나쁨, 0.201~ 매우나쁨
	 * 오존농도    : 0~0.030 좋음, 0.030~0.090 보통, 0.091~0.150 나쁨, 0.151~ 매우나쁨
	 * 이산화탄소 : 0~2 좋음, 2~9 보통, 9~15 나쁨, 15~ 매우나쁨
	 * 아황산가스 : 0~0.020 좋음, 0.021~0.050 보통, 0.051~0.150 나쁨, 0.151~ 매우나쁨
	 * 미세먼지 : 0~30 좋음, 31~80 보통, 81~150 나쁨, 151~ 매우나쁨
	 * 초미세먼지 : 0~15 좋음, 16~35 보통, 36~75 나쁨, 76~ 매우나쁨
	 */
	static final Color good = new Color(135, 206, 250);
	static final Color nomal = new Color(60, 179, 113);
	static final Color bad = new Color(240, 230, 140);
	static final Color verybad = new Color(255, 69, 0);
	
	static final double[] nppm_lv = {0.030, 0.060, 0.200};
	static final double[] oppm_lv = {0.030, 0.090, 0.150};
	static final double[] cppm_lv = {2, 9, 15};
	static final double[] appm_lv = {0.020, 0.050, 0.150};
	static final double[] dust_lv = {30, 80, 150};
	static final double[] mdust_lv = {15, 35, 75};
	
	static final String nppmS = "이산화질소 : 0-0.03 좋음      0.03-0.06 보통      0.06-0.20 나쁨      0.20- 매우나쁨";
	static final String oppmS = "오존   농도 : 0-0.03 좋음      0.03-0.09 보통      0.09-0.15 나쁨      0.15- 매우나쁨";
	static final String cppmS = "이산화탄소 : 0-2 좋음      2-9 보통      9-15 나쁨      15- 매우나쁨";
	static final String appmS = "아황산가스 : 0-0.02 좋음      0.02-0.05 보통      0.05-0.15 나쁨      0.15- 매우나쁨";
	static final String dustS = "미세   먼지 : 0-30 좋음      30-80 보통      80-150 나쁨      150- 매우나쁨";
	static final String mdustS = "초미세먼지 : 0-15 좋음      15-35 보통      35-75 나쁨      75- 매우나쁨";
		
	// 지역목록
	static final String[] locations = { "전체", "강남구", "강남대로", "강동구", "강변북로", "강북구", "강서구", "공항대로", "관악구", "관악산", "광진구",
			"구로구", "궁동", "금천구", "남산", "노원구", "도봉구", "도산대로", "동대문구", "동작구", "동작대로", "마포구", "북한산", "서대문구", "서초구", "성동구",
			"성북구", "세곡", "송파구", "시흥대로", "신촌로", "양천구", "영등포구", "영등포로", "용산구", "은평구", "정릉로", "종로", "종로구", "중구", "중랑구",
			"천호대로", "청계천로", "한강대로", "행주", "홍릉로", "화랑로" };
	static final String[] location_total = {"전체"};

	// 대화상자 사이즈
	static final int dial_W = 600;
	static final int dial_H = 350;
		
	// polygon points

	static final String[] s1 = {"강변북로", "남산", "마포구", "북한산", "서대문구", "신촌로", "용산구", "은평구", "종로", "종로구", "중구", "청계천로", "한강대로", "행주"  };
	static final int[] s1_xp = {65, 105, 95, 160, 195, 244, 240, 195};
	static final int[] s1_yp = {148, 120, 78, 60, 153, 177, 232, 264};
	static final int s1_num = 8;
	
	static final String[] s2 = {"강북구", "노원구", "도봉구", "북한산", "성북구", "정릉로", "화랑로"};
	static final int[] s2_xp = {175, 195, 244, 387, 378, 360, 352, 315, 249, 210, 202    };
	static final int[] s2_yp = {92, 153, 177, 130, 99, 99, 49, 45, 25, 41, 78    };
	static final int s2_num = 11;
	
	static final String[] s3 = {"강변북로", "광진구", "동대문구", "성동구", "중랑구", "천호대로", "홍릉로" };
	static final int[] s3_xp = {244, 240, 270, 342, 432, 408, 439, 387  };
	static final int[] s3_yp = {177, 232, 227, 253, 213, 194, 131, 130 };
	static final int s3_num = 8;
	
	static final String[] s4 = {"강동구", "송파구" };
	static final int[] s4_xp = {342, 344, 409, 425, 520, 492, 540, 560, 556, 522, 443 };
	static final int[] s4_yp = {280, 322, 345, 366, 322, 282, 227, 224, 175, 167, 238  };
	static final int s4_num = 11;
	
	static final String[] s5 = {"강남구", "강남대로", "관악구", "관악산", "도산대로", "동작구", "동작대로", "서초구", "세곡"  };
	static final int[] s5_xp = {342, 344, 409, 425, 357, 330, 300, 247, 252, 232, 168, 109, 157, 195, 219, 266  };
	static final int[] s5_yp = {280, 322, 345, 366, 401, 385, 426, 403, 376, 372, 418, 332, 265, 287, 288, 252 };
	static final int s5_num = 16;
	
	static final String[] s6 = {"강서구", "공항대로", "구로구", "궁동", "금천구", "시흥대로", "양천구", "영등포구"  };
	static final int[] s6_xp = {157, 109, 154, 117, 56, 19,  10,  32,  11,  54 };
	static final int[] s6_yp = {265, 332, 403, 431, 335, 355, 332, 243, 212, 167 };
	static final int s6_num = 10;
	
	static final ArrayList<String[]> sectorList = new ArrayList<String[]>(
			Arrays.asList(s1, s2, s3, s4, s5, s6));
	
	

}
