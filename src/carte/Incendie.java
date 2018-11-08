package carte;

import exceptions.exceptions_remplissage.PlusDeau;

public class Incendie {

	private int intensite;
	private Case position;
    private boolean libre;

	
	public Incendie(int intensite, Case position) {

			this.setIntensite(intensite);
			this.setPosition(position);
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public int getIntensite() {
		return this.intensite;
	}
	
	public void setPosition(Case position) {
		this.position = position;
	}
	
	public void setIntensite(int intensite){
		this.intensite = intensite;
	}

	
	 @Override
	public String toString() {
		 return "Incendie en " + this.getPosition() + "d'intensité: " + this.intensite;
	    }

	/**
	 * indique si l'incendie est libre ou non.
	 * @return
	 */
	public boolean isLibre() {
		return this.libre;
	}

	/**
	 * Libère l'incendie ou le marque comme occupé
	 * @param libre
	 */
	public void setLibre(boolean libre) {
		this.libre = libre;
	}
}
