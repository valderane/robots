package evenements;

import robots.Robot;


public class EvenementRemplir extends Evenement {
	private Robot robot;
	
	public EvenementRemplir(long dateEvenement, Robot robot) {
		super(dateEvenement);
		this.robot = robot;
	}
	
	@Override
	public void execute() {
		this.robot.setReservoirEau(this.robot.getVolumeRemplissage());
	}
}
