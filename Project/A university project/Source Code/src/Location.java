import java.time.LocalDate;

public class Location {
   private String      name;   // 도시명
   private LocalDate   date;   // 기간
   private Stat stat;   // 오염물질
   
   Location(LocalDate date, String name, Stat c) {
	  this.date = date;
      this.name = name;
      this.stat = c;
    
   }
   
   // 도시명을 반환한다.
   public String getName() {
      return name;
   }
   public void setName(String name) {
	   this.name = name;
   }
   
   // 날짜를 반환한다.
   public LocalDate getDate() {
      return date;
   }
   
   // 오염물질 객체를 반환한다.
   public Stat getStat() {
      return stat;
   }
   
   public void setStat(Stat in) {
	   this.stat = in;
   }
}