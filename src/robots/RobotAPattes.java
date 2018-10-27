package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class RobotAPattes extends Robot {

	private final double VITESSE_PAR_DEFAUT = 30.0;
	private final double VITESSE_SUR_ROCHE = 10.0;
	
	public RobotAPattes(Case c) {
		super(c);
		//this.capacite_remplir_ms = 5000*3600;
		this.capacite_vider_litre = 10;
		this.capacite_vider_ms = 1000;

	}
		
	@Override
	public double getVitesse(NatureTerrain nat) {
		
		if(nat == NatureTerrain.ROCHE)
			return this.VITESSE_SUR_ROCHE;
		
		if(nat == NatureTerrain.EAU)
			return 0;//ne peut pas se déplacer sur l'eau et la roche
		
		return this.VITESSE_PAR_DEFAUT;
	}


	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub
		
	}
	
	// vitesse ne peut pas changer
	public void setVitesse(double vitesse) throws DataFormatException{};


	@Override
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		if (nature == NatureTerrain.EAU) 
			return false;
		return true;
	}


}
