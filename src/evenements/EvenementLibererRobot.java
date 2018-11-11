package evenements;

import robots.Robot;

//deplace le robot vers la direction donnée une  fois de suite si possible. sinon pour l'instant ne fait rien
/**
 * Evenement pour libérer un robot à une certaine date. Un robot libre peut être
 * assigné pour éteindre un incendie.
 * 
 * @author Equipe 23
 *
 */
public class EvenementLibererRobot extends Evenement {

	private Robot robot;

	/**
	 * @param date  Date de l'évenement.
	 * @param robot Robot à libérer.
	 */
	public EvenementLibererRobot(long date, Robot robot) {
		super(date);
		this.robot = robot;
	}

	/* (non-Javadoc)
	 * @see evenements.Evenement#execute()
	 */
	@Override
	public void execute() {
		this.robot.setLibre(true);
	}

}
