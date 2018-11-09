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
		this.capaciteRemplirSec = 60*10;
		this.capaciteRemplirLitre = 5000000;
		this.capaciteViderLitre = 100;
		this.capaciteViderSec = 5;
		this.reservoirEau = this.capaciteRemplirLitre;	
		
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
	public void remplirReservoir(int vol) {
		// remplissage du reservoir avec le nombre de litres pass� en parametre
		this.reservoirEau += vol;
		//si on remplit jusqu'a deborder, on conserve la capacite max
		if(this.reservoirEau >= this.capaciteRemplirLitre ) {
			this.reservoirEau = this.capaciteRemplirLitre;
			System.out.println("reservoir plein !");
		}
		
	}

	@Override
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		if (nature == NatureTerrain.TERRAIN_LIBRE | nature == NatureTerrain.HABITAT){
			return true;
		}
		return false;
	}


	


}
