package robots;

import java.util.Stack;

import carte.Carte;
import carte.Case;
import carte.Direction;
import chemins.Chemin;
import chemins.Djikstra;
import evenements.Evenement;
import evenements.EvenementDeplacer;
import evenements.EvenementLibererRobot;
import evenements.Simulateur;
import exceptions.exceptions_chemins.AucunCheminPossible;
import gui.Plateau;

/**
 * @author Equipe 23
 * Gère les déplacement d'un robot.
 *
 */
public class GestionnaireDeplacementRobot {

	/**
	 * 
	 */
	private Robot mrobot;

	/**
	 * 
	 */
	private Djikstra algoPlusCourtChemin;

	/**
	 * 
	 */
	private Carte mcarte;

	/**
	 * 
	 */
	private Simulateur simulateur;

	/**
	 * 
	 */
	private double pasSimulation;

	/**
	 * Gère les évenement de déplacements d'un robot.
	 * L'algorithme de Djiksra donne une pile de direction à suivre pour arriver jusqu'à l'incendie ou point d'eau
	 * En fonction de la vitesse moyenne du robot et du pas on obtient la fréquence de changemment de case pour le robot
	 * 
	 * @param robot
	 * @param plateau
	 * @param carte
	 */
	public GestionnaireDeplacementRobot(Robot robot, Simulateur simulateur, double pasSimulation, Carte carte) {
		this.algoPlusCourtChemin = new Djikstra(carte, robot);
		this.mrobot = robot;
		this.mcarte = carte;
		this.simulateur = simulateur;
		this.pasSimulation = pasSimulation;
	}

	/**
	 * Indique si la case est accessible par le robot. Attention, cela calcule le
	 * plus court chemin et peut être couteux!
	 * 
	 * @param caseDestination
	 * @return
	 */
	public boolean caseEstAccessible(Case caseDestination) {
		try {
			// si aucune exception levée, alors un chemin existe
			this.algoPlusCourtChemin.plusCourtChemin(this.mrobot.getPosition(), caseDestination);
			return true;
		} catch (AucunCheminPossible event) {
			return false;
		}
	}

	/**
	 * @param caseDestination
	 * @throws AucunCheminPossible Défnit le nombre d'évenement "déplacer" à
	 *                             utiliser pour arriver sur la case destination.
	 */
	/**
	 * @param caseDestination
	 * @throws AucunCheminPossible
	 */
	/**
	 * @param caseDestination
	 * @throws AucunCheminPossible
	 */
	// TODO CHANGER TEMPS_INITIAL
	public long deplacerRobot(Case caseDestination, long tempsInitial) throws AucunCheminPossible {
		/* Initialisation du plus court chemin */
		Chemin plusCourtChemin = this.algoPlusCourtChemin.plusCourtChemin(this.mrobot.getPosition(), caseDestination);
		return this.deplacerRobot(plusCourtChemin, tempsInitial);

	}

	public long deplacerRobotVersPointDEau(long tempsInitial) throws AucunCheminPossible{
		
		Chemin plusCourtChemin = this.algoPlusCourtChemin.plusCourtCheminVersPointEau(this.mrobot.getPosition());
		return this.deplacerRobot(plusCourtChemin, tempsInitial);
	}
	
	public long deplacerRobotVersPointDEau(Case caseDepartRobot, long tempsInitial) throws AucunCheminPossible{
		
		Chemin plusCourtChemin = this.algoPlusCourtChemin.plusCourtCheminVersPointEau(caseDepartRobot);
		return this.deplacerRobot(plusCourtChemin, tempsInitial);
	}

	
	/**
	 * @param chemin :contient la pile de directions et le temps de parcours
	 * @param tempsInitial: temps de début des évenements de déplacement.
	 * @return
	 */
	private long deplacerRobot(Chemin chemin, long tempsInitial) {
		Stack<Direction> pileDirections = chemin.getDirections();
		long tempsCourant = tempsInitial;
		double tempsParcoursChemin = chemin.getTempsParcours();
		double vitesseMoyenne = chemin.getVitesseMoyenne();

		int tailleCase = this.mcarte.getTailleCases();

		long tempsCourantInterieur;
		int nbrCasesAAvancer;
		int positionDansCase;
		int duree_evenement = 0;

		int distance_pas = (int) ((double) this.pasSimulation * vitesseMoyenne);

		 while (tempsCourant < (tempsInitial + tempsParcoursChemin)) {
			tempsCourantInterieur = tempsCourant;
			positionDansCase = mrobot.getPositionDansCase();

			positionDansCase += distance_pas;

			
			nbrCasesAAvancer = positionDansCase / tailleCase;

			positionDansCase = positionDansCase % tailleCase;
			mrobot.setPositionDansCase(positionDansCase, tailleCase);

			
			if (this.pasSimulation < nbrCasesAAvancer) {
				duree_evenement = 0;
			} else if (nbrCasesAAvancer != 0 & this.pasSimulation >= nbrCasesAAvancer) {
				duree_evenement = (int) (this.pasSimulation) / nbrCasesAAvancer;
			}
			/* définit le nombre de case à avancer pour pasDeTemps */
			for (int i = 0; i < nbrCasesAAvancer; i++) {

				
				if (pileDirections.empty()) {
					//System.out.println("PILE VIDE");
					break;
				}

				Direction direction = pileDirections.pop();
				Evenement e = new EvenementDeplacer(tempsCourantInterieur, mrobot, mcarte, direction);
				this.simulateur.ajouteEvenement(e);

				
				tempsCourantInterieur += duree_evenement;
			}

			tempsCourant += this.pasSimulation;
		}
		return tempsCourant;
	}

}
