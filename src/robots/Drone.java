package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.NatureTerrain;

public class Drone extends Robot {

	/**
	 * Constantes
	 */
	private final double VITESSE_PAR_DEFAUT = 100.0;
	private final double VITESSE_MAX = 150.0;
	private final int TEMPS_REMPLISSAGE = 60 * 30;
	private final int VOLUME_REMPLISSAGE = 10000;
	private final int TEMPS_VIDAGE = 30;

	/**
	 * Attributs
	 */

	public Drone(Case c) {
		super(c);
		this.setReservoirEau(VOLUME_REMPLISSAGE);
		this.setTempsRemplissage(this.TEMPS_REMPLISSAGE);
		this.setVolumeRemplissage(this.VOLUME_REMPLISSAGE);
		this.setVolumeDeverseParExtinction(this.getReservoirEau());
		this.setTempsVidage(this.TEMPS_VIDAGE);
		
		try {
			this.setVitesse(this.VITESSE_PAR_DEFAUT);
		} catch (DataFormatException e) {
			System.err.println(e);
		}
	}

	public Drone(Case c, int vitesse) throws DataFormatException {
		super(c);
		this.setReservoirEau(VOLUME_REMPLISSAGE);
		this.setTempsRemplissage(this.TEMPS_REMPLISSAGE);
		this.setVolumeRemplissage(this.VOLUME_REMPLISSAGE);
		this.setVolumeDeverseParExtinction(this.getReservoirEau());
		this.setTempsVidage(this.TEMPS_VIDAGE);
		
		try {
			this.setVitesse(vitesse);
		} catch (DataFormatException e) {
			System.err.println(e);
		}
	}

	public void setVitesse(double vitesse) throws DataFormatException {
		if (0 > vitesse || vitesse > this.VITESSE_MAX) {
			throw new DataFormatException("Vitesse de drone invalide :" + vitesse);
		}
		super.setVitesse(vitesse);
	}

	@Override
	// Vitesse constante
	public double getVitesse(NatureTerrain nat) {
		return this.getVitesse();
	}

	@Override
	public void remplirReservoir(int vol) {
		// remplissage du reservoir avec le nombre de litres pass� en parametre
		this.setReservoirEau(this.getReservoirEau() + vol);
		// si on remplit jusqu'a deborder, on conserve la capacite max
		if (this.getReservoirEau() >= this.getVolumeRemplissage()) {
			this.setReservoirEau(this.getVolumeRemplissage());
			System.out.println("reservoir plein !");
		}

	}

	@Override
	// le drone peut aller partout
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		return true;
	}

	@Override
	public boolean estBienPlacePourRemplissage(Case caseRobot, Carte carte) {
		return (caseRobot.getNature() == NatureTerrain.EAU);
	}

	
}
