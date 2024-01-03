import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

// ��� ���� Ŭ����
public class Constant {
	// ��������
	static final String[] pollut = { "�̻�ȭ����", "������", "�̻�ȭź��", "��Ȳ�갡��", "�̼�����", "�ʹ̼�����" };
	// ���������� �ִ밪
	//static final double pollut_max[] = {0.060, 0.060, 3, 0.050, 200, 130};
	//static final double pollut_max[] = {0.060, 0.050, 3, 0.045, 180, 110};
	static final double pollut_max[] = {0.200, 0.150, 15, 0.150, 180, 90};
	
	static final String header[] = { "����", "��¥", Constant.pollut[0], Constant.pollut[1], Constant.pollut[2],
			Constant.pollut[3], Constant.pollut[4], Constant.pollut[5] };

	
	/*	������ ���� ����
	 * �̻�ȭ���� : 0.000 ~ 0.150 // 0.06 
	 * ������ : 0.000 ~ 0.300 // 0.06 ����ġ 
	 * �̻�ȭź�� : 0 ~ 3
	 * // ��Ȳ�갡�� : 0.001 ~ 0.150 // 0.05 
	 * �̼����� : 0 ~ 200 int�� // 100 
	 * �ʹ̼����� : 0 ~ 130 int�� // 35
	 */
	
	/*	�ǰ���� - ��ó : �ѱ�ȯ�����.
	 * �Ķ� - ��� - ��� - ����
	 * �̻�ȭ���� : 0~0.030 ����, 0.031~0.060 ����, 0.061~0.200 ����, 0.201~ �ſ쳪��
	 * ������    : 0~0.030 ����, 0.030~0.090 ����, 0.091~0.150 ����, 0.151~ �ſ쳪��
	 * �̻�ȭź�� : 0~2 ����, 2~9 ����, 9~15 ����, 15~ �ſ쳪��
	 * ��Ȳ�갡�� : 0~0.020 ����, 0.021~0.050 ����, 0.051~0.150 ����, 0.151~ �ſ쳪��
	 * �̼����� : 0~30 ����, 31~80 ����, 81~150 ����, 151~ �ſ쳪��
	 * �ʹ̼����� : 0~15 ����, 16~35 ����, 36~75 ����, 76~ �ſ쳪��
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
	
	static final String nppmS = "�̻�ȭ���� : 0-0.03 ����      0.03-0.06 ����      0.06-0.20 ����      0.20- �ſ쳪��";
	static final String oppmS = "����   �� : 0-0.03 ����      0.03-0.09 ����      0.09-0.15 ����      0.15- �ſ쳪��";
	static final String cppmS = "�̻�ȭź�� : 0-2 ����      2-9 ����      9-15 ����      15- �ſ쳪��";
	static final String appmS = "��Ȳ�갡�� : 0-0.02 ����      0.02-0.05 ����      0.05-0.15 ����      0.15- �ſ쳪��";
	static final String dustS = "�̼�   ���� : 0-30 ����      30-80 ����      80-150 ����      150- �ſ쳪��";
	static final String mdustS = "�ʹ̼����� : 0-15 ����      15-35 ����      35-75 ����      75- �ſ쳪��";
		
	// �������
	static final String[] locations = { "��ü", "������", "�������", "������", "�����Ϸ�", "���ϱ�", "������", "���״��", "���Ǳ�", "���ǻ�", "������",
			"���α�", "�õ�", "��õ��", "����", "�����", "������", "������", "���빮��", "���۱�", "���۴��", "������", "���ѻ�", "���빮��", "���ʱ�", "������",
			"���ϱ�", "����", "���ı�", "������", "���̷�", "��õ��", "��������", "��������", "��걸", "����", "������", "����", "���α�", "�߱�", "�߶���",
			"õȣ���", "û��õ��", "�Ѱ����", "����", "ȫ����", "ȭ����" };
	static final String[] location_total = {"��ü"};

	// ��ȭ���� ������
	static final int dial_W = 600;
	static final int dial_H = 350;
		
	// polygon points

	static final String[] s1 = {"�����Ϸ�", "����", "������", "���ѻ�", "���빮��", "���̷�", "��걸", "����", "����", "���α�", "�߱�", "û��õ��", "�Ѱ����", "����"  };
	static final int[] s1_xp = {65, 105, 95, 160, 195, 244, 240, 195};
	static final int[] s1_yp = {148, 120, 78, 60, 153, 177, 232, 264};
	static final int s1_num = 8;
	
	static final String[] s2 = {"���ϱ�", "�����", "������", "���ѻ�", "���ϱ�", "������", "ȭ����"};
	static final int[] s2_xp = {175, 195, 244, 387, 378, 360, 352, 315, 249, 210, 202    };
	static final int[] s2_yp = {92, 153, 177, 130, 99, 99, 49, 45, 25, 41, 78    };
	static final int s2_num = 11;
	
	static final String[] s3 = {"�����Ϸ�", "������", "���빮��", "������", "�߶���", "õȣ���", "ȫ����" };
	static final int[] s3_xp = {244, 240, 270, 342, 432, 408, 439, 387  };
	static final int[] s3_yp = {177, 232, 227, 253, 213, 194, 131, 130 };
	static final int s3_num = 8;
	
	static final String[] s4 = {"������", "���ı�" };
	static final int[] s4_xp = {342, 344, 409, 425, 520, 492, 540, 560, 556, 522, 443 };
	static final int[] s4_yp = {280, 322, 345, 366, 322, 282, 227, 224, 175, 167, 238  };
	static final int s4_num = 11;
	
	static final String[] s5 = {"������", "�������", "���Ǳ�", "���ǻ�", "������", "���۱�", "���۴��", "���ʱ�", "����"  };
	static final int[] s5_xp = {342, 344, 409, 425, 357, 330, 300, 247, 252, 232, 168, 109, 157, 195, 219, 266  };
	static final int[] s5_yp = {280, 322, 345, 366, 401, 385, 426, 403, 376, 372, 418, 332, 265, 287, 288, 252 };
	static final int s5_num = 16;
	
	static final String[] s6 = {"������", "���״��", "���α�", "�õ�", "��õ��", "������", "��õ��", "��������"  };
	static final int[] s6_xp = {157, 109, 154, 117, 56, 19,  10,  32,  11,  54 };
	static final int[] s6_yp = {265, 332, 403, 431, 335, 355, 332, 243, 212, 167 };
	static final int s6_num = 10;
	
	static final ArrayList<String[]> sectorList = new ArrayList<String[]>(
			Arrays.asList(s1, s2, s3, s4, s5, s6));
	
	

}
