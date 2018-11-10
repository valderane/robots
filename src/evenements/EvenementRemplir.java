package evenements;

import robots.Robot;


public class EvenementRemplir extends Evenement {
	private Robot robot;
	
	private int volumeEau;
	
	public EvenementRemplir(long dateEvenement, Robot robot, int volumeEau) {
		super(dateEvenement);
		this.robot = robot;
		this.volumeEau = volumeEau;
	}
	
	@Override
	public void execute() {
		
		int futurVolumeReservoir;
		if(this.robot.getReservoirEau() + this.volumeEau > this.robot.getVolumeRemplissage())
			futurVolumeReservoir = this.robot.getVolumeRemplissage();
		else
			futurVolumeReservoir = this.robot.getReservoirEau() + this.volumeEau;
		
		this.robot.setReservoirEau(futurVolumeReservoir);
	}
}
