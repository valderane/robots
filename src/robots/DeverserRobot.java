package robots;

import carte.Incendie;
import evenements.Evenement;
import evenements.EvenementDeverser;
import evenements.EvenementLibererIncendie;
import evenements.EvenementLibererRobot;
import evenements.Simulateur;
import io.DonneesSimulation;

/**
 * @author emmanuel
 *Programe la suite d'évenement jusqu'à éteindre un incendie ou jusqu'a ce que le robot vide son réservoir
 *Le robot est déjà au bon endroit.
 *Les temps sont théorique sont calculé sur une base de tous les pas depuis la dateCouranteChef
 */
public class DeverserRobot {
	
	 private Simulateur simulateur;
	 private long pasSimu;
	 private Robot mrobot;
		 
	/**
	 * @param robot
	 * @param donnesSimu
	 * @param simulateur
	 * @param pasSimulation
	 */
	public DeverserRobot(Robot robot, Simulateur simulateur, long pasSimulation) {
		
		this.pasSimu = pasSimulation;
		this.simulateur = simulateur;
		this.mrobot = robot;
	}
	
	/**
	 * @param incendieEnCours
	 * @param tempsCourantChef en sec. (Ex: Si on deplace de t0 à 48 sec on doit savoir ou on en est.
	 * Toute les fonction de gestion d'evenement devrait renvoyer le temps courant?
	 */
	//marge de manoeuvre temps
	public void DeverserEau(Incendie incendieEnCours, long tempsCourantChef, DonneesSimulation data ) {
		
		/*c'est le temps courant de la simulation + le temps des actions que le chef robot à prévus avant (pour ce temps de simu)*/
		double tempsCourant = tempsCourantChef ;
		long prochaineDate = 0;		
		long dureeEvenement = 0;
		int VolumeAVider = 0;
		int intensite = incendieEnCours.getIntensite();
		int reservoirEau = this.mrobot.getReservoirEau();

		while (true) {
			
		/*fin évenement théorique*/
		prochaineDate = (long) (tempsCourantChef + this.pasSimu);
		
		
		
		/*duree evenement théorique = laps de temps pour vider*/
		 dureeEvenement = this.pasSimu;
		/*Volume à vider théorique.*/
		 VolumeAVider =  (int)(this.mrobot.getCapaciteViderLitre() * dureeEvenement) / this.mrobot.getCapaciteViderSec();
		
		if (VolumeAVider > intensite) {
			/*L'evenement aura une durée plus courte que prévu car on va moins vider d'eau -> besoin de moins de temps pour vider incendie*/
			dureeEvenement = incendieEnCours.getIntensite() * this.mrobot.getCapaciteViderSec() / this.mrobot.getCapaciteViderLitre();
			VolumeAVider = intensite;
		
		if (VolumeAVider > reservoirEau) {
			/*L'evenement aura une durée plus courte car on va moins vider d'eau -> robot à cours.*/
			dureeEvenement = this.mrobot.getReservoirEau() * this.mrobot.getCapaciteViderSec() / this.mrobot.getCapaciteViderLitre();
			VolumeAVider = reservoirEau;
		}
		
		//VolumeAVider et dureeEvenement sont correct
		intensite -= VolumeAVider;
		reservoirEau -= VolumeAVider;		
		/*Vide eau de robot sur incendie*/
		Evenement e = new EvenementDeverser(tempsCourantChef, this.mrobot, incendieEnCours, reservoirEau, intensite);
		this.simulateur.ajouteEvenement(e);
		//dans tous les cas
		tempsCourantChef += dureeEvenement;

		//condition d'arret
		if (intensite == 0 & reservoirEau == 0 ) {
			//supprimer_incendie 
			data.supprimerIncendie(this.mrobot.getPosition(), incendieEnCours);
			//laisser robot_occupe
			break;
		}
		else if (intensite == 0) {
			//supprimer_incendie 
			data.supprimerIncendie(this.mrobot.getPosition(), incendieEnCours);
			//désocuper robot
			Evenement f = new EvenementLibererRobot(tempsCourantChef, this.mrobot);
			this.simulateur.ajouteEvenement(f);
			break;
		}
		else if (reservoirEau == 0) {
			//laisser robot occuper
			
			//liberer_incendie
			Evenement g = new EvenementLibererIncendie(tempsCourantChef, incendieEnCours);
			break;
		}
		//sinon on continue la boucle
	}

		
		
		
		
		//Doit Calculer la duree d'execution maintenant. et l'envoyer a l'evt.
			//car si on s'arrete a 2.8, on doit le savoir maintenant pour tempsChefRbot
			//ou alors c'est l'evt qui calcule la duree d'exec et qui renvoie ici.
			//comme ca on pourra incrementer date chef
			//TODO
			//new EvenementDeverser(dateEvenement, robot, donneSimu, prochaineDate)
			
		}
		
		
		
	}

}
