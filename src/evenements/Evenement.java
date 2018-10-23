package evenements;

public abstract class Evenement {
	protected long date;

	
	public Evenement(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return date;
	}
	
	//execute les actions d'un évenement
	public abstract void execute();
}
