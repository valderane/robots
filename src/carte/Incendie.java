package carte;

public class Incendie {

	private int intensite;
	private Case position;
	
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
	
	public void setIntensite(int intensite) {
		this.intensite = intensite;
	}
}
