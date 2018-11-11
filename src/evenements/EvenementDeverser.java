package evenements;

import carte.Incendie;
import robots.Robot;

/**
 * Deverse un volume d'eau sur un incendie à une certaine date.
 * 
 * @author Equipe 23
 * 
 */
public class EvenementDeverser extends Evenement {

	private Robot robot;

	private Incendie incendieEnCours;

	private int volumeEauDeversee;

	/**
	 * Constructeur
	 * 
	 * @param dateEvenement     date de l'événement
	 * @param robot             Robot qui déverse de l'eau
	 * @param incendieEnCours   Incendie sur lequel l'eau va être déversée
	 * @param volumeEauDeversee Volume d'eau déversé
	 */
	public EvenementDeverser(long dateEvenement, Robot robot, Incendie incendieEnCours, int volumeEauDeversee) {
		super(dateEvenement);
		this.robot = robot;
		this.incendieEnCours = incendieEnCours;
		this.volumeEauDeversee = volumeEauDeversee;
	}

	/* (non-Javadoc)
	 * @see evenements.Evenement#execute()
	 */
	@Override
	public void execute() {
		this.robot.setReservoirEau(this.robot.getReservoirEau() - this.volumeEauDeversee);

		if ((this.incendieEnCours.getIntensite() - this.volumeEauDeversee) < 0)
			this.incendieEnCours.setIntensite(0);
		else
			this.incendieEnCours.setIntensite(this.incendieEnCours.getIntensite() - this.volumeEauDeversee);

	}
}
