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

//SI CA MARCHE PAS ON PASSE EN DISCRET.
/**
 * @author jicquelv
 *
 */
public class DeplacementRobot {

	/*
	 * Plateau: pas + taille_case + temps_courant Djiktra: Pile+temps+vitesse
	 * moyenne Case visé
	 */

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

	/* Vérif copie + cast */
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

	/**
	 * Indique si la case est accessible par le robot. Attention, cela calcule le plus court chemin et peut être couteux!
	 * @param caseDestination
	 * @return
	 */
	public boolean caseEstAccessible(Case caseDestination) {
		try {
			//si aucune exception levée, alors un chemin existe
			this.algoPlusCourtChemin.plusCourtChemin(caseDestination);
			System.out.println("case est accessible");
			return true;
		}catch(AucunCheminPossible event) {
			System.out.println("case pas accessible");
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
	//TODO CHANGER TEMPS_INITIAL
	public long deplacer_robot(Case caseDestination) throws AucunCheminPossible {

		/* Initialisation du plus court chemin */
		Chemin plusCourtChemin = this.algoPlusCourtChemin.plusCourtChemin(caseDestination);
		Stack<Direction> pileDirections = plusCourtChemin.getDirections();
		long tempsCourant = this.simulateur.getDateSimulation();
		long tempsInitial = tempsCourant;
		double tempsParcoursChemin = plusCourtChemin.getTempsParcours();
		double vitesseMoyenne = plusCourtChemin.getVitesseMoyenne();
		// System.out.println("vitesse moyene: " + vitesseMoyenne);

		int tailleCase = this.mcarte.getTailleCases();

		long tempsCourantInterieur;
		int nbrCasesAAvancer;
		int positionDansCase;
		int nombreCaseAvanceTotal = 0;
		int duree_evenement = 0;

		/* Distance parcourue en 1 pas de temps */
		int distance_pas = (int) ((double) this.pasSimulation * vitesseMoyenne);

		while (tempsCourant < (tempsInitial + tempsParcoursChemin)) {
			tempsCourantInterieur = tempsCourant;
			positionDansCase = mrobot.getPositionDansCase();

			positionDansCase += distance_pas;

			/*
			 * Calcul le nombre de case de déplacement selon le pas (pour un next) vérif 1/2
			 * = 0, div entiere
			 */
			nbrCasesAAvancer = positionDansCase / tailleCase;

			/* nouvelle position dans case (finale) */
			/* TODO: expliquer */
			positionDansCase = positionDansCase % tailleCase;
			mrobot.setPositionDansCase(positionDansCase, tailleCase);

			/* Avance du bon nombre de case */
			nombreCaseAvanceTotal += nbrCasesAAvancer;
			/*
			 * si le pas = et que le nb de case a avancer est de 4 Comme temps discret -> on
			 * peut pas couper pour la date des evt. donc on choisit de mettre tout au mm
			 * moment dans un next
			 */
			if (this.pasSimulation < nbrCasesAAvancer) {
				/* il faudra tout mettre au même moment */
				duree_evenement = 0;
			} else if (nbrCasesAAvancer != 0 & this.pasSimulation >= nbrCasesAAvancer) {
				duree_evenement = (int) (this.pasSimulation) / nbrCasesAAvancer;
			}
			/* définit le nombre de case à avancer pour pasDeTemps */
			for (int i = 0; i < nbrCasesAAvancer; i++) {

				/*
				 * exemple: pas = 2 sec et tmps de parcours = 7 sec. on devrait avancer, mais
				 * non
				 */
				if (pileDirections.empty()) {
					System.out.println("PILE VIDE");
					break;
				}

				Direction direction = pileDirections.pop();
				Evenement e = new EvenementDeplacer(tempsCourantInterieur, mrobot, mcarte, direction);
				this.simulateur.ajouteEvenement(e);

				/* on place l'evt au bon endroit */
				/* Il faudrait calculer: distance_pas/nbr_case */
				/* A voir */
				tempsCourantInterieur += duree_evenement;
			}

			tempsCourant += this.pasSimulation;
		}
		/*Normalement faut retourner tempsCourantinterieur?*/
		this.simulateur.ajouteEvenement(new EvenementLibererRobot(tempsCourant, this.mrobot));
		return tempsCourant;
	}

}
