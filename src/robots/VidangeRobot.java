package robots;

import carte.Incendie;
import evenements.EvenementDeverser;
import evenements.Simulateur;
import io.DonneesSimulation;

/**
 * @author emmanuel
 *Programe la suite d'évenement jusqu'à éteindre un incendie ou jusqu'a ce que le robot vide son réservoir
 */
public class VidangeRobot {
	
	 private Robot mrobot;
	 private DonneesSimulation donnesSimu;
	 private Simulateur simulateur;
	 private double pasSimu;
	 
	/**
	 * @param robot
	 * @param donnesSimu
	 * @param simulateur
	 * @param pasSimulation
	 */
	public VidangeRobot(Robot robot, DonneesSimulation donnesSimu, Simulateur simulateur, double pasSimulation) {
		this.mrobot = robot;
		this.donnesSimu = donnesSimu;
		this.pasSimu = pasSimulation;
	}
	
	/**
	 * @param tempsChef en secondes
	 */
	public void DeverserEau(double tempsChef) {
		double tempsCourantInterieur = tempsChef;
		int flag = 0;
		long prochaine_date = 0;
		while (flag ==0) {
			prochaine_date = (long) Math.floor(tempsChef + this.pasSimu);
			//Doit Calculer la duree d'execution maintenant. et l'envoyer a l'evt.
			//car si on s'arrete a 2.8, on doit le savoir maintenant pour tempsChefRbot
			//ou alors c'est l'evt qui calcule la duree d'exec et qui renvoie ici.
			//comme ca on pourra incrementer date chef
			//TODO
			//new EvenementDeverser(dateEvenement, robot, donneSimu, prochaineDate)
			
		}
		
		
		
	}

}
