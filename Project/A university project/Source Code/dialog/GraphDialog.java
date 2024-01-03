import java.awt.BorderLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// �׷��� ��ȭ����
@SuppressWarnings("serial")
public class GraphDialog extends JDialog{

	private JButton closeBtn = new JButton("�ݱ�");
	private JButton paintBtn = new JButton("�׸���");
	
	private CircleGraph circle_graph = new CircleGraph();
	private LineGraph line_graph = new LineGraph();
	private BarGraph bar_graph = new BarGraph();
	
	private double num1, num2, num3, num4, num5, num6; // ������������ ��
	private int type; // �׷����� ������ ����. 0 : ����, 1 : ������, 2 : ����
	
	private LocalDate selectedLocalDate; // ���õ� Ư���� �� ��¥
	private String selectedItem; // ���õ� Ư�� ��������
	private LocalDate selectedStartDate;	// �Ⱓ�� ���۳�¥
	private LocalDate selectedEndDate;		// �Ⱓ�� ����¥
	private String selectedLocation;
	
	public GraphDialog(JFrame jframe, String title, int type) {
		super(jframe, title);
		this.type = type;
		setLayout(new BorderLayout());
		
		// ��ư �г�
		JPanel btnP = new JPanel();
		btnP.add(paintBtn);
		btnP.add(closeBtn);
		add(btnP, BorderLayout.SOUTH);
		
		switch (type) {	// �׷��� ���� ����
		case 0:
			add(circle_graph, BorderLayout.CENTER);
			break;
		case 1:
			add(line_graph, BorderLayout.CENTER);
			break;
		case 2:
			add(bar_graph, BorderLayout.CENTER);
			break;
		}
		
		// ��ȭâ ������ ����
		setSize(Constant.dial_W, Constant.dial_H);
		if (type == 2) {	// ����׷�����
			setSize(Constant.dial_W + 1427, Constant.dial_H + 300);
		} else if (type == 1) {
			setSize(Constant.dial_W + 600, Constant.dial_H + 300);
		}
		// �׸��� ��ư ������
		paintBtn.addActionListener(e -> {
			if(type == 0) {	// �����׷����̸�,
				if(Frame.resTable.getSelectedRowCount() != 1) {
					JOptionPane.showMessageDialog(null, "���� �׷����� �ϳ��� Į���� �׸� �� �ֽ��ϴ�.");
					return;
				}
			
				
				int row = Frame.resTable.getSelectedRow();
				//int col = Frame.resTable.getSelectedColumn();
				
				String area = (String) Frame.resTable.getValueAt(row, 0);
				double pol1 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 2)));
				double pol2 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 3)));
				double pol3 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 4)));
				double pol4 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 5)));
				double pol5 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 6)));
				double pol6 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 7)));
				Frame.cgDialog.setNumbers(pol1, pol2, pol3, pol4, pol5, pol6);
				
				circle_graph.setName(area);
				circle_graph.setNumbers(num1, num2, num3, num4, num5, num6);
				circle_graph.repaint();
			} else if(type == 1) {	// ������ �׷����̸�
				// �Է°� �ʱ�ȭ
				init();
				
				// �Ⱓ �Է� & ���� ����
				try{
					LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("�����ϴ� ��¥�� �Է��ϼ���. ex) 2018-01-06"));
					LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("������ ��¥�� �Է��ϼ���. ex) 2018-01-16"));
					setDate(start, end);
				} catch(Exception err) {
					JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// ���� ����
				String[] locations = Constant.locations;
				Object selectedLocation = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ������ �ϳ� �����ϼ���.\n��ü�� �������Դϴ�.", "Selection", JOptionPane.DEFAULT_OPTION, null, locations, "1");
				if ( selectedLocation != null ){ 
				    setLocation(selectedLocation.toString());
				}else{
				    //System.out.println("User cancelled");
				}
				// �������� ����(1��)
				String[] values = Constant.pollut;
				Object selected = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ���������� �ϳ� �����ϼ���.", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
				if ( selected != null ){ 
					setItem(selected.toString());			    
				}else{
				    JOptionPane.showMessageDialog(null, "item selection Error !!!", "Unknowun Error", JOptionPane.ERROR_MESSAGE);
				}
				
				//line_graph.Period.between(start, end);
				// �׸��� - ��� ��������
				
				line_graph.init();
				line_graph.setDate(this.selectedStartDate, this.selectedEndDate);
				line_graph.setLocation(this.selectedLocation.toString());
				line_graph.setItem(this.selectedItem);
				line_graph.repaint();

			} else {	// ���� �׷����̸�
				// ��¥ ����
				String inputDate = JOptionPane.showInputDialog("��¥�� �Է��ϼ���. ex) 2018-01-06");
				// inputDate ��ȿ�� ����
				try{
					LocalDate localDate = LocalDate.parse(inputDate);
					setDate(localDate);
				} catch(Exception err) {
					JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// �������� ����(1��)
				String[] values = Constant.pollut;
				Object selected = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ���������� �ϳ� �����ϼ���.", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
				if ( selected != null ){ 
					setItem(selected.toString());			    
				}else{
				    JOptionPane.showMessageDialog(null, "item selection Error !!!", "Unknowun Error", JOptionPane.ERROR_MESSAGE);
				}
				
				// �� ���� ��� ������ ���������� ����׷����� �׸���.
				bar_graph.setDate(this.selectedLocalDate);
				bar_graph.setItem(this.selectedItem);
				bar_graph.repaint();
				
			}
		});
		
		// �ݱ� ��ư ������
		closeBtn.addActionListener(e -> setVisible(false));
		
	}
	
	public void setNumbers(double pol1, double pol2, double pol3, double pol4, double pol5, double pol6) {
		this.num1 = pol1;
		this.num2 = pol2;
		this.num3 = pol3;
		this.num4 = pol4;
		this.num5 = pol5;
		this.num6 = pol6;
	}
	// ����׷�����
	public void setDate(LocalDate lo) {
		this.selectedLocalDate = lo;
		bar_graph.setDate(this.selectedLocalDate);
	}
	public void setItem(String item) {
		this.selectedItem = item;
		bar_graph.setItem(this.selectedItem);
		line_graph.setItem(item);
	}
	
	// line graph ��
	public void setDate(LocalDate start, LocalDate end) {
		this.selectedStartDate = start;
		this.selectedEndDate = end;
		line_graph.setDate(start, end);
		
	}
	public void setLocation(String string) {
		this.selectedLocation = string;
		line_graph.setLocation(string);
		
	}
	public void init() {
		selectedStartDate = null;	// �Ⱓ�� ���۳�¥
		selectedEndDate = null;		// �Ⱓ�� ����¥
		selectedLocation = "";
		
	}

	
}
