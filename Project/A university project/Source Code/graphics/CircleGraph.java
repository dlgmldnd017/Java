
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

class CircleGraph extends JPanel {
	private String area;

	private double num[] = {0, 0, 0, 0, 0, 0}; // ���������� ��
	private double relative_num[] = {0, 0, 0, 0, 0, 0};
	//private double max[] = {0.060, 0.060, 3, 0.050, 200, 130}; // ������������ �ִ밪
	
	private int count = 0;
	// �׷��� ��ǥ, ������
	private static int pos_x = 50;
	private static int pos_y = 20;
	private static int radius = 200;
	// �� �׷����� �߽���ǥ
	private static int c_x = pos_x + radius/2;
	private static int c_y = pos_y + radius/2;
	
	private double pai = 3.14;

	private String[] suf = {"(ppm)", "(ppm)","(ppm)","(ppm)","(��/��)","(��/��)",};
	private Color[] colorL = {Color.YELLOW, Color.red, Color.blue, Color.green, Color.orange, Color.magenta};

	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, Constant.dial_W, Constant.dial_H);
		//���� ���ٸ�(-1) ���ܽ�Ŵ
		for(int i=0; i<6; i++) {
			if(num[i] <= 0) {
				num[i] = 0;
			}
		}
		
		
		// num ���� ��밪ȭ ��Ų��.
		for (int i=0; i<6; i++) {
			relative_num[i] = num[i] / Constant.pollut_max[i] * 100;
		}	
		//��ü ���� ���Ѵ�
		double total = 0;
		for (int i=0; i<6; i++) {
			total += relative_num[i];
		}
		if (total == 0)
			return;
		// ��ü������ ������ ����. ������ ��
		int arc[] = {0, 0, 0, 0, 0, 0};
		for(int i=0; i<6; i++) {
			arc[i] = (int) ((int) 360.0 * relative_num[i] / total);
		}
		// ȣ �׸���. (���۰�, ����) - ���߿� for��ȭ ��ų��
		g.setColor(Color.YELLOW);
		g.fillArc(pos_x, pos_y, radius, radius, 0, arc[0]);
		g.setColor(Color.RED);
		g.fillArc(pos_x, pos_y, radius, radius, arc[0], arc[1]);
		g.setColor(Color.BLUE);
		g.fillArc(pos_x, pos_y, radius, radius, arc[0] + arc[1], arc[2]);
		g.setColor(Color.GREEN);
		g.fillArc(pos_x, pos_y, radius, radius, arc[0] + arc[1] + arc[2], arc[3]);
		g.setColor(Color.ORANGE);
		g.fillArc(pos_x, pos_y, radius, radius, arc[0] + arc[1] + arc[2] + arc[3], arc[4]/*360 - (arc1 + arc2 + arc3 + arc4)*/);
		g.setColor(Color.magenta);
		g.fillArc(pos_x, pos_y, radius, radius, arc[0] + arc[1] + arc[2] + arc[3] + arc[4], 360 - (arc[0] + arc[1] + arc[2] + arc[3] + arc[4]));
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillArc(pos_x+15, pos_y+15, radius-30, radius-30, 0, 360);
		
		
		for(int i=0; i<6; i++) {
			drawLabel(num[i], c_x, c_y, arc, arc[i], g);
		}
		count = 0;

		// ����
		g.setColor(Color.BLACK);
		g.setFont(new Font("���� ���", Font.BOLD, 20));
		g.drawString(area, 300, 50);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		
		// ��
		for(int i=0; i<6; i++) {
			g.drawString(Constant.pollut[i] + suf [i] + condition(i), 330, 100 + (i*20));
			g.setColor(colorL [i]);
			g.fillRect(300, 85 + (i*20), 18, 18);
			g.setColor(Color.BLACK);
		}		
	}
	
	private String condition(int i) {
		// Constant.pollut[i] �� �󵵰� ����ġ�� ��������ΰ�?
		String result;
		double[] lv = {0, 0, 0};
		switch (i) {
		case 0:
			lv = Constant.nppm_lv;
			break;
		case 1:
			lv = Constant.oppm_lv;
			break;
		case 2:
			lv = Constant.cppm_lv;
			break;
		case 3:
			lv = Constant.appm_lv;
			break;
		case 4:
			lv = Constant.dust_lv;
			break;
		case 5:
			lv = Constant.mdust_lv;
			break;
		}
		
		if (num[i] < lv[0]) {
			result = "   ����";
		} else if(num[i] < lv[1]) {
			result = "   ����";
		} else if(num[i] < lv[2]) {
			result = "   ����";
		} else {
			result = "   �ſ쳪��";
		}
		return result;
	}

	// �߽��� x, y�̰� ���հ��� mid_arc�� ���� ���� �ٿ��ִ� �Լ�
	private void drawLabel(double num, int x, int y, int arcs[], int arc, Graphics g) {
		int result_x, result_y;
		// ���ݱ����� ������ �� + �̹��� �׸� ���� ���հ�
		int sum = 0;
		for(int i = 0; i < count; i++) {
			sum += arcs[i];
		}
		count++;
		int res_arc = (sum + arc/2);
		
		result_x = (int)( c_x + (Math.cos((res_arc) * (pai/180)) * radius/3));
		result_y = (int)( c_y - (Math.sin((res_arc) * (pai/180)) * radius/3));
		g.setColor(Color.BLACK);
		g.setFont(new Font("���� ���", Font.PLAIN, 20));
		g.drawString(Double.toString(num), result_x , result_y);
		
	}
	
	public void setNumbers(double num1, double num2, double num3, double num4, double num5, double num6) {
		num[0] = num1;
		num[1] = num2;
		num[2] = num3;
		num[3] = num4;
		num[4] = num5;
		num[5] = num6;
	}
	
	public void setName(String in) {
		this.area = in;
	}

}