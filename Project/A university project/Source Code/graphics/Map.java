import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Map extends JPanel {

	private String item = "�̼�����";			// ��������
	private LocalDate date = LocalDate.parse("2019-01-01");	// ��¥
					

	public void paint(Graphics g) {
		//setSize(250, 250);
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		//g.drawRect(20, 10, 100, 200);
		makeFill(g);
		
		g.setColor(Color.black);
		g.setFont(new Font("���� ���", Font.PLAIN, 20));
		g.drawString(date.toString(), 450, 50);
		g.setFont(new Font("���� ���", Font.PLAIN, 18));
		g.drawString(item, 450, 70);
		
		for(int i=0; i<50; i++) {
			g.setColor(new Color(200, i*5, 10));
			g.fillRect(450 + (i*2), 80, 2, 10);
		}
		
		g.setColor(Color.black);
		g.drawPolygon(Constant.s1_xp, Constant.s1_yp, Constant.s1_num);
		g.drawPolygon(Constant.s2_xp, Constant.s2_yp, Constant.s2_num);
		g.drawPolygon(Constant.s3_xp, Constant.s3_yp, Constant.s3_num);
		g.drawPolygon(Constant.s4_xp, Constant.s4_yp, Constant.s4_num);
		g.drawPolygon(Constant.s5_xp, Constant.s5_yp, Constant.s5_num);
		g.drawPolygon(Constant.s6_xp, Constant.s6_yp, Constant.s6_num);
		
		//System.out.println(date.toString() + " ��¥, ������ " + item);
		//makeFill(g);
		
		
		
	
		
		
		
	}

	private void makeFill(Graphics g) {
		// ������ ������� �����￡ ���� ĥ�Ѵ�.
		
		// s1 �� ����� ���Ѵ�.
		int[] power = {0, 0, 0, 0, 0, 0}; // �׶��̼��� ���� (0~255)
		//power = getColor(1, item);
		
		for (int i=0; i<6; i++) {
			// i sector�� �׶��̼� ���⸦ ���Ѵ�.
			power[i] = getColor(i, item);
			//System.out.println(power[i]);
		}
		
		
		
		
		g.setColor(new Color(200, power[0], 10));
		g.fillPolygon(Constant.s1_xp, Constant.s1_yp, Constant.s1_num);
		
		g.setColor(new Color(200, power[1], 10));
		g.fillPolygon(Constant.s2_xp, Constant.s2_yp, Constant.s2_num);
		
		g.setColor(new Color(200, power[2], 10));
		g.fillPolygon(Constant.s3_xp, Constant.s3_yp, Constant.s3_num);
		
		g.setColor(new Color(200, power[3], 10));
		g.fillPolygon(Constant.s4_xp, Constant.s4_yp, Constant.s4_num);
		
		g.setColor(new Color(200, power[4], 10));
		g.fillPolygon(Constant.s5_xp, Constant.s5_yp, Constant.s5_num);
		
		g.setColor(new Color(200, power[5], 10));
		g.fillPolygon(Constant.s6_xp, Constant.s6_yp, Constant.s6_num);
		
		
		
		
		
	}

	private int getColor(int s, String item) {
		double sum = 0;
		double count = 0;
		// ���� ���ʹ� s �� ����.
		// ���� ��¥�� date, ã������ ������ item
		// date�� item �󵵸� �����´�.		
		for(int i=0; i < Constant.sectorList.get(s).length; i++) {
			Stat resStat = new Stat(0,0,0,0,0,0);
			if(Frame.csvL.findLocation(Constant.sectorList.get(s)[i], date) != null) {
				resStat = Frame.csvL.findLocation(Constant.sectorList.get(s)[i], date).getStat();
				
				double ppm = 0;
				ppm = resStat.getPpm(item);	// item�� �󵵸� ������
				if(ppm == -1) {
					// ���� ���� �����.
					ppm = 0;
				} else {
					count++;
				}
				sum += ppm;
			}
		}
		// ��ü �������� item�� ����� ����.
		double avg = 0;
		avg = sum/count;
		
		
		//System.out.println(sum + " = sum, " + avg);
		int result = 0; // 100 ~ 255
		// �� ���������� �ǰ������ ã�Ƴ���.
		switch (item) {
		case "�̻�ȭ����":
			result = (int) (255 / 0.2 * avg);
			break;
		case "������":
			result = (int)(255 / 0.15 * avg);
			break;
		case "�̻�ȭź��":
			result = (int)(255 / 15 * avg);
			break;
		case "��Ȳ�갡��":		
			result = (int)(255 / 0.15 * avg);
			break;
		case "�̼�����":	
			result = (int)(255 / 150 * avg);
			break;			
		case "�ʹ̼�����":
			result = (int)(255 / 75 * avg);
			break;
		}
		
		if(result > 255) {
			result = 255;
		}
		
		
		return 255-result;
	}

	public void setDate(String searchDate) {
		try {
			this.date = LocalDate.parse(searchDate);	
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�߸��� ��¥�� �Է��߽��ϴ�.");
		}
	}

	public void setItem(String string) {
		this.item = string;
		
	}
	
	
	

}
