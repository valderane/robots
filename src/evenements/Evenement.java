package evenements;

public abstract class Evenement {
	
	/**
	 * Date de l'événement
	 * 
	 * @author Equipe 23
	 */
	private long date;
	
	/**
	 * Constructeur
	 * @param date date de l'événement
	 */
	public Evenement(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return date;
	}


	/**
	 * Execute l'événement en question.
	 */
	public abstract void execute();
}
