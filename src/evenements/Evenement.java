package evenements;

public abstract class Evenement {
	protected long date;
	
	public Evenement(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return date;
	}

	//parametre: pochaine date (date courante + pas) du simulateur.
	//besoin pour calculer qtté remplissage + vitesse deplacement.
	//execute les actions d'un évenement
	public abstract int execute(long pochaineDate);
}
