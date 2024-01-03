import java.io.*;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class CSVLoad {
	static ArrayList<Location> locations = new ArrayList<Location>();
	
	//Vector<Location> vlos;
	
	@SuppressWarnings("resource")
	public static void Read() throws IOException {
		      BufferedReader br;
		      String lineString;
		      
		      // csv���� �о����
		      //br = new BufferedReader(new FileReader("C://Users//dlgml//eclipse-workspace//POL//src//input.CSV"));
		      br = new BufferedReader(new FileReader(".//CSV//input.CSV"));
		      
		      // �� ���� �׸� ���̹Ƿ� �ѱ��
		      br.readLine();
		      //locations = new ArrayList();
		      // ������ �� �پ� �о����
		      while((lineString = br.readLine()) != null) {
		         String[] splitedString;
		         LocalDate date;
		         Stat stat;
		         Location l;
		         
		         splitedString = lineString.split(",", 8);

		         // �и��� ���ڿ����� �������� ��ġ�� ����ִ� ��� -1�� �ʱ�ȭ�Ѵ�.
		         for(int i = 2; i < splitedString.length; i++) {
		            if(splitedString[i].equals("")) {
		               splitedString[i] = "-1";
		            }
		         }
		         
		         
		         // �������� ������ ����
		         stat = new Stat(Double.parseDouble(splitedString[2]), Double.parseDouble(splitedString[3]), Double.parseDouble(splitedString[4]),
		              Double.parseDouble(splitedString[5]), Integer.parseInt(splitedString[6]), Integer.parseInt(splitedString[7]));
		         
		         
		         // ���ڿ��� ǥ���� ��¥�� LocalDate�������� ��ȯ�ϰ�, ��� ������ ����
		         date = LocalDate.parse(splitedString[0], DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH));
		         l = new Location(date,splitedString[1], stat);
		         //System.out.println(l.getName() + l.getDate() + l.getStat());
		         
		         // ��ü �����Ϳ� ���� �����͸� �߰��Ѵ�.
		         locations.add(l);
		         //System.out.println(locations.size());
		         //System.out.println(l.getName() + l.getDate() + l.getStat());  
		      }
		      br.close();
		   }
	  	// ��ü �����Ϳ� ���� ���� ����� ��ȯ�Ѵ�.
	   public ArrayList<Location> getlocations() {
	      return locations;
	   }
	   
	   // �̸� �������� ����
	   
	   
	   // locations �ʱ�ȭ
	   public void Reset() {
		   locations.clear();
	   }
	   
	  // Ư�� location ã�Ƴ���. - ������� ��¥�� ����
	   public static Location findLocation(String name, LocalDate ldate) {
		   for (int i=0; i<locations.size(); i++) {
			   if (locations.get(i).getName().equals(name) &&
				   locations.get(i).getDate().equals(ldate) ) {   
				   //System.out.println(i);
				   Location result = locations.get(i);
				   return result;
			   }
		   }
		   
		   return null;
	   }
	   
	   
	   
}
