package robots;

import carte.Incendie;
import evenements.Evenement;
import evenements.EvenementDeverser;
import evenements.EvenementLibererIncendie;
import evenements.EvenementLibererRobot;
import evenements.EvenementRemplir;
import evenements.EvenementSupprimerIncendie;
import evenements.Simulateur;
import io.DonneesSimulation;

/**
 * Gère le vidage / remplissage pour un robot donné.
 */
public class GestionnaireReservoirRobot {

	private Simulateur simulateur;
	private Robot mrobot;
	private long pasSimulation;

	/**
	 * @param robot
	 * @param donnesSimu
	 * @param simulateur
	 * @param pasSimulation
	 */
	public GestionnaireReservoirRobot(Robot robot, Simulateur simulateur, long pasSimulation) {

		this.simulateur = simulateur;
		this.mrobot = robot;
		this.pasSimulation = pasSimulation;
	}

	/**
	 * Pour un robot et un incendie donné:  Programme la suite d'évenements jusqu'à éteindre un incendie
	 * ou jusqu'a ce que le robot vide son réservoir Le robot est déjà au
	 *bon endroit.
	 *Quand le robot à finit d'éteindre un incendie, si il lui reste de l'eau on le replace dans un état "libre".
	 * Sinon, on le laisse occupé, et on libère l"incendie.
	 * @param incendieEnCours
	 * @param tempsCourantChef 
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
	
	/**
	 * programme la suite d'évenement pour remplir le réservoir d'un robot.
	 * 
	 * **/
	public long remplirReservoir( long tempsInitial) {
		long tempsCourant = tempsInitial;
		EvenementRemplir evenementRemplirReservoir;
		
		int nombreEvenementsRemplissage = (int)Math.ceil( ((double)this.mrobot.getTempsRemplissage() )/ this.pasSimulation);
		int volumeUnEvenementRemplissage = (int)Math.ceil( ((double)this.mrobot.getVolumeRemplissage() )/ nombreEvenementsRemplissage);
		for(int i = 0 ; i < nombreEvenementsRemplissage ; ++i)
		{
			tempsCourant += this.pasSimulation;
			evenementRemplirReservoir= new EvenementRemplir(tempsCourant, this.mrobot, volumeUnEvenementRemplissage);
			this.simulateur.ajouteEvenement(evenementRemplirReservoir);			

			
		}
		Evenement f = new EvenementLibererRobot(tempsCourant, this.mrobot);
		this.simulateur.ajouteEvenement(f);	
		
		return tempsCourant;
	}

}
