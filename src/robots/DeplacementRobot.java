package robots;


import java.util.Stack;

import carte.Carte;
import carte.Case;
import carte.Direction;
import chemins.Djikstra;
import evenements.Evenement;
import evenements.Evenement_deplacer;
import evenements.Plateau2;

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
	private Plateau2 mplateau;
	
	/*Vérif copie + cast */
	public DeplacementRobot(Robot robot, Plateau2 plateau,Carte carte ,Case destination) {

		djikstra = new Djikstra(carte,robot);
		temps_parcours = djikstra.plusCourtChemin(destination).getTempsParcours();
		/*en m/s*/
		vitesse_moyenne = (djikstra.plusCourtChemin(destination).getVitesseMoyenne() / 3.6);
		
		mrobot = robot;
		mcarte = carte;
		temps_courant = plateau.getSimulateur().getDateSimulation();
		taille_case = plateau.getTAILLE_CASE();
		pas_simu = plateau.getPas_simu_sec();
		mplateau = plateau;
		mdestination = destination;
		pile_directions = djikstra.plusCourtChemin(destination).getDirection();
		deplace_robot();
	}
	
	
	
	public void deplace_robot() {
		long temps_courant_interieur;
		int nbr_case_a_avancer;
		int position_dans_case;
		
		/*distance parcouru en 1 pas de temps*/
		int distance_pas= (int) ((double) this.pas_simu * this.vitesse_moyenne);

		
		while (temps_courant < temps_parcours) {
			/*Remet compteur */
			temps_courant_interieur = this.temps_courant;
			position_dans_case = mrobot.getPosition_dans_case();
			//System.out.println("position_dans_case = " + position_dans_case + " et taille_case = " + taille_case);
		//	System.out.println("V moy = "+ vitesse_moyenne);
			position_dans_case += distance_pas;
			/*Calcul le nombre de case de déplacement selon le pas (pour un next)
			 * vérif 1/2 = 0, div entiere*/
			nbr_case_a_avancer = position_dans_case / taille_case;
			System.out.println("position_dans_case = " + position_dans_case + " et taille_case = " + taille_case);
			/*nouvelle position dans case*/
			position_dans_case = position_dans_case % taille_case;
			mrobot.setPosition_dans_case(position_dans_case, taille_case);
			
			/*Avance du bon nombre de case*/
			for (int i=0; i<nbr_case_a_avancer; i++) {
				System.out.println("on avance de: "+ nbr_case_a_avancer);
				if (pas_simu < nbr_case_a_avancer) {
					System.out.println(nbr_case_a_avancer);
					/*il faudra tout mettre au meme moment*/
					throw new IllegalArgumentException("PAS_SIMU < NBR_CASE");
				}
				
				/*pas = 2 sec et tmps de parcours = 7 sec*/
				if (this.pile_directions.empty()) {
					break;
				}
				Direction direction = this.pile_directions.pop();
				Evenement e = new Evenement_deplacer(temps_courant_interieur, mrobot, mcarte, direction);
				this.mplateau.getSimulateur().ajouteEvenement(e);
				/*on place l'evt au bon endroit*/
				/* Il faudrait calculer: distance_pas/nbr_case*/
				/*A voir*/
				temps_courant_interieur += 1; 
			}
			temps_courant += pas_simu;
		}
	}

}
