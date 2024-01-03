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
	
	private static boolean isOpen; // 데이터가 들어왔는지 확인하는 변수
	
	static ArrayList<String[]> data = new ArrayList<String[]>();
	static String[][] arr = new String[13000][8];

	JPanel top_area = new JPanel(new BorderLayout());	// 상단에 위치하는 툴바&컴포넌트 자리
	JPanel main_area = new JPanel(new BorderLayout());	// 메인
	
	MenuActionListener mal = new MenuActionListener();
	ButtonActionListener bal = new ButtonActionListener();
	
	
	static JScrollPane jsp;
	static JTable resTable;
	static DefaultTableModel model;
	Vector<String> dataRow;

	public static Map map;
	
	static GraphDialog cgDialog;	// 원형그래프 대화창
	static GraphDialog lgDialog;	// 선형그래프 대화창
	static GraphDialog bgDialog;	// 막대그래프
	static DataDialog tbDialog;		// 데이터 수정
	static TextDialog txDialog;		// 기타 정보 대화창
	static StaticsDialog stDialog;		// 통계 정보 대화창 1
	static StaticsDialog stDialog2;		// 통계 정보 대화창 2
	
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
	
		cgDialog = new GraphDialog(this, "원형그래프", 0);
		lgDialog = new GraphDialog(this, "꺽은선그래프", 1);
		bgDialog = new GraphDialog(this, "막대그래프", 2);
		tbDialog = new DataDialog(this, "데이터");
		txDialog = new TextDialog(this, "오염물질 권고기준");
		stDialog = new StaticsDialog(this, "지역별 통계 정보", 0);
		stDialog2 = new StaticsDialog(this, "날짜별 통계 정보", 1);
		
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
	}
	
	// 메뉴바 구성
	void menu() {
		JMenuItem item;
		JMenuBar mb = new JMenuBar();
		// 메뉴 구성
		
		// 1. 파일
		JMenu file = new JMenu("파일");
		// 1. Items
		item = new JMenuItem("DB 저장");
		item.addActionListener(mal);
		file.add(item);
		item = new JMenuItem("DB 불러오기");
		item.addActionListener(mal);
		file.add(item);
		item = new JMenuItem("CSV 파일 불러오기");
		item.addActionListener(mal);
		file.add(item);
		item = new JMenuItem("CSV 파일 저장하기");
		item.addActionListener(mal);
		file.add(item);
		file.addSeparator();
		item = new JMenuItem("종료");
		item.addActionListener(mal);
		file.add(item);
		mb.add(file);
		
		// 2. 그래프
		JMenu graph = new JMenu("그래프");
		// 2. Items
		item = new JMenuItem("원형 그래프");
		item.addActionListener(mal);
		graph.add(item);
		item = new JMenuItem("꺽은선 그래프");
		item.addActionListener(mal);
		graph.add(item);
		item = new JMenuItem("막대 그래프");
		item.addActionListener(mal);
		graph.add(item);
		
		mb.add(graph);
		
		// 3. 통계
		JMenu statics = new JMenu("통계");
		// 3. Items
		item = new JMenuItem("특정 지역의 통계량 조회");
		item.addActionListener(mal);
		statics.add(item);
		item = new JMenuItem("선택한 날짜의 통계량 조회");
		item.addActionListener(mal);
		statics.add(item);

		mb.add(statics);
		
		// 4. 데이터 관련 메뉴
		JMenu data = new JMenu("데이터");
		// 4. Items
		item = new JMenuItem("데이터 추가 및 수정");
		item.addActionListener(mal);
		data.add(item);
		item = new JMenuItem("오염물질 권고기준");
		item.addActionListener(mal);
		data.add(item);
		
		mb.add(data);

		setJMenuBar(mb);
		
	}
	
	
	// 툴바 구성
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
		
		item = new JButton("지역 통계", Stat1);
		item.addActionListener(bal);
		tb.add(item);
		item = new JButton("날짜 통계", Stat2);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		item = new JButton("Data", Data);
		item.addActionListener(bal);
		tb.add(item);
		tb.addSeparator();
		item = new JButton("종료", Exit);
		item.addActionListener(bal);
		tb.add(item);
	
		
		tb.setFloatable(false);
		
		top_area.add(tb, BorderLayout.NORTH);
	}
	
	
	
	// 메인화면 레이아웃
	void mainLayout() {	
		setLayout(new BorderLayout(10,50));
		
		
		/// p1  ::
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		// 지역 콤보박스
		p1.add(locations);
		// 레이블 1
		JLabel inputDate_ex = new JLabel("날짜입력 ");
		p1.add(inputDate_ex);
		// 날짜 입력 텍스트필드
		//JTextField inputDate = new JTextField("ex) 20180101", 8);
		// 텍스트필드 클릭시 초기 입력 삭제
		inputDate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				inputDate.setText("");
			}
			
		});
		p1.add(inputDate);
		// 확인 버튼
		JButton send = new JButton("검색");
		send.addActionListener(bal);
		p1.add(send);
		
		JButton drawing = new JButton("그리기");
		drawing.addActionListener(bal);
		p1.add(drawing);
		
		/// p2 :: 서울시 지도 그림 들어갈 곳
		JPanel p2 = new JPanel();
		map = new Map();
		map.setPreferredSize(new Dimension(600, 450)); // w, h
			
		p2.add(map);
		p2.setPreferredSize(new Dimension(600, 450));
			
		/// p3 :: 데이터 출력 할 곳
		JPanel p3 = new JPanel();
		// JTable
		

		// 테이블 모델 생성 & 테이블 생성
		model = new DefaultTableModel(Constant.header, 0) {
			public boolean isCellEditable(int i, int c) { // 수정 금지
				return false;
			}
		};
		resTable = new JTable(model);

		// 수정, 이동방지. 사이즈 조정
		resTable.getTableHeader().setReorderingAllowed(false);
		resTable.getTableHeader().setResizingAllowed(false);
		resTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		// 테이블 헤더 클릭시 정렬
		//resTable.setAutoCreateRowSorter(true); :: String 기준 정렬임.
		// 오염수치 정렬을 위한 Sorter 정의
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
		
		// 패널에 테이블 추가
		jsp = new JScrollPane(resTable);
		jsp.setPreferredSize(new Dimension(600,400));
		p3.add(jsp);
		resTable.updateUI();
		
		
		// 메인에 패널들 추가
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
	


	// data를 불러왔으면 isOpen = true 한다.
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
