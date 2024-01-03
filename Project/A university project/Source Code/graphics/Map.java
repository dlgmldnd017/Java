import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Map extends JPanel {

	private String item = "미세먼지";			// 오염물질
	private LocalDate date = LocalDate.parse("2019-01-01");	// 날짜
					

	public void paint(Graphics g) {
		//setSize(250, 250);
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		//g.drawRect(20, 10, 100, 200);
		makeFill(g);
		
		g.setColor(Color.black);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		g.drawString(date.toString(), 450, 50);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
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
		
		//System.out.println(date.toString() + " 날짜, 아이템 " + item);
		//makeFill(g);
		
		
		
	
		
		
		
	}

	private void makeFill(Graphics g) {
		// 구역의 평균으로 폴리곤에 색을 칠한다.
		
		// s1 의 평균을 구한다.
		int[] power = {0, 0, 0, 0, 0, 0}; // 그라데이션의 세기 (0~255)
		//power = getColor(1, item);
		
		for (int i=0; i<6; i++) {
			// i sector의 그라데이션 세기를 구한다.
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
		// 현재 섹터는 s 번 섹터.
		// 현재 날짜는 date, 찾으려는 물질은 item
		// date의 item 농도를 가져온다.		
		for(int i=0; i < Constant.sectorList.get(s).length; i++) {
			Stat resStat = new Stat(0,0,0,0,0,0);
			if(Frame.csvL.findLocation(Constant.sectorList.get(s)[i], date) != null) {
				resStat = Frame.csvL.findLocation(Constant.sectorList.get(s)[i], date).getStat();
				
				double ppm = 0;
				ppm = resStat.getPpm(item);	// item의 농도를 가져옴
				if(ppm == -1) {
					// 값이 없는 경우임.
					ppm = 0;
				} else {
					count++;
				}
				sum += ppm;
			}
		}
		// 전체 지역에서 item의 평균을 구함.
		double avg = 0;
		avg = sum/count;
		
		
		//System.out.println(sum + " = sum, " + avg);
		int result = 0; // 100 ~ 255
		// 각 오염물질의 권고기준을 찾아낸다.
		switch (item) {
		case "이산화질소":
			result = (int) (255 / 0.2 * avg);
			break;
		case "오존농도":
			result = (int)(255 / 0.15 * avg);
			break;
		case "이산화탄소":
			result = (int)(255 / 15 * avg);
			break;
		case "아황산가스":		
			result = (int)(255 / 0.15 * avg);
			break;
		case "미세먼지":	
			result = (int)(255 / 150 * avg);
			break;			
		case "초미세먼지":
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
			JOptionPane.showMessageDialog(null, "잘못된 날짜를 입력했습니다.");
		}
	}

	public void setItem(String string) {
		this.item = string;
		
	}
	
	
	

}
