package evenements;

import robots.Robot;

/**
 * Evenement pour remplir un robot d'un certain volume à une certaine date.
 * 
 * @author Equipe 23
 *
 */
public class EvenementRemplir extends Evenement {
	private Robot robot;

	private int volumeEau;

	/**
	 * Constructeur
	 * 
	 * @param dateEvenement Date de l'événement.
	 * @param robot         Robot à remplir.
	 * @param volumeEau     Volume d'eau à remplir.
	 */
	public EvenementRemplir(long dateEvenement, Robot robot, int volumeEau) {
		super(dateEvenement);
		this.robot = robot;
		this.volumeEau = volumeEau;
	}

	/* (non-Javadoc)
	 * @see evenements.Evenement#execute()
	 */
	@Override
	public void execute() {

		int futurVolumeReservoir;
		if (this.robot.getReservoirEau() + this.volumeEau > this.robot.getVolumeRemplissage())
			futurVolumeReservoir = this.robot.getVolumeRemplissage();
		else
			futurVolumeReservoir = this.robot.getReservoirEau() + this.volumeEau;

		this.robot.setReservoirEau(futurVolumeReservoir);
	}
}
