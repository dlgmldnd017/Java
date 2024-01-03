import java.awt.Graphics;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.JPanel;




// 선택한 지역의 선택한 기간동안의 통계정보 조회
public class PeriodPanel extends JPanel {
	private LocalDate dateS, dateE;
	private LocalDate bufD;
	
	private long dateLength; // +1 해야 dateE 까지 나옴
	
	private ArrayList<String> dateList = new ArrayList<String>();
	private ArrayList<String> maxList = new ArrayList<String>();
	private String location;
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		dateLength = ChronoUnit.DAYS.between(dateS, dateE);
		dateLength++; // +1 해야 dateE 까지 나옴
		bufD = dateS;

		g.drawString(dateS.toString() + " ~ " + dateE.toString(), 50, 50);
		// dateList 생성
		for (int i=0; i< dateLength; i++) {
			dateList.add(bufD.plusDays(i).toString());
		}

		g.drawString(this.location, 50, 20);
		for(int i=0; i<Constant.pollut.length; i++) {
			// index out of bound
			g.drawString(Constant.pollut[i] + "가 가장 높았던 날 : " + calculate(this.location, Constant.pollut[i]), 50, 80 + (i*20));
		}
		
	}

	private String calculate(String location, String item) {
		// dateS~dateE의 location의 item
		String maxdate = "";
		double max = 0;
		Stat resS = new Stat(0,0,0,0,0,0);
		for (int i=0; i<dateLength; i++) {
			LocalDate curD = LocalDate.parse(dateList.get(i));
			for (int j=1; j<Constant.locations.length; j++) {
				if(Frame.csvL.findLocation(this.location, curD) != null) {
					resS = Frame.csvL.findLocation(this.location, curD).getStat();
				} else {
					continue;
				}
				
				double ppm = 0;
				ppm = resS.getPpm(item);
				if(max < ppm) {
					maxdate = curD.toString();
					max = ppm;
				}
				
				
			}
			
		}
		
		return (maxdate + " " + max);
	}


	public void setDate(LocalDate start, LocalDate end) {
		this.dateS = start;
		this.dateE = end;
	}

	public void reset() {
		this.dateS = null;
		this.dateE = null;
		this.dateList.clear();
		this.maxList.clear();
		
	}

	public void setLocation(String location) {
		this.location = location;
		
	}
	
}
