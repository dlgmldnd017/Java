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
		      
		      // csv파일 읽어오기
		      //br = new BufferedReader(new FileReader("C://Users//dlgml//eclipse-workspace//POL//src//input.CSV"));
		      br = new BufferedReader(new FileReader(".//CSV//input.CSV"));
		      
		      // 한 줄은 항목 명이므로 넘기기
		      br.readLine();
		      //locations = new ArrayList();
		      // 끝까지 한 줄씩 읽어오기
		      while((lineString = br.readLine()) != null) {
		         String[] splitedString;
		         LocalDate date;
		         Stat stat;
		         Location l;
		         
		         splitedString = lineString.split(",", 8);

		         // 분리한 문자열에서 오염물질 수치가 비어있는 경우 -1로 초기화한다.
		         for(int i = 2; i < splitedString.length; i++) {
		            if(splitedString[i].equals("")) {
		               splitedString[i] = "-1";
		            }
		         }
		         
		         
		         // 오염물질 데이터 생성
		         stat = new Stat(Double.parseDouble(splitedString[2]), Double.parseDouble(splitedString[3]), Double.parseDouble(splitedString[4]),
		              Double.parseDouble(splitedString[5]), Integer.parseInt(splitedString[6]), Integer.parseInt(splitedString[7]));
		         
		         
		         // 문자열로 표현된 날짜를 LocalDate형식으로 변환하고, 장소 데이터 생성
		         date = LocalDate.parse(splitedString[0], DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH));
		         l = new Location(date,splitedString[1], stat);
		         //System.out.println(l.getName() + l.getDate() + l.getStat());
		         
		         // 전체 데이터에 현재 데이터를 추가한다.
		         locations.add(l);
		         //System.out.println(locations.size());
		         //System.out.println(l.getName() + l.getDate() + l.getStat());  
		      }
		      br.close();
		   }
	  	// 전체 데이터에 대한 지역 목록을 반환한다.
	   public ArrayList<Location> getlocations() {
	      return locations;
	   }
	   
	   // 이름 오름차순 정렬
	   
	   
	   // locations 초기화
	   public void Reset() {
		   locations.clear();
	   }
	   
	  // 특정 location 찾아내기. - 지역명과 날짜를 통해
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
