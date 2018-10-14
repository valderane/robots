package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class RobotAChenilles extends Robot {

	public static final int VITESSE_PAR_DEFAUT = 60;
	public static final int VITESSE_MAX = 80;
	
	public RobotAChenilles(Case c) {
		super(c);
		this.vitesse = RobotAChenilles.VITESSE_PAR_DEFAUT;
	}
	
	public RobotAChenilles(Case c, int vitesse) throws DataFormatException {
		super(c);
		this.setVitesse(vitesse);
	}
	
	@Override
	public void setVitesse(int vitesse) throws DataFormatException {
		
		if(0 > vitesse || vitesse > RobotAChenilles.VITESSE_MAX) {
			 throw new DataFormatException("Vitesse de robot à chenilles invalide :"+vitesse);
		}
		this.vitesse = vitesse;
	}
	
	@Override
	public double getVitesse(NatureTerrain nat) {
		
		if(nat == NatureTerrain.FORET)
			return this.vitesse * 0.5;
		
		if(nat == NatureTerrain.EAU || nat == NatureTerrain.ROCHE)
			return 0;//ne peut pas se déplacer sur l'eau et la roche
		
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
