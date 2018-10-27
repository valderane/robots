package evenements;

import carte.Carte;
import carte.Direction;
import robots.Robot;

//deplace le robot vers la direction donnée une  fois de suite si possible. sinon pour l'instant ne fait rien
public class Evenement_deplacer extends Evenement{

	private Robot robot;
	private Carte carte;
	private Direction direction;
	
	//Robot pour obtenir robot+case et carte pour obtenir info
	public Evenement_deplacer(long date, Robot robot, Carte carte, Direction direction) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.direction = direction;
	}

	

	//parametre: pochaine date (date courante + pas) du simulateur.
	//besoin pour calculer qtté remplissage + vitesse deplacement.
	@Override
	public void execute(long prochaine_date) {
		System.out.println(robot);
		robot.deplacer(direction, carte);
		}
}


