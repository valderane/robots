package chemins;

import java.util.Stack;

import carte.Carte;
import carte.Case;
import carte.Direction;
import exceptions.exceptions_chemins.AucunCheminPossible;
import robots.Robot;

/**
 * @author 
 *
 */
public abstract class AlgoPlusCourtChemin {
	
	/**
	 * Carte sur laquelle l'algorithme va être appliqué
	 */
	protected Carte carte;
	
	/**
	 * Robot qui se déplace
	 */
	protected Robot robot;
	
	/**
	 * @param carte Carte sur laquelle l'algorithme va être appliqué
	 * @param robot Robot sujet au déplacement
	 */
	public AlgoPlusCourtChemin(Carte carte, Robot robot) {
		this.carte = carte;
		this.robot = robot;
	}
	
	/**
	 * @param c Case destination
	 * @return Le plus court chemin trouvé
	 */
	public abstract Chemin plusCourtChemin(Case caseDeDepart, Case caseDestination) throws AucunCheminPossible;
		
	/**
	 * Renvoie le plus court chemin vers le point d'eau le plus proche
	 * @return Le plus court chemin vers un point d'eau
	 * @throws AucunCheminPossible
	 */
	public abstract Chemin plusCourtCheminVersPointEau(Case caseDeDepart) throws AucunCheminPossible;
	 
	
}
