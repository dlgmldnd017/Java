import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OuputDatabase{
   private static final String URL = 
         "jdbc:mysql://localhost:3306/project?characterEncoding=UTF-8&serverTimezone=UTC";
   private static final String ID = "root";
   private static final String PW = "123123";
   private static Connection connection = null;
   static ArrayList<Location> from_DB = new ArrayList<Location>();
   static int count = 1;
   static boolean check = false;
   OuputDatabase(){
	   
   }
   OuputDatabase(int a) throws SQLException{
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
   
   // DB���� ���α׷����� �����͸� �ҷ����� �޼ҵ�
   public static void SaveData() {
	  check = true;
      String sql;
      PreparedStatement pstmt;
      ResultSet rs;
      
      sql = "SELECT * FROM j_stat";
      
      try {
    	 connection.setAutoCommit(false);
         pstmt = getConnection().prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         // ��� �׸��� �����´�
         while(rs.next()) {
            Location p;
            Stat stat;
            LocalDate date;
            
            stat = new Stat(Double.parseDouble(rs.getString("�̻�ȭ����")), Double.parseDouble(rs.getString("������")), Double.parseDouble(rs.getString("�̻�ȭź��"))
                  , Double.parseDouble(rs.getString("��Ȳ�갡��")), Double.parseDouble(rs.getString("�̼�����")), Double.parseDouble(rs.getString("�ʹ̼�����")));
            
            date = LocalDate.parse(rs.getString("��¥"), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
            p = new Location(date, rs.getString("����"),  stat);
            
            from_DB.add(p);
            count ++;
            System.out.println(count +" ��° �������� ��...");
         }
         connection.commit();
         pstmt.close();
         
         count = 1;
        
         rs.close();
      } catch(SQLException e) {
         System.out.println("DB ������ �ҷ����� ����: " + e.getMessage());
      }
   }
   public void Reset() {
	   from_DB.clear();
   }
}