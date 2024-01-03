import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDate;

import javax.swing.JPanel;




// 선택한 날의 통계정보 조회.
public class LocationPanel extends JPanel {

	private LocalDate date;


	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.black);
		g.drawString(date.toString(), 50, 50);
		// 그 날 p0이 제일 높은 동네 & 낮은 동네
		// 그 날 p1이 제일 높은 동네 & 낮은 동네
		// ...
		
		for(int i=0; i<Constant.pollut.length; i++) {
			g.drawString(Constant.pollut[i] + "가 가장 높은 동네 : " + getMaxLocation(this.date, Constant.pollut[i]), 50, 80 + (i*40));
			g.drawString(Constant.pollut[i] + "가 가장 낮은 동네 : " + getMinLocation(this.date, Constant.pollut[i]), 50, 100 + (i*40));
			
		}
		
	}


	// date에 item이 가장 낮은 지역 찾는 메소드
	private String getMinLocation(LocalDate date, String item) {
		String result = "";
		double min = 3000;
		double ppm = 3000;
		Stat resS = new Stat(0,0,0,0,0,0);
		for (int i=1; i<Constant.locations.length; i++) {
			if(Frame.csvL.findLocation(Constant.locations[i], date) != null) {
				resS = Frame.csvL.findLocation(Constant.locations[i], date).getStat();
				ppm = resS.getPpm(item);
				if(ppm == -1) {
					// null 값인 경우이므로 스킵.
					continue;
				} else if(min > ppm) {
					min = ppm;
					result = Constant.locations[i] + "에서 " + min;
				}
			} else {
				continue;
			}
		}
		
		
		return result;
	}


	// date에 item이 가장 높은 지역을 찾는 메소드
	private String getMaxLocation(LocalDate date, String item) {
		String result = "";
		double max = 0;
		double ppm = -1;
		Stat resS = new Stat(0,0,0,0,0,0);
		for (int i=1; i<Constant.locations.length; i++) {
			if(Frame.csvL.findLocation(Constant.locations[i], date) != null) {
				resS = Frame.csvL.findLocation(Constant.locations[i], date).getStat();
				ppm = resS.getPpm(item);
				if(ppm == -1) {
					continue;
				} else if(max < ppm) {
					max = ppm;
					result = Constant.locations[i] + "에서 " + max;
				}
				
			} else {
				continue;
			}
		}
		
		
		
		return result;
	}

	public void setDate(LocalDate targetDate) {
		this.date = targetDate;
	}

	
	
}
