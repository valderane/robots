package chemins;

import carte.Case;
import carte.Direction;

/**
 * @author 
 *
 */
public class Lien {
	
	/**
	 * noeud d'arrivée du lien
	 */
	private Case caseDestination;
	
	/**
	 * noeud de départ du lien
	 */
	private Case caseDepart;

	/**
	 * Indique quelle direction a été prise pour atteindre la case destination
	 */
	private Direction direction;
	
	/**
	 * Poids du lien
	 */
	private double poids;
	
	/**
	 * @param caseDestination
	 * @param direction
	 * @param poids
	 */
	public Lien(Case caseDepart, Case caseDestination, Direction direction, double poids) {
		this.caseDepart = caseDepart;
		this.caseDestination = caseDestination;
		this.direction = direction;
		this.poids = poids;
	}
	
	/**
	 * @return
	 */
	public Case getCaseDepart() {
		return this.caseDepart;
	}
	
	/**
	 * @return
	 */
	public Case getCaseDestination() {
		return this.caseDestination;
	}
	
	/**
	 * @return
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	public double getPoids() {
		return this.poids;
	}
	
	@Override
	public String toString() {
		return "Lien - Case départ : "+this.caseDepart+", Case destination : "+this.caseDestination+", temps parcours : "+this.poids;
	}
}
