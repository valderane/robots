package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class RobotAPattes extends Robot {

	public static final int VITESSE_PAR_DEFAUT = 30;
	public static final int VITESSE_SUR_ROCHE = 10;
	
	public RobotAPattes(Case c) {
		super(c);
		this.vitesse = RobotAPattes.VITESSE_PAR_DEFAUT;
	}
	

	@Override
	public void setVitesse(int vitesse) throws DataFormatException {}
	
	@Override
	public double getVitesse(NatureTerrain nat) {
		
		if(nat == NatureTerrain.ROCHE)
			return RobotAPattes.VITESSE_SUR_ROCHE;
		
		if(nat == NatureTerrain.EAU)
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
