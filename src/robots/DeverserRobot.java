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
 * @author emmanuel Programe la suite d'évenement jusqu'à éteindre un incendie
 *         ou jusqu'a ce que le robot vide son réservoir Le robot est déjà au
 *         bon endroit. Les temps sont théorique sont calculé sur une base de
 *         tous les pas depuis la dateCouranteChef
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
	 * @param tempsCourantChef en sec. (Ex: Si on deplace de t0 à 48 sec on doit
	 *                         savoir ou on en est. Toute les fonction de gestion
	 *                         d'evenement devrait renvoyer le temps courant?
	 */
	public long deverserEau(Incendie incendie, long tempsInitial, DonneesSimulation data) {
		long tempsCourant = tempsInitial;
		int volumeEauDeversee;
		int reservoirEau = this.mrobot.getReservoirEau();
		int intensiteIncendie = incendie.getIntensite();

		while (reservoirEau > 0 && intensiteIncendie > 0) {
			if (reservoirEau < this.mrobot.getVolumeDeverseParExtinction())
				volumeEauDeversee = reservoirEau;
			else
				volumeEauDeversee = this.mrobot.getVolumeDeverseParExtinction();
			

			intensiteIncendie -= volumeEauDeversee;
			reservoirEau -= volumeEauDeversee;

			EvenementDeverser eventDeverser = new EvenementDeverser(tempsCourant, this.mrobot, incendie,
					volumeEauDeversee);
			this.simulateur.ajouteEvenement(eventDeverser);

			/* Quelque soit le volume d'eau vidé, l'extinction dure CapaciteVideSec */
			tempsCourant += this.mrobot.getTempsVidage();
		}
		// Cas où le robot a réussi à éteindre l'incendie, mais n'a plus d'eau. Il est
		// donc toujours dans l'état "occupé"
		if (intensiteIncendie <= 0 && reservoirEau <= 0) {
			// supprimer_incendie
			Evenement suppressionIncendie = new EvenementSupprimerIncendie(tempsCourant, incendie, data);
			this.simulateur.ajouteEvenement(suppressionIncendie);	
			
		} else if (intensiteIncendie <= 0) {
			// supprimer_incendie
			Evenement suppressionIncendie = new EvenementSupprimerIncendie(tempsCourant, incendie, data);
			this.simulateur.ajouteEvenement(suppressionIncendie);

			// désoccuper robot
			Evenement f = new EvenementLibererRobot(tempsCourant, this.mrobot);
			this.simulateur.ajouteEvenement(f);
			
		} else if (reservoirEau <= 0) {
			
			// laisser robot occupé et libérer incendie
			Evenement g = new EvenementLibererIncendie(tempsCourant, incendie);
			this.simulateur.ajouteEvenement(g);

		}
		
		return tempsCourant;
	}

}
