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
	
	private int newReservoirEau;
	
	private int newIntensite;
	/**
	 * @param dateEvenement
	 * @param robot
	 * @param donneSimu
	 * @param prochaineDate date du prochain next
	 */
	public EvenementDeverser(long dateEvenement, Robot robot, Incendie incendieEnCours, int newReservoirEau, int newIntensite) {
		super(dateEvenement);
		this.robot = robot;
		this.incendieEnCours = incendieEnCours;
		this.newReservoirEau = newReservoirEau;
		this.newIntensite = newIntensite;
	}

	@Override
	public void execute() {
		this.robot.setReservoirEau(this.newReservoirEau);
		this.incendieEnCours.setIntensite(newIntensite);

	}
}

