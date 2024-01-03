import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.time.LocalDate;

import javax.swing.JPanel;

public class BarGraph extends JPanel{
	
	private static final int bar_w = 10; // 막대그래프 폭
	private int bar_h; // 막대그래프 높이
	
	private static final int graph_pos_x = 40; // 그래프 틀 시작좌표
	private static final int graph_pos_y = 40;
	private static final int graph_w = 420;	// 그래프 폭
	private static final int graph_h = 280;	// 그래프 높이
	
	private int s_x, s_y; // 막대 시작점 좌표
	private int gap;
	
	private LocalDate date; // 그릴 날짜
	private String item; // 그릴 오염물질
	private int type; // 타입
	
	private int plus = 1600;  // 막대그래프에서 추가로 늘어나는 폭
	private int plus2 = 400;

	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, Constant.dial_W + plus, Constant.dial_H + plus2);
		
		setType(item);
		
		gap = (graph_w+plus) / (Constant.locations.length -1); // 간격. 46개지역
		// 그래프 틀
		g.setColor(Color.black);
		// x축
		g.drawLine(graph_pos_x, graph_pos_y+graph_h, graph_pos_x, graph_pos_y);
		for(int i=1; i < Constant.locations.length; i++) {
			g.drawLine(graph_pos_x+gap, graph_pos_y+graph_h, graph_pos_x+gap, graph_pos_y);
			g.drawString(Constant.locations[i], (gap), graph_pos_y + graph_h + 15);
			//gap += 420/6;
			gap += (graph_w + plus) / (Constant.locations.length -1);
		}
		
		
		// y축
		for(int i=0; i<10; i++) {
			//g.drawLine(graph_pos_x, graph_pos_y+(i*28), graph_pos_x+graph_w+plus, graph_pos_y+(i*28));
			g.drawLine(graph_pos_x, graph_pos_y+(i*28), graph_pos_x+graph_w+plus, graph_pos_y+(i*28));
			//g.drawString(Integer.toString(100 - i*10), graph_pos_x-22, graph_pos_y+(i*28));
			double dou = Constant.pollut_max[type] - i *(Constant.pollut_max[type]/10);
			String str = String.format("%.3f", dou);
			g.drawString(str, graph_pos_x-22, graph_pos_y+(i*28));
		}
		

		gap = (graph_w+plus) / (Constant.locations.length -1);
		// 각 도시의 date 의 item 농도를 그린다.
		for (int i=1; i < Constant.locations.length - 1; i++) {
			// 막대의 x좌표와 gap을 다시 설정합니다.
			s_x = graph_pos_x + gap/2;
			gap += ( (graph_w + plus)/(Constant.locations.length -1) ) * 2  ;
			// 지역 i의 date날의 item의 농도를 가져옵니다.
			//System.out.println(Constant.locations[i] + " 도시의 " + date + " 의 " + item + " 은...");
			// 막대의 높이를 계산합니다. 데이터가 없는 경우에는 -1이 반환됩니다.
			bar_h = getBar(i, date, item);
			if (bar_h < 0) {
				bar_h = 0;
			}
			// 막대의 높이를 이용하여 막대의 y좌표를 계산합니다.
			s_y = graph_pos_y + graph_h - bar_h;
			
			g.setColor(Color.red);
			g.fillRect(s_x, s_y, bar_w, bar_h);
		}
		g.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		g.setColor(Color.BLACK);
		g.drawString(date.toString(), 50, graph_pos_y + graph_h + 100);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		g.drawString(item + "의 농도", 50, graph_pos_y + graph_h + 130);
		
		//printStn(item);
		
		switch (item) {
		case "이산화질소":
			g.drawString(Constant.nppmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "오존농도":
			g.drawString(Constant.oppmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "이산화탄소":
			g.drawString(Constant.cppmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "아황산가스":
			g.drawString(Constant.appmS, 50, graph_pos_y + graph_h + 180);
			break;
		case "미세먼지":
			g.drawString(Constant.dustS, 50, graph_pos_y + graph_h + 180);
			break;
		case "초미세먼지":
			g.drawString(Constant.mdustS, 50, graph_pos_y + graph_h + 180);
			break;
		}
		
		

	}
	

	private void setType(String item) {
		switch (item) {
		case "이산화질소":
			this.type = 0;
			break;
		case "오존농도":
			this.type = 1;
			break;
		case "이산화탄소":
			this.type = 2;
			break;
		case "아황산가스":
			this.type = 3;
			break;
		case "미세먼지":
			this.type = 4;
			break;
		case "초미세먼지":
			this.type = 5;
			break;
		}
		
	}


	private int getBar(int i, LocalDate date, String item) {
		// i번째 도시 (1부터 시작) 의 date날의 item에 해당하는 오염물질의 농도를 가져오는 함수

		// i 와 date 기준으로 탐색
		Stat resStat = new Stat(0,0,0,0,0,0);
		// findLocation후에 null이면 getStat을 진행하지 않도록 바꾼다.
		if(Frame.csvL.findLocation(Constant.locations[i], date) != null) {
			resStat = Frame.csvL.findLocation(Constant.locations[i], date).getStat();
		} else {
			// 해당 날짜와 지역의 데이터가 없는 경우에는 return -1
			return -1;
		}

		// item의 농도를 탐색. 없으면 -1 반환
		double ppm = -1;
		ppm = resStat.getPpm(item);
		if(ppm == -1) {
			return -1; // 값 없음
		}
		
		// 1칸 28px, max/10
		// 10칸이 max
		
		
		// 찾아낸 ppm을 상대값화 시킨다. 각 오염물질의 값 범위는 max를 참조한다.
		for (int k=0; k<Constant.pollut.length; k++) {
			if(item.equals(Constant.pollut[k])) {
				//System.out.println((ppm / Constant.pollut_max[k] * 100));
				//return (int)(ppm / Constant.pollut_max[k] * 100);
				double result = (ppm / Constant.pollut_max[k] * 100); // ex) 21  == 21%.. 즉 2.1칸		
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
