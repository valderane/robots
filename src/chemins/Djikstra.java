package chemins;

import java.util.Stack;

import carte.Carte;
import carte.Case;
import carte.Direction;
import robots.Robot;

/**
 * @author 
 *
 */
public class Djikstra extends AlgoPlusCourtChemin{
	
	/**
	 * Constante utilisée pour marquer les liens non parcourus
	 */
	private final int INFINI = -1;
	
	/**
	 * Distances minimale pour atteindre chaque case depuis la case de départ 
	 */
	protected double[][] distances;
	
	/**
	 * Case précédente utilisée pour atteindre chaque case avec le plus court chemin
	 */
	protected Case[][] precedents;
	
	/**
	 * @param carte Carte sur laquelle l'algorithme va être appliqué
	 * @param robot Robot sujet au déplacement
	 */
	public Djikstra(Carte carte, Robot robot) {
		super(carte, robot);
		this.distances = new double[this.carte.getNbLignes()][this.carte.getNbColonnes()];
		this.precedents = new Case[this.carte.getNbLignes()][this.carte.getNbColonnes()];		
	}
	
	/* (non-Javadoc)
	 * @see chemins.AlgoPlusCourtChemin#plusCourtChemin(carte.Case)
	 */
	@Override
	public Stack<Direction> plusCourtChemin(Case c) {
		
		return null;
	}
	
}
