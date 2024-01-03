import java.time.LocalDate;

public class Location {
   private String      name;   // ���ø�
   private LocalDate   date;   // �Ⱓ
   private Stat stat;   // ��������
   
   Location(LocalDate date, String name, Stat c) {
	  this.date = date;
      this.name = name;
      this.stat = c;
    
   }
   
   // ���ø��� ��ȯ�Ѵ�.
   public String getName() {
      return name;
   }
   public void setName(String name) {
	   this.name = name;
   }
   
   // ��¥�� ��ȯ�Ѵ�.
   public LocalDate getDate() {
      return date;
   }
   
   // �������� ��ü�� ��ȯ�Ѵ�.
   public Stat getStat() {
      return stat;
   }
   
   public void setStat(Stat in) {
	   this.stat = in;
   }
}