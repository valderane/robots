package robots;


import java.util.Stack;

import carte.Carte;
import carte.Case;
import carte.Direction;
import chemins.Djikstra;
import evenements.Evenement;
import evenements.EvenementDeplacer;
import exceptions.exceptionsChemins.AucunCheminPossible;
import gui.Plateau;

//SI CA MARCHE PAS ON PASSE EN DISCRET.
public class DeplacementRobot {
	

	/*Plateau: pas + taille_case + temps_courant
	 * Djiktra: Pile+temps+vitesse moyenne
	 * Case visé*/
	private Robot mrobot;
	
	private long temps_courant;
	private int taille_case;
	private int pas_simu;	
	private Carte mcarte;
	private Djikstra djikstra;
	private double temps_parcours;
	private double vitesse_moyenne;
	private Case mdestination;
	private Stack<Direction> pile_directions;
	private Plateau mplateau;
	
	/*Vérif copie + cast */
	public DeplacementRobot(Robot robot, Plateau plateau, Carte carte ,Case destination) throws AucunCheminPossible {

		djikstra = new Djikstra(carte,robot);
		temps_parcours = djikstra.plusCourtChemin(destination).getTempsParcours();
		
		System.out.println("tps parcours: " + temps_parcours);
		/*en m/s*/
		vitesse_moyenne = djikstra.plusCourtChemin(destination).getVitesseMoyenne();
		System.out.println("Vitesse moyenne : "+vitesse_moyenne);
		System.out.println(carte.getTailleCases());
		
		mrobot = robot;
		mcarte = carte;
		temps_courant = plateau.getSimulateur().getDateSimulation();
		taille_case = carte.getTailleCases();
		pas_simu = plateau.getPas_simu_sec();
		mplateau = plateau;
		mdestination = destination;
		pile_directions = djikstra.plusCourtChemin(destination).getDirection();
		deplacer_robot();
	}
	
	
	
	public void deplacer_robot() {
		long temps_courant_interieur;
		int nbr_case_a_avancer;
		int position_dans_case;
		int nombre_case_avance_total = 0;
		/*distance parcouru en 1 pas de temps*/
		int distance_pas= (int) ((double) this.pas_simu * this.vitesse_moyenne);

		System.out.println("temps courant : "+temps_courant+" temps parcours : "+temps_parcours);
		while (temps_courant < temps_parcours) {
			/*Remet compteur */
			temps_courant_interieur = this.temps_courant;
			position_dans_case = mrobot.getPositionDansCase();
			
			//System.out.println("position_dans_case = " + position_dans_case + " et taille_case = " + taille_case);
		//	System.out.println("V moy = "+ vitesse_moyenne);
			position_dans_case += distance_pas;
			
			/*Calcul le nombre de case de déplacement selon le pas (pour un next)
			 * vérif 1/2 = 0, div entiere*/
			nbr_case_a_avancer = position_dans_case / taille_case;
			System.out.println("position_dans_case = " + position_dans_case + " et taille_case = " + taille_case);
			
			/*nouvelle position dans case*/
			position_dans_case = position_dans_case % taille_case;
			mrobot.setPositionDansCase(position_dans_case, taille_case);
			
			/*Avance du bon nombre de case*/
			nombre_case_avance_total += nbr_case_a_avancer;
			for (int i=0; i<nbr_case_a_avancer; i++) {
				
				System.out.println("on avance de: "+ nbr_case_a_avancer);
				
				if (pas_simu < nbr_case_a_avancer) {
					System.out.println(nbr_case_a_avancer);
					/*il faudra tout mettre au meme moment*/
					throw new IllegalArgumentException("PAS_SIMU < NBR_CASE");
				}
				
				/*pas = 2 sec et tmps de parcours = 7 sec*/
				if (this.pile_directions.empty()) {
					System.out.println("PILE VIDE");
					break;
				}
				Direction direction = this.pile_directions.pop();
				Evenement e = new EvenementDeplacer(temps_courant_interieur, mrobot, mcarte, direction);
				this.mplateau.getSimulateur().ajouteEvenement(e);
				System.out.println("ajout événement");
				/*on place l'evt au bon endroit*/
				/* Il faudrait calculer: distance_pas/nbr_case*/
				/*A voir*/
				temps_courant_interieur += 1; 
			}
			temps_courant += pas_simu;
		}
	System.out.println("avance totale:" + nombre_case_avance_total);
	}

}
