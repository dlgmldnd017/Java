import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.time.LocalDate;

import javax.swing.JPanel;

public class BarGraph extends JPanel{
	
	private static final int bar_w = 10; // ����׷��� ��
	private int bar_h; // ����׷��� ����
	
	private static final int graph_pos_x = 40; // �׷��� Ʋ ������ǥ
	private static final int graph_pos_y = 40;
	private static final int graph_w = 420;	// �׷��� ��
	private static final int graph_h = 280;	// �׷��� ����
	
	private int s_x, s_y; // ���� ������ ��ǥ
	private int gap;
	
	private LocalDate date; // �׸� ��¥
	private String item; // �׸� ��������
	private int type; // Ÿ��
	
	private int plus = 1600;  // ����׷������� �߰��� �þ�� ��
	private int plus2 = 400;

	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, Constant.dial_W + plus, Constant.dial_H + plus2);
		
		setType(item);
		
		gap = (graph_w+plus) / (Constant.locations.length -1); // ����. 46������
		// �׷��� Ʋ
		g.setColor(Color.black);
		// x��
		g.drawLine(graph_pos_x, graph_pos_y+graph_h, graph_pos_x, graph_pos_y);
		for(int i=1; i < Constant.locations.length; i++) {
			g.drawLine(graph_pos_x+gap, graph_pos_y+graph_h, graph_pos_x+gap, graph_pos_y);
			g.drawString(Constant.locations[i], (gap), graph_pos_y + graph_h + 15);
			//gap += 420/6;
			gap += (graph_w + plus) / (Constant.locations.length -1);
		}
		
		
		// y��
		for(int i=0; i<10; i++) {
			//g.drawLine(graph_pos_x, graph_pos_y+(i*28), graph_pos_x+graph_w+plus, graph_pos_y+(i*28));
			g.drawLine(graph_pos_x, graph_pos_y+(i*28), graph_pos_x+graph_w+plus, graph_pos_y+(i*28));
			//g.drawString(Integer.toString(100 - i*10), graph_pos_x-22, graph_pos_y+(i*28));
			double dou = Constant.pollut_max[type] - i *(Constant.pollut_max[type]/10);
			String str = String.format("%.3f", dou);
			g.drawString(str, graph_pos_x-22, graph_pos_y+(i*28));
		}
		

		gap = (graph_w+plus) / (Constant.locations.length -1);
		// �� ������ date �� item �󵵸� �׸���.
		for (int i=1; i < Constant.locations.length - 1; i++) {
			// ������ x��ǥ�� gap�� �ٽ� �����մϴ�.
			s_x = graph_pos_x + gap/2;
			gap += ( (graph_w + plus)/(Constant.locations.length -1) ) * 2  ;
			// ���� i�� date���� item�� �󵵸� �����ɴϴ�.
			//System.out.println(Constant.locations[i] + " ������ " + date + " �� " + item + " ��...");
			// ������ ���̸� ����մϴ�. �����Ͱ� ���� ��쿡�� -1�� ��ȯ�˴ϴ�.
			bar_h = getBar(i, date, item);
			if (bar_h < 0) {
				bar_h = 0;
			}
			// ������ ���̸� �̿��Ͽ� ������ y��ǥ�� ����մϴ�.
			s_y = graph_pos_y + graph_h - bar_h;
			
			g.setColor(Color.red);
			g.fillRect(s_x, s_y, bar_w, bar_h);
		}
		g.setFont(new Font("���� ���", Font.BOLD, 24));
		g.setColor(Color.BLACK);
		g.drawString(date.toString(), 50, graph_pos_y + graph_h + 100);
		g.setFont(new Font("���� ���", Font.PLAIN, 18));
		g.drawString(item + "�� ��", 50, graph_pos_y + graph_h + 130);
		
		//printStn(item);
		
		switch (item) {
		case "�̻�ȭ����":
			g.drawString(Constant.nppmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "������":
			g.drawString(Constant.oppmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "�̻�ȭź��":
			g.drawString(Constant.cppmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "��Ȳ�갡��":
			g.drawString(Constant.appmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "�̼�����":
			g.drawString(Constant.dustS, 50, graph_pos_y + graph_h + 180);
			break;
		case "�ʹ̼�����":
			g.drawString(Constant.mdustS, 50, graph_pos_y + graph_h + 180);
			break;
		}
		
		

	}
	

	private void setType(String item) {
		switch (item) {
		case "�̻�ȭ����":
			this.type = 0;
			break;
		case "������":
			this.type = 1;
			break;
		case "�̻�ȭź��":
			this.type = 2;
			break;
		case "��Ȳ�갡��":
			this.type = 3;
			break;
		case "�̼�����":
			this.type = 4;
			break;
		case "�ʹ̼�����":
			this.type = 5;
			break;
		}
		
	}


	private int getBar(int i, LocalDate date, String item) {
		// i��° ���� (1���� ����) �� date���� item�� �ش��ϴ� ���������� �󵵸� �������� �Լ�

		// i �� date �������� Ž��
		Stat resStat = new Stat(0,0,0,0,0,0);
		// findLocation�Ŀ� null�̸� getStat�� �������� �ʵ��� �ٲ۴�.
		if(Frame.csvL.findLocation(Constant.locations[i], date) != null) {
			resStat = Frame.csvL.findLocation(Constant.locations[i], date).getStat();
		} else {
			// �ش� ��¥�� ������ �����Ͱ� ���� ��쿡�� return -1
			return -1;
		}

		// item�� �󵵸� Ž��. ������ -1 ��ȯ
		double ppm = -1;
		ppm = resStat.getPpm(item);
		if(ppm == -1) {
			return -1; // �� ����
		}
		
		// 1ĭ 28px, max/10
		// 10ĭ�� max
		
		
		// ã�Ƴ� ppm�� ��밪ȭ ��Ų��. �� ���������� �� ������ max�� �����Ѵ�.
		for (int k=0; k<Constant.pollut.length; k++) {
			if(item.equals(Constant.pollut[k])) {
				//System.out.println((ppm / Constant.pollut_max[k] * 100));
				//return (int)(ppm / Constant.pollut_max[k] * 100);
				double result = (ppm / Constant.pollut_max[k] * 100); // ex) 21  == 21%.. �� 2.1ĭ		
				return (int)(result / 10 * 28);
			}
		}
		
		return -1;
		
	}

	public void setDate(LocalDate selectedLocalDate) {
		this.date = selectedLocalDate;
	}

	public void setItem(String selectedItem) {
		this.item = selectedItem;
	}
	
	
}
