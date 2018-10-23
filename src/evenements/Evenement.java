package evenements;

public abstract class Evenement {
	protected long date;
	protected boolean aExecuter;
	
	public Evenement(long date) {
		this.date = date;
		this.aExecuter = true;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setAExecuter(boolean b) {
		this.aExecuter = b;
	}
	
	public boolean aExecuter() {
		return this.aExecuter;
	}
	
	//execute les actions d'un évenement
	public abstract void execute();
}
