package evenements;

import carte.Carte;
import carte.Direction;
import exceptions.exceptions_deplacement.ProchaineCaseMauvaiseNature;
import exceptions.exceptions_deplacement.RobotSorsCarte;
import robots.Robot;

//deplace le robot vers la direction donnée une  fois de suite si possible. sinon pour l'instant ne fait rien
public class EvenementDeplacer extends Evenement {

	private Robot robot;
	private Carte carte;
	private Direction direction;

	// Robot pour obtenir robot+case et carte pour obtenir info
	public EvenementDeplacer(long date, Robot robot, Carte carte, Direction direction) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.direction = direction;
	}


	// parametre: pochaine date (date courante + pas) du simulateur.
	// besoin pour calculer qtté remplissage + vitesse deplacement.
	@Override
	public void execute() {
		//System.out.println(robot);
		
		try {
			robot.deplacer(direction, carte);
		}
		catch (RobotSorsCarte e) {
			System.out.println(e);
		}
		catch (ProchaineCaseMauvaiseNature e) {
			System.out.println(e);

		}
        System.out.println("case courante" + robot.getPosition() +" "+ this.getDate());

		}
	}
	
