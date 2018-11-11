package chemins;

import carte.Case;
import carte.Direction;

/**
 * Représente un lien pondéré du point de vue des graphe. Possède une case de destination, de départ,
 * la direction pour atteindre la case de destination et le poids du lien.
 * 
 * @author Equipe 23
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
	 * Constructeur de la classe
	 * 
	 * @param caseDepart case de départ du lien
	 * @param caseDestination case de destination du lien
	 * @param direction direction pour atteindre la case de destination depuis la case de départ
	 * @param poids le poids du lien
	 */
	public Lien(Case caseDepart, Case caseDestination, Direction direction, double poids) {
		this.caseDepart = caseDepart;
		this.caseDestination = caseDestination;
		this.direction = direction;
		this.poids = poids;
	}
	

	public Case getCaseDepart() {
		return this.caseDepart;
	}

	public Case getCaseDestination() {
		return this.caseDestination;
	}
	
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
