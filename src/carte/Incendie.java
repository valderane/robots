package carte;

/**
 * Un Incendie a une intensité et une position. Il peut être soit libre, soit en
 * cours d'extinction par un robot
 * 
 * @author
 *
 */
public class Incendie {

	private int intensite;

	/**
	 * Intensité initiale de l'incendie. Utile pour dessiner en fonction du ratio
	 * intensite / intensiteInit.
	 */
	private int intensiteInit;
	private Case position;
	private boolean libre;

	/**
	 * Constructeur de l'incendie
	 * 
	 * @param intensite Intensite de l'incendie
	 * @param position  La position de l'incendie dans la carte
	 */
	public Incendie(int intensite, Case position) {

		this.setIntensite(intensite);
		this.setIntensiteInit(intensite);
		this.setPosition(position);
		this.setLibre(true);
	}

	public Case getPosition() {
		return this.position;
	}

	public int getIntensite() {
		return this.intensite;
	}

	public int getIntensiteInit() {
		return this.intensiteInit;
	}

	public void setPosition(Case position) {
		this.position = position;
	}

	public void setIntensite(int intensite) {
		this.intensite = intensite;
	}

	public void setIntensiteInit(int intensite) {
		this.intensiteInit = intensite;
	}

	@Override
	public String toString() {
		return "Incendie en " + this.getPosition() + "d'intensité: " + this.intensite;
	}

	/**
	 * Indique si l'incendie est libre ou non.
	 * 
	 * @return true si l'incendie est libre, false si un robot s'en occupe
	 */
	public boolean isLibre() {
		return this.libre;
	}

	/**
	 * Libère l'incendie ou le marque comme occupé
	 * 
	 * @param libre
	 */
	public void setLibre(boolean libre) {
		this.libre = libre;
	}
}
