package evenements;

import carte.Carte;
import carte.Direction;
import exceptions.exceptions_deplacement.ProchaineCaseMauvaiseNature;
import exceptions.exceptions_deplacement.RobotSorsCarte;
import robots.Robot;

/**
 * Deplace le robot vers la direction donnée à une certaine date.
 * 
 * @author Equipe 23
 *
 */
public class EvenementDeplacer extends Evenement {

	private Robot robot;
	private Carte carte;
	private Direction direction;

	/**
	 * @param date      Date de l'événement
	 * @param robot     Robot à déplacer
	 * @param carte     Carte
	 * @param direction Direction à emprunter
	 */
	public EvenementDeplacer(long date, Robot robot, Carte carte, Direction direction) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.direction = direction;
	}

	/* (non-Javadoc)
	 * @see evenements.Evenement#execute()
	 */
	@Override
	public void execute() {
		try {
			robot.deplacer(direction, carte);
		} catch (RobotSorsCarte e) {
			System.out.println(e);
		} catch (ProchaineCaseMauvaiseNature e) {
			System.out.println(e);

		}

	}
}
