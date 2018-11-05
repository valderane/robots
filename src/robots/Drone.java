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

	
	public Drone(Case c) {
		super(c);
		this.capaciteRemplirSec = 60*30;
		this.capaciteRemplirLitre = 10000;
		this.capaciteViderLitre = this.reservoirEau;
		this.capaciteViderSec = 30;
		this.vitesse = this.VITESSE_PAR_DEFAUT;
	}
	
	public Drone(Case c, int vitesse) throws DataFormatException{
		super(c);

		this.capaciteRemplirSec = 60*60;
		this.capaciteRemplirLitre = 10000;
		this.capaciteViderLitre = this.reservoirEau;
		this.capaciteViderSec= 30;
		this.setVitesse(vitesse);
	}

	public void setVitesse(double vitesse) throws DataFormatException {
		if(0 > vitesse || vitesse > this.VITESSE_MAX) {
			 throw new DataFormatException("Vitesse de drone invalide :"+vitesse);
		}
		this.vitesse = vitesse;
	}
	
	@Override
	//Vitesse constante
	public double getVitesse(NatureTerrain nat) {
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
	//le drone peut aller partout
	public boolean appartientTerrainRobot(NatureTerrain nature) {		
		return true;
	}





}
