package evenements;


import carte.Incendie;
import exceptions.exceptions_remplissage.PlusDeau;
import exceptions.exceptions_remplissage.PlusDeauEtReservoirVide;
import exceptions.exceptions_remplissage.ReservoirVide;
import io.DonneesSimulation;
import robots.Robot;



/**
 * @author emmanuel
 *Calcule la quantité d'eau à déverser en fonction du robot et du laps de temps entre la date de l'évenement et la
 *date du prochain next.
 */
public class EvenementDeverser extends Evenement {
	
	/**
	 * 
	 */
	private Robot robot;

	private Incendie incendieEnCours;
	
	private int volumeEauDeversee;

	/**
	 * @param dateEvenement
	 * @param robot
	 * @param donneSimu
	 * @param prochaineDate date du prochain next
	 */
	public EvenementDeverser(long dateEvenement, Robot robot, Incendie incendieEnCours, int volumeEauDeversee) {
		super(dateEvenement);
		this.robot = robot;
		this.incendieEnCours = incendieEnCours;
		this.volumeEauDeversee = volumeEauDeversee;
	}

	@Override
	public void execute() {
		this.robot.setReservoirEau(this.robot.getReservoirEau()- this.volumeEauDeversee);
		
		if( (this.incendieEnCours.getIntensite() - this.volumeEauDeversee) < 0)
			this.incendieEnCours.setIntensite(0);
		else
			this.incendieEnCours.setIntensite(this.incendieEnCours.getIntensite() - this.volumeEauDeversee);

	}
}

