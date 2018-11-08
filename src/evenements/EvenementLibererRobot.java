package evenements;

import carte.Carte;
import carte.Direction;
import exceptions.exceptions_deplacement.ProchaineCaseMauvaiseNature;
import exceptions.exceptions_deplacement.RobotSorsCarte;
import robots.Robot;

//deplace le robot vers la direction donnée une  fois de suite si possible. sinon pour l'instant ne fait rien
public class EvenementLibererRobot extends Evenement {

	private Robot robot;

	// Robot pour obtenir robot+case et carte pour obtenir info
	public EvenementLibererRobot(long date, Robot robot) {
		super(date);
		this.robot = robot;
	}

//	public int tab[] nombreCaseDepaceEtPositionActuelle(){
//		
//	}

	// parametre: pochaine date (date courante + pas) du simulateur.
	// besoin pour calculer qtté remplissage + vitesse deplacement.
	@Override
	public void execute() {
		this.robot.setLibre(true);
	}

}
