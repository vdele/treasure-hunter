package treasures.hunter.bo;

public abstract class Case {
	public static final int PLAIN = 0;
	public static final int MOUNTAIN = 1;
	public static final int TREASURE= 2;
	
	
	private int caseType;
	
	public Case(int caseType){
		this.caseType= caseType;
	}

	protected int getCaseType() {
		return caseType;
	}

	public String toString(){
		return Integer.toString(caseType);
	}
	
	
	
	
}
