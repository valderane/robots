package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class RobotARoues extends Robot {

	public static final int VITESSE_PAR_DEFAUT = 80;
	
	public RobotARoues(Case c) {
		super(c);
		this.vitesse = RobotARoues.VITESSE_PAR_DEFAUT;
	}
	
	
	@Override
	public void setVitesse(int vitesse) throws DataFormatException {
		if(0 > vitesse) {
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
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub
		
	}


}
