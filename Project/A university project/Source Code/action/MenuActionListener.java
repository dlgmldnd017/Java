


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuActionListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem mi = (JMenuItem) (e.getSource());

		switch (mi.getText()) {

		case "DB 저장":
			try {
				new InputDatabase(4);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(OuputDatabase.check == false) {
				InputDatabase.loadData(CSVLoad.locations);
			}else if(OuputDatabase.check == true) {
				InputDatabase.loadData(OuputDatabase.from_DB);
			}
			
			break;
			
		case "DB 불러오기":
			Frame.opdb.Reset();
			
			int rowCount = Frame.model.getRowCount();
			
			for(int i=0; i<rowCount; i++) {
				Table.deleteTable(0, Frame.model);
			}	
			// 초기화	
			Frame.model.fireTableDataChanged();
			try {
				new OuputDatabase(4);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			OuputDatabase.SaveData();
			// 데이터 추가
			Frame.insertTable(OuputDatabase.from_DB, Frame.model);
			Frame.setOpen(true);
			
			break;
		case "CSV 파일 불러오기":
			try {
				Frame.csvL.Reset();
				Frame.csvL.Read();
				// 초기화
				int rowCount1 = Frame.model.getRowCount();
				for(int i=0; i<rowCount1; i++) {
					Table.deleteTable(0, Frame.model);
				}	
				Frame.model.fireTableDataChanged();
				// 데이터 추가
				Frame.insertTable(Frame.csvL.getlocations(), Frame.model);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Frame.setOpen(true);
			break;
		case "CSV 파일 저장하기":
			try {
				Frame.csvW.Write();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//System.out.println(e1);
			}
			break;
		case "원형 그래프":
			// 테이블에서 어딘가 선택되었을때만 그려지도록 한다.
			switch(Frame.resTable.getSelectedRowCount()) {
			case 0:
				JOptionPane.showMessageDialog(null, "선택된 칼럼이 없습니다!");
				break;
			case 1:
				// 선택한 칼럼에서 데이터 가져오기
				int row = Frame.resTable.getSelectedRow();
				int col = Frame.resTable.getSelectedColumn();
				
				String area = (String) Frame.resTable.getValueAt(row, 0);
				double pol1 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 2)));
				double pol2 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 3)));
				double pol3 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 4)));
				double pol4 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 5)));
				double pol5 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 6)));
				double pol6 = Double.valueOf((String) (Frame.resTable.getValueAt(row, 7)));
				
				//System.out.println(row + ", " + col);
				//System.out.println(area + " " + pol1);
				
				// 데이터 적용 시키기
				Frame.cgDialog.setNumbers(pol1, pol2, pol3, pol4, pol5, pol6);
				// 데이터를 기반으로 그래프 그리기
				Frame.cgDialog.setVisible(true);
				break;
			default:
				JOptionPane.showMessageDialog(null, "원형 그래프는 하나의 칼럼만 그릴 수 있습니다.");
				break;
				
				
			}
			break;
		case "꺽은선 그래프":
			Frame.lgDialog.init();
			// 선형 그래프. 시작날짜, 끝날짜, 지역을 선택하고 그래프 창 출력
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "먼저 데이터를 불러와야합니다!");
				return;
			}
			// 지역을 선택하고 기간을 입력하는 팝업창을 띄운다
			try{
				LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("시작하는 날짜를 입력하세요. ex) 2018-01-06"));
				LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("끝나는 날짜를 입력하세요. ex) 2018-01-16"));
				Frame.lgDialog.setDate(start, end);
			} catch(Exception err) {
				JOptionPane.showMessageDialog(null, "잘못된 날짜입니다!", "입력오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 지역 선택
			String[] locations = Constant.locations;
			Object selectedLocation = JOptionPane.showInputDialog(null, "조회하고 싶은 지역을 하나 선택하세요.\n전체는 미지원입니다.", "Selection", JOptionPane.DEFAULT_OPTION, null, locations, "1");
			if ( selectedLocation != null ){ 
			    Frame.lgDialog.setLocation(selectedLocation.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			// 오염물질 선택(1개)
			String[] values1 = Constant.pollut;
			Object selected1 = JOptionPane.showInputDialog(null, "조회하고 싶은 오염물질을 하나 선택하세요.", "Selection", JOptionPane.DEFAULT_OPTION, null, values1, "0");
			if ( selected1 != null ){ 
				Frame.lgDialog.setItem(selected1.toString());			    
			}else{
			    JOptionPane.showMessageDialog(null, "item selection Error !!!", "Unknowun Error", JOptionPane.ERROR_MESSAGE);
			}
			
			Frame.lgDialog.setVisible(true);
			break;
		case "막대 그래프":
			// 막대그래프. 날짜와 물질을 하나선택. 모든 지역 출력 
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "먼저 데이터를 불러와야합니다!");
				return;
			}
			// 날짜 선택
			String inputDate = JOptionPane.showInputDialog("날짜를 입력하세요. ex) 2018-01-06");
			// inputDate 유효성 검증
			try{
				LocalDate localDate = LocalDate.parse(inputDate);
				Frame.bgDialog.setDate(localDate);
			} catch(Exception err) {
				JOptionPane.showMessageDialog(null, "잘못된 날짜입니다!", "입력오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 오염물질 선택(1개)
			String[] values = Constant.pollut;
			Object selected = JOptionPane.showInputDialog(null, "조회하고 싶은 오염물질을 하나 선택하세요.", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "0");
			if ( selected != null ){ 
			    Frame.bgDialog.setItem(selected.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			Frame.bgDialog.setVisible(true);			
			break;
		case "선택한 날짜의 통계량 조회":
			// 특정 날짜의 통계량 조회
			// 모든 지역에서의 통계량을 제공 
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "먼저 데이터를 불러와야합니다!");
				return;
			}
			LocalDate targetDate;
			try {
				targetDate = LocalDate.parse(JOptionPane.showInputDialog("날짜를 입력하세요. ex)2018-01-06"));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "잘못된 날짜입니다!", "입력오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
						
			Frame.stDialog2.setDate(targetDate);
			//Frame.stDialog2.setType(1);
			Frame.stDialog2.setVisible(true);
			break;
		case "특정 지역의 통계량 조회":
			// 한 지역에서 특정 기간동안의 통계량을 제공
			// 초기화
			Frame.stDialog.init();	
			// 기간 입력
			// 그 기간동안 A 물질이 가장 높은 날, 낮은 날.  B 물질이 가장 높은날 낮은날. ...
			
			// 구간 입력창
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "먼저 데이터를 불러와야합니다!");
				return;
			}
			// 기간을 입력하는 팝업창을 띄운다		
			try{
				LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("시작하는 날짜를 입력하세요. ex) 2018-01-06"));
				LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("끝나는 날짜를 입력하세요. ex) 2018-01-16"));
				Frame.stDialog.setDate(start, end);
			} catch(Exception err) {
				JOptionPane.showMessageDialog(null, "잘못된 날짜입니다!", "입력오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// 지역 선택창을 띄운다.
			String[] ls = Constant.locations;
			Object selectedL = JOptionPane.showInputDialog(null, "조회하고 싶은 지역을 하나 선택하세요.\n전체는 미지원입니다.", "Selection", JOptionPane.DEFAULT_OPTION, null, ls, "1");
			if ( selectedL != null ){ 
			    Frame.stDialog.setLocation(selectedL.toString());
			}else{
			    //System.out.println("User cancelled");
			}

			Frame.stDialog.setVisible(true);			
			break;
		case "데이터 추가 및 수정":
			Frame.tbDialog.setVisible(true);
			
			break;
		case "오염물질 권고기준":
			Frame.txDialog.setVisible(true);
			
			break;
		
		case "종료":
			System.exit(0);
			break;
			
		}
		
	}

	
	
}
