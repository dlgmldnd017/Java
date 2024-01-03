import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Frame extends JFrame {
	
	ImageIcon open = new ImageIcon(".//Icon//open.png");
	ImageIcon save = new ImageIcon(".//Icon//save.png");	
	ImageIcon DBLoad = new ImageIcon(".//Icon//dbload.png");
	ImageIcon DBSave = new ImageIcon(".//Icon//dbsave.png");
	ImageIcon Graph1 = new ImageIcon(".//Icon//graph1.png");
	ImageIcon Graph2 = new ImageIcon(".//Icon//graph2.png");
	ImageIcon Graph3 = new ImageIcon(".//Icon//graph3.png");
	ImageIcon Stat1 = new ImageIcon(".//Icon//stat1.png");
	ImageIcon Stat2 = new ImageIcon(".//Icon//stat2.png");
	ImageIcon Data = new ImageIcon(".//Icon//data.png");
	ImageIcon Exit = new ImageIcon(".//Icon//exit.png");

	
	static CSVLoad csvL = new CSVLoad();
	static CSVWrite csvW = new CSVWrite();
	static InputDatabase ipdb = new InputDatabase();
	static OuputDatabase opdb = new OuputDatabase();
	
	private static boolean isOpen; // �����Ͱ� ���Դ��� Ȯ���ϴ� ����
	
	static ArrayList<String[]> data = new ArrayList<String[]>();
	static String[][] arr = new String[13000][8];

	JPanel top_area = new JPanel(new BorderLayout());	// ��ܿ� ��ġ�ϴ� ����&������Ʈ �ڸ�
	JPanel main_area = new JPanel(new BorderLayout());	// ����
	
	MenuActionListener mal = new MenuActionListener();
	ButtonActionListener bal = new ButtonActionListener();
	
	
	static JScrollPane jsp;
	static JTable resTable;
	static DefaultTableModel model;
	Vector<String> dataRow;

	public static Map map;
	
	static GraphDialog cgDialog;	// �����׷��� ��ȭâ
	static GraphDialog lgDialog;	// �����׷��� ��ȭâ
	static GraphDialog bgDialog;	// ����׷���
	static DataDialog tbDialog;		// ������ ����
	static TextDialog txDialog;		// ��Ÿ ���� ��ȭâ
	static StaticsDialog stDialog;		// ��� ���� ��ȭâ 1
	static StaticsDialog stDialog2;		// ��� ���� ��ȭâ 2
	
	//static JComboBox locations = new JComboBox(Constant.locations);
	static JComboBox locations = new JComboBox(Constant.locations);
	static JTextField inputDate = new JTextField("ex) 2018-01-01", 8);

	
	public Frame() {
		setTitle("POL Project");
		setSize(1000, 1000);
		setLayout(new BorderLayout());
		menu();
		toolbar();
		mainLayout();
		add(top_area, BorderLayout.NORTH);
		add(main_area, BorderLayout.CENTER);
	
		cgDialog = new GraphDialog(this, "�����׷���", 0);
		lgDialog = new GraphDialog(this, "�������׷���", 1);
		bgDialog = new GraphDialog(this, "����׷���", 2);
		tbDialog = new DataDialog(this, "������");
		txDialog = new TextDialog(this, "�������� �ǰ����");
		stDialog = new StaticsDialog(this, "������ ��� ����", 0);
		stDialog2 = new StaticsDialog(this, "��¥�� ��� ����", 1);
		
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
	}
	
	// �޴��� ����
	void menu() {
		JMenuItem item;
		JMenuBar mb = new JMenuBar();
		// �޴� ����
		
		// 1. ����
		JMenu file = new JMenu("����");
		// 1. Items
		item = new JMenuItem("DB ����");
		item.addActionListener(mal);
		file.add(item);
		item = new JMenuItem("DB �ҷ�����");
		item.addActionListener(mal);
		file.add(item);
		item = new JMenuItem("CSV ���� �ҷ�����");
		item.addActionListener(mal);
		file.add(item);
		item = new JMenuItem("CSV ���� �����ϱ�");
		item.addActionListener(mal);
		file.add(item);
		file.addSeparator();
		item = new JMenuItem("����");
		item.addActionListener(mal);
		file.add(item);
		mb.add(file);
		
		// 2. �׷���
		JMenu graph = new JMenu("�׷���");
		// 2. Items
		item = new JMenuItem("���� �׷���");
		item.addActionListener(mal);
		graph.add(item);
		item = new JMenuItem("������ �׷���");
		item.addActionListener(mal);
		graph.add(item);
		item = new JMenuItem("���� �׷���");
		item.addActionListener(mal);
		graph.add(item);
		
		mb.add(graph);
		
		// 3. ���
		JMenu statics = new JMenu("���");
		// 3. Items
		item = new JMenuItem("Ư�� ������ ��跮 ��ȸ");
		item.addActionListener(mal);
		statics.add(item);
		item = new JMenuItem("������ ��¥�� ��跮 ��ȸ");
		item.addActionListener(mal);
		statics.add(item);

		mb.add(statics);
		
		// 4. ������ ���� �޴�
		JMenu data = new JMenu("������");
		// 4. Items
		item = new JMenuItem("������ �߰� �� ����");
		item.addActionListener(mal);
		data.add(item);
		item = new JMenuItem("�������� �ǰ����");
		item.addActionListener(mal);
		data.add(item);
		
		mb.add(data);

		setJMenuBar(mb);
		
	}
	
	
	// ���� ����
	void toolbar() {
		JToolBar tb = new JToolBar("Toolbar");
		tb.setBackground(Color.gray);
		JButton item;
		item = new JButton("Open", open);
		item.addActionListener(bal);
		tb.add(item);
		item = new JButton("Save", save);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		item = new JButton("DBSave", DBLoad);
		item.addActionListener(bal);
		tb.add(item);
		item = new JButton("DBLoad", DBSave);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		
		item = new JButton("Graph 1", Graph1);
		item.addActionListener(bal);
		tb.add(item);
		item = new JButton("Graph 2", Graph2);
		item.addActionListener(bal);
		tb.add(item);
		item = new JButton("Graph 3", Graph3);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		
		item = new JButton("���� ���", Stat1);
		item.addActionListener(bal);
		tb.add(item);
		item = new JButton("��¥ ���", Stat2);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		item = new JButton("Data", Data);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		item = new JButton("����", Exit);
		item.addActionListener(bal);
		tb.add(item);
	
		
		tb.setFloatable(false);
		
		top_area.add(tb, BorderLayout.NORTH);
	}
	
	
	
	// ����ȭ�� ���̾ƿ�
	void mainLayout() {	
		setLayout(new BorderLayout(10,50));
		
		
		/// p1  ::
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		// ���� �޺��ڽ�
		p1.add(locations);
		// ���̺� 1
		JLabel inputDate_ex = new JLabel("��¥�Է� ");
		p1.add(inputDate_ex);
		// ��¥ �Է� �ؽ�Ʈ�ʵ�
		//JTextField inputDate = new JTextField("ex) 20180101", 8);
		// �ؽ�Ʈ�ʵ� Ŭ���� �ʱ� �Է� ����
		inputDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inputDate.setText("");
			}
			
		});
		p1.add(inputDate);
		// Ȯ�� ��ư
		JButton send = new JButton("�˻�");
		send.addActionListener(bal);
		p1.add(send);
		
		JButton drawing = new JButton("�׸���");
		drawing.addActionListener(bal);
		p1.add(drawing);
		
		/// p2 :: ����� ���� �׸� �� ��
		JPanel p2 = new JPanel();
		map = new Map();
		map.setPreferredSize(new Dimension(600, 450)); // w, h
			
		p2.add(map);
		p2.setPreferredSize(new Dimension(600, 450));
			
		/// p3 :: ������ ��� �� ��
		JPanel p3 = new JPanel();
		// JTable
		

		// ���̺� �� ���� & ���̺� ����
		model = new DefaultTableModel(Constant.header, 0) {
			public boolean isCellEditable(int i, int c) { // ���� ����
				return false;
			}
		};
		resTable = new JTable(model);

		// ����, �̵�����. ������ ����
		resTable.getTableHeader().setReorderingAllowed(false);
		resTable.getTableHeader().setResizingAllowed(false);
		resTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		// ���̺� ��� Ŭ���� ����
		//resTable.setAutoCreateRowSorter(true); :: String ���� ������.
		// ������ġ ������ ���� Sorter ����
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
		p3.add(jsp);
		resTable.updateUI();
		
		
		// ���ο� �гε� �߰�
		top_area.add(p1, BorderLayout.CENTER); 
		
		main_area.add(p2, BorderLayout.NORTH);
		main_area.add(p3, BorderLayout.SOUTH);	
	}
	

	public static void insertTable(ArrayList<Location> arrayList, DefaultTableModel mod) {
		for(int i=0; i<arrayList.size(); i++) {
			Location location = arrayList.get(i);
			String name = location.getName();
			LocalDate date = location.getDate();
			Stat stat = location.getStat();
			
			String[] one = {name, date.toString(), Double.toString(stat.nppm),
					Double.toString(stat.oppm), Double.toString(stat.cppm), Double.toString(stat.appm),
					Double.toString(stat.dust), Double.toString(stat.mdust)
			};
			
			mod.addRow(one);
			
		}
	}
	


	// data�� �ҷ������� isOpen = true �Ѵ�.
	public static void setOpen(boolean tf) {
		if (tf){
			isOpen = true;
		} else {
			isOpen = false;
		}
	}
	
	public static boolean getOpen() {
		return isOpen;
	}
	
	

}
