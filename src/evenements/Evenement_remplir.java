package evenements;

import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import io.DonneesSimulation;
import robots.Drone;
import robots.Robot;
import robots.RobotAPattes;

public class Evenement_remplir extends Evenement {
	private Robot robot;
	private DonneesSimulation data;
	
	public Evenement_remplir(long date_evenement, Robot robot, /*Carte carte*/ DonneesSimulation donne_simu ) {
		super(date_evenement);
		this.robot = robot;
		this.data = donne_simu;
	}
	
	@Override
	public void execute(long prochaine_date) {
		
		if( !(this.robot instanceof RobotAPattes ) ) {
			// si le robot n'est pas un robot a pattes (les robots a patte ne se remplissent pas )
			
			long difference_temps = prochaine_date - this.date;
			int volume_a_remplir = (int)(robot.getCapacite_remplir_litre() * difference_temps) / robot.getCapacite_remplir_sec();
			//System.out.println("vol_a_vider:" + volume_a_vider+ " difference_temps "+ difference_temps);
			
			if(this.robot instanceof Drone  ) {
	
				Case ma_case = this.data.getCarte().getCase( this.robot.getPosition().getLigne() , this.robot.getPosition().getColonne());
				if(ma_case.getNature() == NatureTerrain.EAU) {
					//si une case voisine est de type eau
					this.robot.remplirReservoir(volume_a_remplir);
					System.out.println("remplissage du robot drone position "+ this.robot.getPosition().toString() +" d'un volume="+volume_a_remplir+" litres");
				}
				else {
					System.out.println("le robot position "+ this.robot.getPosition().toString() +" n'est pas sur une case d'eau !!");
				}
				
			}
			else {
				
				Case voisin_nord = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.NORD);   
				Case voisin_sud = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.SUD);     
				Case voisin_est = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.EST);     
				Case voisin_ouest = this.data.getCarte().getVoisin(this.robot.getPosition(), Direction.OUEST);
				
				Case voisins[] = { voisin_nord, voisin_sud, voisin_est, voisin_ouest  };
				
				int compteur_voisin = 0;
				
				for(int i = 0; i < voisins.length; i++) {
					
					if(voisins[i].getNature() == NatureTerrain.EAU) {
						//si une case voisine est de type eau
						this.robot.remplirReservoir(volume_a_remplir);
						System.out.println("remplissage du robot position "+ this.robot.getPosition().toString() +" d'un volume="+volume_a_remplir+" litres");
						break;
					}
					else {
						compteur_voisin++;
					}
					
					if(compteur_voisin == 4) {
						System.out.println("le robot position "+ this.robot.getPosition().toString() +" n'a pas de source de remplissage a proximité");
					}
					
				}
				
			}
		
		}
		else {
			System.out.println("robot a pattes, ne se remplit pas");
		}
		
	}
}
