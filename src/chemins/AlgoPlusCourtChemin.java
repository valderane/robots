package chemins;

import carte.Carte;
import carte.Case;
import exceptions.exceptions_chemins.AucunCheminPossible;
import robots.Robot;

/**
 * Classe abstraite d'un algorithme de plus court chemin
 * 
 * @author Equipe 23
 *
 */
public abstract class AlgoPlusCourtChemin {

	/**
	 * Carte sur laquelle l'algorithme va être appliqué
	 */
	private Carte carte;

	/**
	 * Robot qui se déplace
	 */
	private Robot robot;

	/**
	 * @param carte Carte sur laquelle l'algorithme va être appliqué
	 * @param robot Robot sujet au déplacement
	 */
	public AlgoPlusCourtChemin(Carte carte, Robot robot) {
		this.carte = carte;
		this.robot = robot;
	}

	public Carte getCarte() {
		return carte;
	}

	public Robot getRobot() {
		return robot;
	}

	/**
	 * Renvoie le plus court chemin pour aller d'une case à une autre. Lève une
	 * exception si aucun chemin n'existe.
	 * 
	 * @param caseDeDepart    case de Départ
	 * @param caseDestination case Destination
	 * @return Le plus court chemin
	 * @throws AucunCheminPossible
	 */
	public abstract Chemin plusCourtChemin(Case caseDeDepart, Case caseDestination) throws AucunCheminPossible;

	/**
	 * Renvoie le plus court chemin vers le point d'eau le plus proche en partant
	 * d'une case. Lève une exception si aucun chemin n'existe.
	 * 
	 * @param caseDeDepart Case de départ
	 * @return Le plus court chemin vers un point d'eau
	 * @throws AucunCheminPossible
	 */
	public abstract Chemin plusCourtCheminVersPointEau(Case caseDeDepart) throws AucunCheminPossible;

}
