import java.io.*;
import java.util.*;

public class CSVWrite {
   public void Write() throws IOException{
      //PrintWriter pw = new PrintWriter("C://Users//dlgml//eclipse-workspace//POL//src//out.csv");
     PrintWriter pw = new PrintWriter(".//CSV//output.CSV");
     String item = "측정 일시"+","+"측정소명"+","+"이산화질소"+","+"오존농도"+","+"이산화탄소"+","+"아황산가스"+","+"미세먼지"+","+"초미세먼지";   //첫째 줄 항목들을 넣기 위한 item 변수
     pw.println(item);
     for(Location stat : CSVLoad.locations) {
            String data = stat.getDate()+","+ stat.getName()+","+stat.getStat().nppm +","+ stat.getStat().oppm+","+stat.getStat().cppm
                  +","+stat.getStat().appm+","+stat.getStat().dust +","+stat.getStat().mdust;
            pw.println(data);
            //System.out.println(data);
        }
     pw.close(); // 이 구문을 추가하니까 출력이 끝까지 완전하게 되었다. 왜 그런지 나중에 확인해보자
   }
}