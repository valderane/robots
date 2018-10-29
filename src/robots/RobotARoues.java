package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class RobotARoues extends Robot {

	private final double VITESSE_PAR_DEFAUT = 80.0;
	/**
	 * Attributs
	 */

	
	public RobotARoues(Case c) {
		super(c);
		this.capacite_remplir_sec = 60*10;
		this.capacite_vider_litre = 100;
		this.capacite_vider_sec = 5;
		
		try {
			this.setVitesse(this.VITESSE_PAR_DEFAUT);
		}catch(DataFormatException e){
			System.err.println(e);
		}
	}
	
	
	@Override
	public void setVitesse(double vitesse) throws DataFormatException {
		if(vitesse < 0) {
			 throw new DataFormatException("Vitesse de robot à roues invalide :"+vitesse);
		}
		this.vitesse = vitesse;
	}
	
	@Override
	public double getVitesse(NatureTerrain nat) {
		if(nat == NatureTerrain.TERRAIN_LIBRE || nat == NatureTerrain.HABITAT)
			return this.vitesse;
		return 0;//vitesse nulle afin d'empêcher les déplacements sur d'autres natures de terrain.
	}



	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		if (nature == NatureTerrain.TERRAIN_LIBRE | nature == NatureTerrain.HABITAT){
			return true;
		}
		return false;
	}


	@Override
	public double getTempsParcours(NatureTerrain nat1, NatureTerrain nat2) {
		// TODO Auto-generated method stub
		return 0;
	}


}
