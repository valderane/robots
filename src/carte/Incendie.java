package carte;

public class Incendie {

	private int intensite;
	private Case position;
	
	public Incendie(int intensite, Case position) {
		this.intensite = intensite;
		this.position = position;
		//TODO verifications		
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public int getIntensite() {
		return this.intensite;
	}
}
