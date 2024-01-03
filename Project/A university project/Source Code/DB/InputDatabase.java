import java.sql.*;
import java.util.ArrayList;

public class InputDatabase{
   private static final String URL = 
         "jdbc:mysql://localhost:3306/project?characterEncoding=UTF-8&serverTimezone=UTC";
   private static final String ID = "root";
   private static final String PW = "123123";
   private static Connection connection = null;
   static int count =1;
   InputDatabase(){
	   
   }
   InputDatabase(int a) throws SQLException{
      Connect_Database();
   }
   void Connect_Database() throws SQLException {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         
         System.out.println("Success Driver Load");
      } catch(ClassNotFoundException e) {
         System.out.println("Fail Driver Load: " + e.getMessage());
      }
      connection = DriverManager.getConnection(URL, ID, PW);
      
   }
   
   public static Connection getConnection() {
      return connection;
   }
   
   // DB에 데이터를 저장하는 메소드
   public static void loadData(ArrayList<Location> Locations) {
      StringBuilder sql = new StringBuilder(); 
      PreparedStatement pstmt;
      PreparedStatement pstmt1;
      
      sql.append("INSERT INTO j_stat (날짜, 지역, 이산화질소, 오존농도, 이산화탄소, 아황산가스, 미세먼지, 초미세먼지)");   //Date, Location, nppm , oppm, cppm, appm, dust, mdust
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
      try {
    	 connection.setAutoCommit(false);
         pstmt = getConnection().prepareStatement(sql.toString());
         pstmt1 = getConnection().prepareStatement("delete from j_stat");
         pstmt1.execute();
         pstmt1.close();
         
         // 데이터를 하나씩 뽑아 DB에 등록한다.
         for(Location p: Locations) {
            pstmt.setString(1, p.getDate().toString());
            pstmt.setString(2, p.getName());
            pstmt.setString(3, Double.toString(p.getStat().nppm));
            pstmt.setString(4, Double.toString(p.getStat().oppm));
            pstmt.setString(5, Double.toString(p.getStat().cppm));
            pstmt.setString(6, Double.toString(p.getStat().appm));
            pstmt.setString(7, Double.toString(p.getStat().dust));
            pstmt.setString(8, Double.toString(p.getStat().mdust));
            pstmt.addBatch();
            //pstmt.clearBatch();
            if(count%1000 == 0) {
            	pstmt.executeBatch();
            	pstmt.clearBatch();
            }
            System.out.println(count++ +" 번째 들어가는 중...");
            
         }
         pstmt.executeBatch();
         connection.commit();
         pstmt.close();
         count = 1;
      } catch(SQLException e) {
         System.out.println("DB 데이터 등록 실패: " + e.getMessage());
      }
   }
 
}

