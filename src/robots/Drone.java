package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class Drone extends Robot {

	/**
	 * Constantes
	 */
	private final double VITESSE_PAR_DEFAUT = 100.0;
	private final double VITESSE_MAX = 150.0;
	
	/**
	 * Attributs
	 */
    protected double vitesse;

	
	public Drone(Case c) {
		super(c);
		this.vitesse = this.VITESSE_PAR_DEFAUT;
	}
	
	public Drone(Case c, int vitesse) throws DataFormatException{
		super(c);
		this.setVitesse(vitesse);
	}

	public void setVitesse(double vitesse) throws DataFormatException {
		if(0 > vitesse || vitesse > this.VITESSE_MAX) {
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
