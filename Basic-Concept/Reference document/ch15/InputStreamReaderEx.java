import java.io.*;

class InputStreamReaderEx {
	public static void main(String[] args) {
		String line = "";
		
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new InputStreamReader(isr);
			
			System.out.println("���� os�� ���ڵ��� " + isr.getEncoding());
			
			do {
				System.out.println("������ �Է��ϼ���. ��ġ�÷��� q �Է�");
				line = br.readLine();
				System.out.println("�Է��Ͻ� ����: " + line);
			}while(!(line.equalsIgnoreCase("q")))
		}
	} // main
}
