import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


public class Table {
	
	// ���̺� �� �� �Է�
	public void insertLine(Location input, DefaultTableModel mod) {
		
		Location location = input;
		String name = location.getName();
		LocalDate date = location.getDate();
		Stat stat = location.getStat();
		
		String[] one = {name, date.toString(), Double.toString(stat.nppm),
				Double.toString(stat.oppm), Double.toString(stat.cppm), Double.toString(stat.appm),
				Double.toString(stat.dust), Double.toString(stat.mdust)
		};
		
		mod.addRow(one);		
	}
	
	// ���̺� ��ü �ʱ�ȭ(����)
	public static void deleteTable(int index, DefaultTableModel mod) {
		//System.out.println(mod.getValueAt(index, 0) + " " + mod.getValueAt(index, 1));
		mod.removeRow(index);
		//Frame.model.fireTableDataChanged();
	}
		
	// ���̺� ���� - index�� row�� ������
	public void changeTable(int index, Location input, DefaultTableModel mod) {
		Location location = input;
		String name = location.getName();
		LocalDate date = location.getDate();
		Stat stat = location.getStat();

		Vector<String> one = new Vector<String>();
		one.addElement(name);
		one.addElement(date.toString());
		one.addElement(Double.toString(stat.nppm));
		one.addElement(Double.toString(stat.oppm));
		one.addElement(Double.toString(stat.cppm));
		one.addElement(Double.toString(stat.appm));
		one.addElement(Double.toString(stat.dust));
		one.addElement(Double.toString(stat.mdust));
		
		mod.removeRow(index);
		mod.insertRow(index, one);
		
		
	}
	

}
