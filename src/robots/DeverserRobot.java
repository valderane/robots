package robots;

import carte.Incendie;
import evenements.Evenement;
import evenements.EvenementDeverser;
import evenements.EvenementLibererIncendie;
import evenements.EvenementLibererRobot;
import evenements.EvenementSupprimerIncendie;
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
	public long DeverserEau(Incendie incendieEnCours, long tempsCourantChef, DonneesSimulation data ) {
		System.out.println("Deverser eau");
		
		/*c'est le temps courant de la simulation + le temps des actions que le chef robot à prévus avant (pour ce temps de simu)*/
		long tempsCourant = tempsCourantChef ;
		long prochaineDate = 0;		
		long dureeEvenement = 0;
		int VolumeAVider = 0;
		int intensite = incendieEnCours.getIntensite();
		int reservoirEau = this.mrobot.getReservoirEau();

		while (true) {
			
		/*fin évenement théorique*/
		prochaineDate = (long) (tempsCourant + this.pasSimu);
		
		
		/*duree evenement théorique = laps de temps pour vider*/
		 dureeEvenement = this.pasSimu;
		/*Volume à vider théorique.*/
		 VolumeAVider =  (int)(this.mrobot.getCapaciteViderLitre() * dureeEvenement) / this.mrobot.getCapaciteViderSec();
		
		if (VolumeAVider > intensite) {
			/*L'evenement aura une durée plus courte que prévu car on va moins vider d'eau -> besoin de moins de temps pour vider incendie*/
			dureeEvenement = incendieEnCours.getIntensite() * this.mrobot.getCapaciteViderSec() / this.mrobot.getCapaciteViderLitre();
			VolumeAVider = intensite;
		}
		
		if (VolumeAVider > reservoirEau) {
			/*L'evenement aura une durée plus courte car on va moins vider d'eau -> robot à cours.*/
			dureeEvenement = this.mrobot.getReservoirEau() * this.mrobot.getCapaciteViderSec() / this.mrobot.getCapaciteViderLitre();
			VolumeAVider = reservoirEau;
		}
		
		//VolumeAVider et dureeEvenement sont correct
		intensite -= VolumeAVider;
		reservoirEau -= VolumeAVider;		
		/*Vide eau de robot sur incendie*/
		Evenement e = new EvenementDeverser(tempsCourant, this.mrobot, incendieEnCours, reservoirEau, intensite);
		this.simulateur.ajouteEvenement(e);
		//dans tous les cas
		tempsCourant += dureeEvenement;

		//condition d'arret
		if (intensite == 0 && reservoirEau == 0 ) {
			//supprimer_incendie 
			Evenement suppressionIncendie = new EvenementSupprimerIncendie(tempsCourant, incendieEnCours, data);
			this.simulateur.ajouteEvenement(suppressionIncendie);

			//laisser robot_occupe
			break;
		}
		else if (intensite == 0) {
			//supprimer_incendie 
			Evenement suppressionIncendie = new EvenementSupprimerIncendie(tempsCourant, incendieEnCours, data);
			this.simulateur.ajouteEvenement(suppressionIncendie);

			//désocuper robot
			Evenement f = new EvenementLibererRobot(tempsCourant, this.mrobot);
			this.simulateur.ajouteEvenement(f);
			break;
		}
		else if (reservoirEau == 0) {
			//laisser robot occupé
			
			//liberer_incendie
			Evenement g = new EvenementLibererIncendie(tempsCourant, incendieEnCours);
			break;
		}
		//sinon on continue la boucle
	}
	return tempsCourant;
			
  }
		
}
