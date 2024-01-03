import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// �׷��� ��ȭ����
@SuppressWarnings("serial")
public class DataDialog extends JDialog{

	private JButton addBtn = new JButton("�߰�");
	private JButton updateBtn = new JButton("����");
	private JButton closeBtn = new JButton("�ݱ�");
	
	
	private static JTextField fname;
	private static JTextField fdate;
	private static JTextField fnppm;
	private static JTextField foppm;
	private static JTextField fcppm;
	private static JTextField fappm;
	private static JTextField fdust;
	private static JTextField fmdust;
	private JPanel pna = new JPanel();
	private JPanel pda = new JPanel();
	private JPanel pnp = new JPanel();
	private JPanel pop = new JPanel();
	private JPanel pcp = new JPanel();
	private JPanel pap = new JPanel();
	private JPanel pdu = new JPanel();
	private JPanel pmd = new JPanel();
	
	private JPanel left = new JPanel(new GridLayout(8,1));
	private JPanel right = new JPanel();
	
	private JLabel label;
	
	private JScrollPane jsp;
	private JTable resTable;
	private static DefaultTableModel model;
	
	public DataDialog(JFrame jframe, String title) {
		super(jframe, title);
		setSize(Constant.dial_W + 400, Constant.dial_H + 200);
		setLayout(new BorderLayout());
		
		fname = new JTextField(10);
		label = new JLabel("��      �� : ");
		pna.add(label);
		pna.add(fname);
		fdate = new JTextField(10);
		label = new JLabel("��      ¥ : ");
		pda.add(label);
		pda.add(fdate);
		fnppm = new JTextField(10);
		label = new JLabel("�̻�ȭ���� : ");
		pnp.add(label);
		pnp.add(fnppm);
		foppm = new JTextField(10);
		label = new JLabel("����  �� : ");
		pop.add(label);
		pop.add(foppm);
		fcppm = new JTextField(10);
		label = new JLabel("�̻�ȭź�� : ");
		pcp.add(label);
		pcp.add(fcppm);
		fappm = new JTextField(10);
		label = new JLabel("��Ȳ�갡�� : ");
		pap.add(label);
		pap.add(fappm);
		fdust = new JTextField(10);
		label = new JLabel("�̼����� : ");
		pdu.add(label);
		pdu.add(fdust);
		fmdust = new JTextField(10);
		label = new JLabel("�ʹ̼����� : ");
		pmd.add(label);
		pmd.add(fmdust);
		
		left.add(pna); left.add(pda); left.add(pnp); left.add(pop); left.add(pcp);
		left.add(pap); left.add(pdu); left.add(pmd);
		
		add(left, BorderLayout.WEST);
		
		
		// ��ư �� �Է� �г�
		JPanel btnP = new JPanel();
		btnP.add(addBtn);
		btnP.add(updateBtn);
		btnP.add(closeBtn);
		add(btnP, BorderLayout.SOUTH);

		// �׼� ������ - ���ٽ�
		closeBtn.addActionListener(e -> setVisible(false));
		addBtn.addActionListener(e -> add());
		updateBtn.addActionListener(e -> update());
		
		
		// ���̺� �� ���� & ���̺� ����
		model = new DefaultTableModel(Constant.header, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		this.model = Frame.model; // ����ȭ���� ���̺��� �����ɴϴ�.
		resTable = new JTable(model);
		// ����, �̵�����. ������ ����
		resTable.getTableHeader().setReorderingAllowed(false);
		resTable.getTableHeader().setResizingAllowed(false);
		resTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		// ���̺� ��� Ŭ���� ����
		//resTable.setAutoCreateRowSorter(true);
		TableRowSorter trs = new TableRowSorter(model);
		class DoubleComparator implements Comparator{
			public int compare(Object o1, Object o2) {
				Double d1 = Double.parseDouble((String)o1);
				Double d2 = Double.parseDouble((String)o2);
				return d1.compareTo(d2);
			}
		}
		for (int i=2; i<8; i++) {
			trs.setComparator(i, new DoubleComparator());
		}
		resTable.setRowSorter(trs);
		
		
		
		// �гο� ���̺� �߰�
		jsp = new JScrollPane(resTable);
		jsp.setPreferredSize(new Dimension(600,400));
		
		right.add(jsp);
		
		add(right, BorderLayout.EAST);
		
		
		
		
	}
	
	public static void add() {
		String name = fname.getText();
		String date = fdate.getText();
		String nppm = fnppm.getText();
		String oppm = foppm.getText();
		String cppm = fcppm.getText();
		String appm = fappm.getText();
		String dust = fdust.getText();
		String mdust = fmdust.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(null, "�������� �Է��ؾ� �մϴ�!");
			return;
		}
		if(date.equals("")) {
			JOptionPane.showMessageDialog(null, "��¥�� �Է��ؾ� �մϴ�!");
			return;
		}
		try {
			LocalDate localDate = LocalDate.parse(date);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�߸��� ��¥�� �Է��߽��ϴ�!");
			return;
		}
		if(nppm.equals("") || oppm.equals("") || cppm.equals("") ||
				appm.equals("") || dust.equals("") || mdust.equals("")) {
			JOptionPane.showMessageDialog(null, "��ġ�� ��� �Է��ؾ� �մϴ�!");
			return;
		}

		String[] one = {name, date, nppm, oppm, cppm, appm, dust, mdust};
		// ���̺� ������ �߰�
		model.addRow(one);
		// locations���� ������ �߰�
		LocalDate localDate = LocalDate.parse(date);
		Stat c = new Stat(nppm, oppm, cppm, appm, dust, mdust);
		Location location = new Location(localDate, name, c);
		Frame.csvL.getlocations().add(location);
		
	}

	public static void update() {
		String name = fname.getText();
		String date = fdate.getText();
		String nppm = fnppm.getText();
		String oppm = foppm.getText();
		String cppm = fcppm.getText();
		String appm = fappm.getText();
		String dust = fdust.getText();
		String mdust = fmdust.getText();
		
		// name�� date�� ��ġ�ϴ� ���� ã��.
		int target;
		for (int i=0; i<model.getRowCount(); i++) {
			if(name.equals(model.getValueAt(i, 0)) && 
					date.equals(model.getValueAt(i, 1))) {
				target = i;
				
				// �Է°� ��ġ�ϴ� locations�� ã�´� 
				LocalDate localDate = LocalDate.parse(date);
				// target ��ġ�� Stat�� ����
				
				// name �� localdate�� location�� ã�Ƴ���,
				Location location = Frame.csvL.findLocation(name, localDate);
				
				// ������ ���� ����
				model.setValueAt(name, target, 0);
				model.setValueAt(date, target, 1);
				// �ʵ忡 ���� �� ���� ��쿡�� �������� �ʴ´�.
				if(!nppm.equals("")) {
					model.setValueAt(nppm, target, 2);
				}
				if(!oppm.equals("")) {
					model.setValueAt(oppm, target, 3);
				}
				if(!cppm.equals("")) {
					model.setValueAt(cppm, target, 4);
				}
				if(!appm.equals("")) {
					model.setValueAt(appm, target, 5);
				}
				if(!dust.equals("")) {
					model.setValueAt(dust, target, 6);
				}
				if(!mdust.equals("")) {
					model.setValueAt(mdust, target, 7);
				}
				
				// Stat �����Ѵ�.
				Stat c = buildStat(i, model.getValueAt(i, 2), model.getValueAt(i, 3),model.getValueAt(i, 4),
						model.getValueAt(i, 5),model.getValueAt(i, 6), model.getValueAt(i, 7));
				// location���� ���������� �ݿ�
				location.setStat(c);
				
				break;
			}
		}	
	}

	private static Stat buildStat(int i, Object valueAt, Object valueAt2, Object valueAt3, Object valueAt4, Object valueAt5,
			Object valueAt6) {
		
		double nppm = Double.parseDouble((String)valueAt);
		double oppm = Double.parseDouble((String)valueAt2);
		double cppm = Double.parseDouble((String)valueAt3);
		double appm = Double.parseDouble((String)valueAt4);
		double dust = Double.parseDouble((String)valueAt5);
		double mdust = Double.parseDouble((String)valueAt6);
		
		Stat ret = new Stat(nppm, oppm, cppm, appm, dust, mdust);
				
		return ret;
	}

	
}
