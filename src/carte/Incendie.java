package carte;

import exceptions.exceptions_remplissage.PlusDeau;

public class Incendie {

	private int intensite;
	private Case position;
	
	public Incendie(int intensite, Case position) {
		try {
			this.setIntensite(intensite);
		} catch (PlusDeau e) {
		  System.out.println(e + "dans creation incendie");
		}
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
	
	public void setIntensite(int intensite) throws PlusDeau {
		if (intensite <= 0) {
			throw new PlusDeau(this + "est eteint");
		}
		else {
			this.intensite = intensite;
	}
}
	
	 @Override
	public String toString() {
		 return "Incendie en " + this.getPosition() + "d'intensité: " + this.intensite;
	    }
}
