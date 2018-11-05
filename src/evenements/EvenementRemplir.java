package evenements;

import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import io.DonneesSimulation;
import robots.Drone;
import robots.Robot;
import robots.RobotAPattes;

public class EvenementRemplir extends Evenement {
	private Robot robot;
	private DonneesSimulation data;
	
	public EvenementRemplir(long dateEvenement, Robot robot, /*Carte carte*/ DonneesSimulation donneesSimu ) {
		super(dateEvenement);
		this.robot = robot;
		this.data = donneesSimu;
	}
	
	@Override
	public void execute(long prochaineDate) {
		
		if( !(this.robot instanceof RobotAPattes ) ) {
			// si le robot n'est pas un robot a pattes (les robots a patte ne se remplissent pas )
			
			long differenceTemps = prochaineDate - this.date;
			int volumeARemplir = (int)(robot.getCapaciteRemplirLitre() * differenceTemps) / robot.getCapaciteRemplirSec();
			//System.out.println("vol_a_vider:" + volume_a_vider+ " differenceTemps "+ differenceTemps);
			
			if(this.robot instanceof Drone  ) {
	
				Case maCase = this.data.getCarte().getCase( this.robot.getPosition().getLigne() , this.robot.getPosition().getColonne());
				if(maCase.getNature() == NatureTerrain.EAU) {
					//si une case voisine est de type eau
					this.robot.remplirReservoir(volumeARemplir);
					System.out.println("remplissage du robot drone position "+ this.robot.getPosition().toString() +" d'un volume="+volumeARemplir+" litres");
				}
				else {
					System.out.println("le robot position "+ this.robot.getPosition().toString() +" n'est pas sur une case d'eau !!");
				}
				
			}
			else {
				
				Case voisinNord = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.NORD);   
				Case voisinSud = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.SUD);     
				Case voisinEst = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.EST);     
				Case voisinOuest = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.OUEST);
				
				Case voisins[] = { voisinNord, voisinSud, voisinEst, voisinOuest  };
				
				int compteurVoisin = 0;
				
				for(int i = 0; i < voisins.length; i++) {
					
					if(voisins[i].getNature() == NatureTerrain.EAU) {
						//si une case voisine est de type eau
						this.robot.remplirReservoir(volumeARemplir);
						System.out.println("remplissage du robot position "+ this.robot.getPosition().toString() +" d'un volume="+volumeARemplir+" litres");
						break;
					}
					else {
						compteurVoisin++;
					}
					
					if(compteurVoisin == 4) {
						System.out.println("le robot position "+ this.robot.getPosition().toString() +" n'a pas de source de remplissage a proximit�");
					}
					
				}
				
			}
		
		}
		else {
			System.out.println("robot a pattes, ne se remplit pas");
		}
		
	}
}
