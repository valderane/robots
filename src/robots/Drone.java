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
		this.capacite_remplir_sec = 60*30;
		this.capacite_remplir_litre = 10000;
		this.capacite_vider_litre = this.reservoir_eau;
		this.capacite_vider_sec = 30;
		this.vitesse = this.VITESSE_PAR_DEFAUT;
	}
	
	public Drone(Case c, int vitesse) throws DataFormatException{
		super(c);

		this.capacite_remplir_sec = 60*60;
		this.capacite_remplir_litre = 10000;
		this.capacite_vider_litre = this.reservoir_eau;
		this.capacite_vider_sec= 30;
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
		// remplissage du reservoir avec le nombre de litres passé en parametre
		this.reservoir_eau += vol;
		//si on remplit jusqu'a deborder, on conserve la capacite max
		if(this.reservoir_eau >= this.capacite_remplir_litre ) {
			this.reservoir_eau = this.capacite_remplir_litre;
			System.out.println("reservoir plein !");
		}
		
	}

	@Override
	//le drone peut aller partout
	public boolean appartientTerrainRobot(NatureTerrain nature) {		
		return true;
	}





}
