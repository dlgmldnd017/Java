


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ButtonActionListener implements ActionListener{

	
	DefaultTableModel dtm = new DefaultTableModel();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) (e.getSource());

		switch (btn.getText()) {

		case "Open":
			//JOptionPane.showMessageDialog(null, "아직 DB기능은 미구현입니다.", "데이터 없음", JOptionPane.ERROR_MESSAGE);
			try {
				Frame.csvL.Reset();
				Frame.csvL.Read();
				// 초기화
				int rowCount = Frame.model.getRowCount();
				for(int i=0; i<rowCount; i++) {
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
		case "Save":
			try {
				Frame.csvW.Write();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//

			}
			//JOptionPane.showMessageDialog(null, "아직 DB기능은 미구현입니다.", "데이터 없음", JOptionPane.ERROR_MESSAGE);
			break;
		case "DBSave":
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
		case "DBLoad":
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
		case "Graph 1":
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
		case "Graph 2":
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
			
			// 해당 기간 데이터 추출
			//Period.between(start, end);
			
			
			// 데이터 적용
			
			// 데이터 기반으로 그래프 그리기
			// Frame.lgDialog.setVisible(true);
			break;
		case "Graph 3":
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
			String[] values2 = Constant.pollut;
			Object selectedItem2 = JOptionPane.showInputDialog(null, "조회하고 싶은 오염물질을 하나 선택하세요.", "Selection", JOptionPane.DEFAULT_OPTION, null, values2, "0");
			if ( selectedItem2 != null ){ 
			    Frame.bgDialog.setItem(selectedItem2.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			
			
			Frame.bgDialog.setVisible(true);
			
			break;
		case "지역 통계":
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
				//System.out.println(start.toString() + end.toString());
				Frame.stDialog.setDate(start, end);
				//Frame.stDialog.setType(0);
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
			
			//Frame.stDialog.setType(0);
			Frame.stDialog.setVisible(true);		
			break;
		case "날짜 통계":
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
		case "Data":
			// 데이터 수정 창을 출력
			Frame.tbDialog.setVisible(true);
			
			break;
		case "검색":
			//Frame.csvL.Reset();
			//Frame.csvL.Read();
			//Frame.insertTable(Frame.csvL.getlocations(), Frame.model);
			
			String searchLoca = Frame.locations.getSelectedItem().toString();
			String searchDate = Frame.inputDate.getText();
			
			// searchLoca & searchDate 조건으로 검색
			if(searchLoca.equals("전체")) {
				if(searchDate.equals("")) {
					// 비어있는 경우는 전체 날짜를 검색
					int rowCount1 = Frame.model.getRowCount();
					for(int i=0; i<rowCount1; i++) {
						Table.deleteTable(0, Frame.model);
					}	
					Frame.insertTable(Frame.csvL.getlocations(), Frame.model);
				}
				ArrayList<String[]> search_result = new ArrayList<String[]>();
				for (int i=0; i < Frame.model.getRowCount(); i++) {
					// date와 일치하는 행을 가지고 리스트로 만들어서 추가함.
					if(searchDate.equals(Frame.model.getValueAt(i, 1))) {
						String[] in = new String[8];
						for(int j=0; j<8; j++) {
							in[j] = (String) Frame.model.getValueAt(i, j);
						}
						search_result.add(in);
					}
				}
				// 현재 화면의 테이블 전부 삭제
				if(search_result.size() > 0) {
					int count = Frame.model.getRowCount();
					for(int i=0; i<count; i++) {
						//Frame.model.removeRow(0);
						Table.deleteTable(0, Frame.model);
					}
					// search_result를 이용해 테이블 재생성
					for (int i=0; i<search_result.size(); i++) {
						Frame.model.addRow(search_result.get(i));
					}	
				} else if(searchDate.equals("")) {
					JOptionPane.showMessageDialog(null, "전체 검색 결과를 불러옵니다.");
				} else {// 검색 결과가 없는 경우.
					JOptionPane.showMessageDialog(null, "일치하는 검색 결과가 없습니다!");
				}
				break;
			}
			
			
			
			
			// 지도 객체에 날짜 반영
			Frame.map.setDate(searchDate);
			
			
			break;
		case "그리기":
			String sd = Frame.inputDate.getText();
			
			// 오염물질 선택 팝업 띄우기
			// 오염물질 선택(1개)
			String[] values3 = Constant.pollut;
			Object selectedItem3 = JOptionPane.showInputDialog(null, "조회하고 싶은 오염물질을 하나 선택하세요.", "Selection", JOptionPane.DEFAULT_OPTION, null, values3, "0");
			if ( selectedItem3 != null ){ 
			    Frame.map.setItem(selectedItem3.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			
			
			
			Frame.map.setDate(sd);
			Frame.map.repaint();
			break;
		case "종료":
			System.exit(0);
			break;
			
		}
			
		
	}



	
	
}
