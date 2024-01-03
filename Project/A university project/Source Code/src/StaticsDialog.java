import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StaticsDialog extends JDialog {

	private JButton closeBtn = new JButton("닫기");
	private JButton paintBtn = new JButton("불러오기");
	
	private PeriodPanel period_panel = new PeriodPanel();
	private LocationPanel location_panel = new LocationPanel();
	
	private LocalDate dateS, dateE;
	private int dateLength;
	private String location;
	

	public StaticsDialog(JFrame jframe, String title, int type) {
		super(jframe, title);
		setLayout(new BorderLayout());
		setSize(Constant.dial_W, Constant.dial_H);
		
		// 타이틀
		JPanel textP = new JPanel();
		
		JLabel titleLabel = new JLabel("통 계 정 보");
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		titleLabel.setForeground(new Color(102, 153, 153));
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		textP.add(titleLabel);
		// 버튼 패널
		JPanel btnP = new JPanel();
		
		btnP.add(paintBtn);
		btnP.add(closeBtn);
		
		
		switch (type) {
		case 0:	// 특정 기간 통계량 조회
			add(period_panel, BorderLayout.CENTER);
			break;
		case 1:	// 특정 지역 통계량 조회
			setSize(Constant.dial_W, Constant.dial_H + 150);
			add(location_panel, BorderLayout.CENTER);
			break;
			
			
		}
		
		paintBtn.addActionListener(e -> {
			if(type == 0) {	// 선택한 지역에서 선택한 기간동안의 통계량을 제공한다.
				period_panel.reset();
				
				LocalDate start = LocalDate.of(2018, 1, 1);
				LocalDate end = LocalDate.of(2018, 12, 31);
				if(Frame.getOpen()) {
					try{
						start = LocalDate.parse(JOptionPane.showInputDialog("시작하는 날짜를 입력하세요. ex) 2018-01-06"));
						end = LocalDate.parse(JOptionPane.showInputDialog("끝나는 날짜를 입력하세요. ex) 2018-01-16"));
						setDate(start, end);
					} catch(Exception err) {
						JOptionPane.showMessageDialog(null, "잘못된 날짜입니다!", "입력오류", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String[] ls = Constant.locations;
					Object selectedL = JOptionPane.showInputDialog(null, "조회하고 싶은 지역을 하나 선택하세요.\n전체는 미지원입니다.", "Selection", JOptionPane.DEFAULT_OPTION, null, ls, "1");
					if ( selectedL != null ){ 
					    setLocation(selectedL.toString());
					}else{
					    //System.out.println("User cancelled");
					}
					
					
				} else {
					JOptionPane.showMessageDialog(null, "먼저 데이터를 불러와야합니다!");
					//setDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
				}
				
				period_panel.setDate(start, end);
				period_panel.repaint();
				
				
			} else {	// 선택된 지역의 통계량을 제공한다.
				//location_panel.reset();
				if(Frame.getOpen()) {
					LocalDate targetDate;
					try {
						targetDate = LocalDate.parse(JOptionPane.showInputDialog("날짜를 입력해주세요. ex) 2018-01-06"));
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "잘못된 날짜입니다!", "입력오류", JOptionPane.ERROR_MESSAGE);
						return;
					}
					setDate(targetDate);
					location_panel.setDate(targetDate);	
				} else {
					JOptionPane.showMessageDialog(null, "먼저 데이터를 불러와야합니다!");
				}
				
				location_panel.repaint();
			}
					
		});
		
		closeBtn.addActionListener(e -> setVisible(false));
			
		add(textP, BorderLayout.NORTH);
		add(btnP, BorderLayout.SOUTH);	
	}

	public void setDate(LocalDate start, LocalDate end) {
		this.dateS = start;
		this.dateE = end;
		this.period_panel.setDate(start, end);
	}

	public void init() {
		this.dateS = null;
		this.dateE = null;
		this.period_panel.reset();
	}
	
	public void setDate(LocalDate targetDate) {
		this.dateS = targetDate;
		this.dateE = targetDate;
		this.location_panel.setDate(targetDate);
		this.period_panel.setDate(targetDate, targetDate);
	}

	public void setLocation(String string) {
		this.location = string;
		this.period_panel.setLocation(this.location);
		
	}


	
}
