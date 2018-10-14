package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class Drone extends Robot {

	public static final int VITESSE_PAR_DEFAUT = 100;
	public static final int VITESSE_MAX = 150;

	
	public Drone(Case c) {
		super(c);
		this.vitesse = Drone.VITESSE_PAR_DEFAUT;
	}
	
	public Drone(Case c, int vitesse) throws DataFormatException{
		super(c);
		this.setVitesse(vitesse);
	}
	
	@Override
	public void setVitesse(int vitesse) throws DataFormatException {
		if(0 > vitesse || vitesse > Drone.VITESSE_MAX) {
			 throw new DataFormatException("Vitesse de drone invalide :"+vitesse);
		}
		this.vitesse = vitesse;
	}
	
	@Override
	public double getVitesse(NatureTerrain nat) {
		// TODO Auto-generated method stub
		return this.vitesse;
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
