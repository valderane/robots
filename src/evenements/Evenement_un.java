package evenements;

import carte.Carte;
import carte.Direction;
import robots.Robot;

//deplace le robot vers le nord 1 fois de suite
public class Evenement_un extends Evenement{

	private Robot robot;
	private Carte carte;
	
	
	//Robot pour obtenir robot+case et carte pour obtenir info
	public Evenement_un(long date, Robot robot, Carte carte) {
		super(date);
		this.robot = robot;
		this.carte = carte;
	}

	

//ou faire les verifs?
	@Override
	public void execute() {
		robot.deplacer(Direction.NORD, carte);
		}
}


