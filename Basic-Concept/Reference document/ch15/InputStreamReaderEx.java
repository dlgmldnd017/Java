import java.io.*;

class InputStreamReaderEx {
	public static void main(String[] args) {
		String line = "";
		
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new InputStreamReader(isr);
			
			System.out.println("현재 os의 인코딩은 " + isr.getEncoding());
			
			do {
				System.out.println("문장을 입력하세요. 마치시려면 q 입력");
				line = br.readLine();
				System.out.println("입력하신 문장: " + line);
			}while(!(line.equalsIgnoreCase("q")))
		}
	} // main
}
