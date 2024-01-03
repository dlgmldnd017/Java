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

	private JButton closeBtn = new JButton("�ݱ�");
	private JButton paintBtn = new JButton("�ҷ�����");
	
	private PeriodPanel period_panel = new PeriodPanel();
	private LocationPanel location_panel = new LocationPanel();
	
	private LocalDate dateS, dateE;
	private int dateLength;
	private String location;
	

	public StaticsDialog(JFrame jframe, String title, int type) {
		super(jframe, title);
		setLayout(new BorderLayout());
		setSize(Constant.dial_W, Constant.dial_H);
		
		// Ÿ��Ʋ
		JPanel textP = new JPanel();
		
		JLabel titleLabel = new JLabel("�� �� �� ��");
		titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
		titleLabel.setForeground(new Color(102, 153, 153));
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		textP.add(titleLabel);
		// ��ư �г�
		JPanel btnP = new JPanel();
		
		btnP.add(paintBtn);
		btnP.add(closeBtn);
		
		
		switch (type) {
		case 0:	// Ư�� �Ⱓ ��跮 ��ȸ
			add(period_panel, BorderLayout.CENTER);
			break;
		case 1:	// Ư�� ���� ��跮 ��ȸ
			setSize(Constant.dial_W, Constant.dial_H + 150);
			add(location_panel, BorderLayout.CENTER);
			break;
			
			
		}
		
		paintBtn.addActionListener(e -> {
			if(type == 0) {	// ������ �������� ������ �Ⱓ������ ��跮�� �����Ѵ�.
				period_panel.reset();
				
				LocalDate start = LocalDate.of(2018, 1, 1);
				LocalDate end = LocalDate.of(2018, 12, 31);
				if(Frame.getOpen()) {
					try{
						start = LocalDate.parse(JOptionPane.showInputDialog("�����ϴ� ��¥�� �Է��ϼ���. ex) 2018-01-06"));
						end = LocalDate.parse(JOptionPane.showInputDialog("������ ��¥�� �Է��ϼ���. ex) 2018-01-16"));
						setDate(start, end);
					} catch(Exception err) {
						JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String[] ls = Constant.locations;
					Object selectedL = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ������ �ϳ� �����ϼ���.\n��ü�� �������Դϴ�.", "Selection", JOptionPane.DEFAULT_OPTION, null, ls, "1");
					if ( selectedL != null ){ 
					    setLocation(selectedL.toString());
					}else{
					    //System.out.println("User cancelled");
					}
					
					
				} else {
					JOptionPane.showMessageDialog(null, "���� �����͸� �ҷ��;��մϴ�!");
					//setDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
				}
				
				period_panel.setDate(start, end);
				period_panel.repaint();
				
				
			} else {	// ���õ� ������ ��跮�� �����Ѵ�.
				//location_panel.reset();
				if(Frame.getOpen()) {
					LocalDate targetDate;
					try {
						targetDate = LocalDate.parse(JOptionPane.showInputDialog("��¥�� �Է����ּ���. ex) 2018-01-06"));
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
						return;
					}
					setDate(targetDate);
					location_panel.setDate(targetDate);	
				} else {
					JOptionPane.showMessageDialog(null, "���� �����͸� �ҷ��;��մϴ�!");
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
