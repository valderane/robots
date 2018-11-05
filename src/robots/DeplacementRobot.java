package robots;


import java.util.Stack;

import carte.Carte;
import carte.Case;
import carte.Direction;
import chemins.Chemin;
import chemins.Djikstra;
import evenements.Evenement;
import evenements.EvenementDeplacer;
import evenements.Simulateur;
import exceptions.exceptions_chemins.AucunCheminPossible;
import gui.Plateau;

//SI CA MARCHE PAS ON PASSE EN DISCRET.
/**
 * @author jicquelv
 *
 */
public class DeplacementRobot {
	

	/*Plateau: pas + taille_case + temps_courant
	 * Djiktra: Pile+temps+vitesse moyenne
	 * Case visé*/
	
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
	
	private double pasSimulation;
	
	/*Vérif copie + cast */
	/**
	 * @param robot
	 * @param plateau
	 * @param carte
	 */
	public DeplacementRobot(Robot robot, Simulateur simulateur, double pasSimulation, Carte carte) {
		this.algoPlusCourtChemin = new Djikstra(carte, robot);
		this.mrobot = robot;
		this.mcarte = carte;
		this.simulateur = simulateur;
		this.pasSimulation = pasSimulation;
	}
	
	
	
	public void deplacer_robot(Case caseDestination) throws AucunCheminPossible {
		
		/* Initialisation du plus court chemin */
		Chemin plusCourtChemin = this.algoPlusCourtChemin.plusCourtChemin(caseDestination);
		Stack<Direction> pileDirections = plusCourtChemin.getDirections();
		long tempsCourant = this.simulateur.getDateSimulation();
		double tempsParcoursChemin = plusCourtChemin.getTempsParcours();
		double vitesseMoyenne = plusCourtChemin.getVitesseMoyenne();

		int tailleCase = this.mcarte.getTailleCases();
		
		long tempsCourantInterieur;
		int nbrCasesAAvancer;
		int positionDansCase;
		int nombreCaseAvanceTotal = 0;
		
		/* Distance parcourue en 1 pas de temps*/
		int distance_pas= (int) ((double) this.pasSimulation * vitesseMoyenne);

		
		/** Vincent -- problème : il faudrait partir du tempsCourant et aller jusqu'à tempsCourant + tempsParcoursChemin **/
		while (tempsCourant < tempsParcoursChemin) {
			/*Remet compteur */
			tempsCourantInterieur = tempsCourant;
			positionDansCase = mrobot.getPositionDansCase();

			positionDansCase += distance_pas;
			
			/*Calcul le nombre de case de déplacement selon le pas (pour un next)
			 * vérif 1/2 = 0, div entiere*/
			nbrCasesAAvancer = positionDansCase / tailleCase;
			
			/*nouvelle position dans case*/
			positionDansCase = positionDansCase % tailleCase;
			mrobot.setPositionDansCase(positionDansCase, tailleCase);
			
			/*Avance du bon nombre de case*/
			nombreCaseAvanceTotal += nbrCasesAAvancer;
			for (int i = 0 ; i < nbrCasesAAvancer ; i++) {
				
				//System.out.println("on avance de: "+ nbr_case_a_avancer);
				
				if (this.pasSimulation < nbrCasesAAvancer) {
					System.out.println(nbrCasesAAvancer);
					/*il faudra tout mettre au meme moment*/
					throw new IllegalArgumentException("PAS_SIMU < NBR_CASE");
				}
				
				/*pas = 2 sec et tmps de parcours = 7 sec*/
				if (pileDirections.empty()) {
					System.out.println("PILE VIDE");
					break;
				}
				Direction direction = pileDirections.pop();
				Evenement e = new EvenementDeplacer(tempsCourantInterieur, mrobot, mcarte, direction);
				this.simulateur.ajouteEvenement(e);

				/*on place l'evt au bon endroit*/
				/* Il faudrait calculer: distance_pas/nbr_case*/
				/*A voir*/
				tempsCourantInterieur += 1; 
			}
			tempsCourant += this.pasSimulation;
		}
	}

}
