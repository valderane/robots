package robots;

import java.util.zip.DataFormatException;

import carte.Case;
import carte.NatureTerrain;

public class RobotAChenilles extends Robot {



	private final double VITESSE_PAR_DEFAUT = 60;
	private final double VITESSE_MAX = 80;
	



	/**
	 * attributs
	 */
	
	public RobotAChenilles(Case c) {
		super(c);
		
		this.capaciteRemplirSec = 60*5;
		this.capaciteViderLitre = 100;
		this.capaciteRemplirLitre = 2000;
		this.capaciteViderSec = 8;

		try {
			this.setVitesse(this.VITESSE_PAR_DEFAUT);
		}catch(DataFormatException e){
			System.err.println(e);
		}
	}
	
	public RobotAChenilles(Case c, int vitesse) throws DataFormatException {
		super(c);
		this.capaciteRemplirSec = 60*5;
		this.capaciteViderLitre = 100;
		this.capaciteRemplirLitre = 2000;
		this.capaciteViderSec = 8;
		this.setVitesse(vitesse);
	}
	

	public void setVitesse(double vitesse) throws DataFormatException {
		
		if(vitesse < 0 || vitesse > this.VITESSE_MAX) {
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
		if (nature == NatureTerrain.EAU | nature == NatureTerrain.ROCHE) 
			return false;
		return true;
	}

	
	
	
}
