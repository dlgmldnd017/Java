


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
			//JOptionPane.showMessageDialog(null, "���� DB����� �̱����Դϴ�.", "������ ����", JOptionPane.ERROR_MESSAGE);
			try {
				Frame.csvL.Reset();
				Frame.csvL.Read();
				// �ʱ�ȭ
				int rowCount = Frame.model.getRowCount();
				for(int i=0; i<rowCount; i++) {
					Table.deleteTable(0, Frame.model);
				}	
				Frame.model.fireTableDataChanged();
				// ������ �߰�
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
			//JOptionPane.showMessageDialog(null, "���� DB����� �̱����Դϴ�.", "������ ����", JOptionPane.ERROR_MESSAGE);
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
			// �ʱ�ȭ	
			Frame.model.fireTableDataChanged();
			try {
				new OuputDatabase(4);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			OuputDatabase.SaveData();
			// ������ �߰�
			Frame.insertTable(OuputDatabase.from_DB, Frame.model);
			Frame.setOpen(true);
			
			break;
		case "Graph 1":
			// ���̺��� ��� ���õǾ������� �׷������� �Ѵ�.
			switch(Frame.resTable.getSelectedRowCount()) {
			case 0:
				JOptionPane.showMessageDialog(null, "���õ� Į���� �����ϴ�!");
				break;
			case 1:
				// ������ Į������ ������ ��������
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
				
				// ������ ���� ��Ű��
				Frame.cgDialog.setNumbers(pol1, pol2, pol3, pol4, pol5, pol6);
				// �����͸� ������� �׷��� �׸���
				Frame.cgDialog.setVisible(true);
				break;
			default:
				JOptionPane.showMessageDialog(null, "���� �׷����� �ϳ��� Į���� �׸� �� �ֽ��ϴ�.");
				break;
					
			}			
			
			break;
		case "Graph 2":
			Frame.lgDialog.init();
			// ���� �׷���. ���۳�¥, ����¥, ������ �����ϰ� �׷��� â ���
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "���� �����͸� �ҷ��;��մϴ�!");
				return;
			}
			// ������ �����ϰ� �Ⱓ�� �Է��ϴ� �˾�â�� ����
			try{
				LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("�����ϴ� ��¥�� �Է��ϼ���. ex) 2018-01-06"));
				LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("������ ��¥�� �Է��ϼ���. ex) 2018-01-16"));
				Frame.lgDialog.setDate(start, end);
			} catch(Exception err) {
				JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// ���� ����
			String[] locations = Constant.locations;
			Object selectedLocation = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ������ �ϳ� �����ϼ���.\n��ü�� �������Դϴ�.", "Selection", JOptionPane.DEFAULT_OPTION, null, locations, "1");
			if ( selectedLocation != null ){ 
			    Frame.lgDialog.setLocation(selectedLocation.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			// �������� ����(1��)
			String[] values1 = Constant.pollut;
			Object selected1 = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ���������� �ϳ� �����ϼ���.", "Selection", JOptionPane.DEFAULT_OPTION, null, values1, "0");
			if ( selected1 != null ){ 
				Frame.lgDialog.setItem(selected1.toString());			    
			}else{
			    JOptionPane.showMessageDialog(null, "item selection Error !!!", "Unknowun Error", JOptionPane.ERROR_MESSAGE);
			}
			
			
			Frame.lgDialog.setVisible(true);
			
			// �ش� �Ⱓ ������ ����
			//Period.between(start, end);
			
			
			// ������ ����
			
			// ������ ������� �׷��� �׸���
			// Frame.lgDialog.setVisible(true);
			break;
		case "Graph 3":
			// ����׷���. ��¥�� ������ �ϳ�����. ��� ���� ��� 
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "���� �����͸� �ҷ��;��մϴ�!");
				return;
			}
			// ��¥ ����
			String inputDate = JOptionPane.showInputDialog("��¥�� �Է��ϼ���. ex) 2018-01-06");
			// inputDate ��ȿ�� ����
			try{
				LocalDate localDate = LocalDate.parse(inputDate);
				Frame.bgDialog.setDate(localDate);
			} catch(Exception err) {
				JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// �������� ����(1��)
			String[] values2 = Constant.pollut;
			Object selectedItem2 = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ���������� �ϳ� �����ϼ���.", "Selection", JOptionPane.DEFAULT_OPTION, null, values2, "0");
			if ( selectedItem2 != null ){ 
			    Frame.bgDialog.setItem(selectedItem2.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			
			
			Frame.bgDialog.setVisible(true);
			
			break;
		case "���� ���":
			// �� �������� Ư�� �Ⱓ������ ��跮�� ����
			// �ʱ�ȭ
			Frame.stDialog.init();	
			// �Ⱓ �Է�
			// �� �Ⱓ���� A ������ ���� ���� ��, ���� ��.  B ������ ���� ������ ������. ...
			
			// ���� �Է�â
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "���� �����͸� �ҷ��;��մϴ�!");
				return;
			}
			// �Ⱓ�� �Է��ϴ� �˾�â�� ����
			
			try{
				LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("�����ϴ� ��¥�� �Է��ϼ���. ex) 2018-01-06"));
				LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("������ ��¥�� �Է��ϼ���. ex) 2018-01-16"));
				//System.out.println(start.toString() + end.toString());
				Frame.stDialog.setDate(start, end);
				//Frame.stDialog.setType(0);
			} catch(Exception err) {
				JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// ���� ����â�� ����.
			String[] ls = Constant.locations;
			Object selectedL = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ������ �ϳ� �����ϼ���.\n��ü�� �������Դϴ�.", "Selection", JOptionPane.DEFAULT_OPTION, null, ls, "1");
			if ( selectedL != null ){ 
			    Frame.stDialog.setLocation(selectedL.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			
			//Frame.stDialog.setType(0);
			Frame.stDialog.setVisible(true);		
			break;
		case "��¥ ���":
			// Ư�� ��¥�� ��跮 ��ȸ
			// ��� ���������� ��跮�� ���� 
			if (Frame.getOpen() != true) {
				JOptionPane.showMessageDialog(null, "���� �����͸� �ҷ��;��մϴ�!");
				return;
			}
			LocalDate targetDate;
			try {
				targetDate = LocalDate.parse(JOptionPane.showInputDialog("��¥�� �Է��ϼ���. ex)2018-01-06"));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "�߸��� ��¥�Դϴ�!", "�Է¿���", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			Frame.stDialog2.setDate(targetDate);
			//Frame.stDialog2.setType(1);
			Frame.stDialog2.setVisible(true);
			break;
		case "Data":
			// ������ ���� â�� ���
			Frame.tbDialog.setVisible(true);
			
			break;
		case "�˻�":
			//Frame.csvL.Reset();
			//Frame.csvL.Read();
			//Frame.insertTable(Frame.csvL.getlocations(), Frame.model);
			
			String searchLoca = Frame.locations.getSelectedItem().toString();
			String searchDate = Frame.inputDate.getText();
			
			// searchLoca & searchDate �������� �˻�
			if(searchLoca.equals("��ü")) {
				if(searchDate.equals("")) {
					// ����ִ� ���� ��ü ��¥�� �˻�
					int rowCount1 = Frame.model.getRowCount();
					for(int i=0; i<rowCount1; i++) {
						Table.deleteTable(0, Frame.model);
					}	
					Frame.insertTable(Frame.csvL.getlocations(), Frame.model);
				}
				ArrayList<String[]> search_result = new ArrayList<String[]>();
				for (int i=0; i < Frame.model.getRowCount(); i++) {
					// date�� ��ġ�ϴ� ���� ������ ����Ʈ�� ���� �߰���.
					if(searchDate.equals(Frame.model.getValueAt(i, 1))) {
						String[] in = new String[8];
						for(int j=0; j<8; j++) {
							in[j] = (String) Frame.model.getValueAt(i, j);
						}
						search_result.add(in);
					}
				}
				// ���� ȭ���� ���̺� ���� ����
				if(search_result.size() > 0) {
					int count = Frame.model.getRowCount();
					for(int i=0; i<count; i++) {
						//Frame.model.removeRow(0);
						Table.deleteTable(0, Frame.model);
					}
					// search_result�� �̿��� ���̺� �����
					for (int i=0; i<search_result.size(); i++) {
						Frame.model.addRow(search_result.get(i));
					}	
				} else if(searchDate.equals("")) {
					JOptionPane.showMessageDialog(null, "��ü �˻� ����� �ҷ��ɴϴ�.");
				} else {// �˻� ����� ���� ���.
					JOptionPane.showMessageDialog(null, "��ġ�ϴ� �˻� ����� �����ϴ�!");
				}
				break;
			}
			
			
			
			
			// ���� ��ü�� ��¥ �ݿ�
			Frame.map.setDate(searchDate);
			
			
			break;
		case "�׸���":
			String sd = Frame.inputDate.getText();
			
			// �������� ���� �˾� ����
			// �������� ����(1��)
			String[] values3 = Constant.pollut;
			Object selectedItem3 = JOptionPane.showInputDialog(null, "��ȸ�ϰ� ���� ���������� �ϳ� �����ϼ���.", "Selection", JOptionPane.DEFAULT_OPTION, null, values3, "0");
			if ( selectedItem3 != null ){ 
			    Frame.map.setItem(selectedItem3.toString());
			}else{
			    //System.out.println("User cancelled");
			}
			
			
			
			Frame.map.setDate(sd);
			Frame.map.repaint();
			break;
		case "����":
			System.exit(0);
			break;
			
		}
			
		
	}



	
	
}
