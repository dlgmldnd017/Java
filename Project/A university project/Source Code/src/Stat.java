
public class Stat {
	protected double nppm;		//이산화질소
	protected double oppm;		//오존농도
	protected double cppm;		//이산화탄소
	protected double appm;		//아황산가스
	protected double dust;		//미세먼지
	protected double mdust;		//초미세먼지
	
	
	public Stat(double nppm, double oppm, double cppm, double appm, double dust, double mdust) {
		this.nppm = nppm;
		this.oppm = oppm;
		this.cppm = cppm;
		this.appm = appm;
		this.dust = dust;
		this.mdust = mdust;
	}

	public Stat(String nppm, String oppm, String cppm, String appm, String dust, String mdust) {
		this.nppm = Double.parseDouble(nppm);
		this.oppm = Double.parseDouble(oppm);
		this.cppm = Double.parseDouble(cppm);
		this.appm = Double.parseDouble(appm);
		this.dust = Integer.parseInt(dust);
		this.mdust = Integer.parseInt(mdust);
	}
	
	// item에 해당하는 농도를 찾아서 리턴
	public double getPpm(String item) {
		double result = 0;
		switch (item) {
		case "이산화질소":
			result = nppm;
			break;
		case "오존농도":
			result = oppm;
			break;
		case "이산화탄소":
			result = cppm;
			break;
		case "아황산가스":
			result = appm;
			break;
		case "미세먼지":
			result = dust;
			break;
		case "초미세먼지":
			result = mdust;
			break;
		default:
			break;
		}
		
		
		return result;
	}
	
	
	
}
