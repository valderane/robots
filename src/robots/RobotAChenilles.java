package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;

public class RobotAChenilles extends Robot {

	private final double VITESSE_PAR_DEFAUT = 60;
	private final double VITESSE_MAX = 80;

	private final int TEMPS_REMPLISSAGE = 60 * 5;
	private final int VOLUME_REMPLISSAGE = 2000;
	private final int TEMPS_VIDAGE = 8;
	private final int VOLUME_DEVERSE_PAR_EXTINCTION = 100;

	public RobotAChenilles(Case c) {
		super(c);
		this.setReservoirEau(VOLUME_REMPLISSAGE);
		this.setTempsRemplissage(this.TEMPS_REMPLISSAGE);
		this.setVolumeRemplissage(this.VOLUME_REMPLISSAGE);
		this.setVolumeDeverseParExtinction(this.VOLUME_DEVERSE_PAR_EXTINCTION);
		this.setTempsVidage(this.TEMPS_VIDAGE);

		try {
			this.setVitesse(this.VITESSE_PAR_DEFAUT);
		} catch (DataFormatException e) {
			System.err.println(e);
		}
	}

	public RobotAChenilles(Case c, int vitesse) throws DataFormatException {
		super(c);
		this.setReservoirEau(VOLUME_REMPLISSAGE);
		this.setTempsRemplissage(this.TEMPS_REMPLISSAGE);
		this.setVolumeRemplissage(this.VOLUME_REMPLISSAGE);
		this.setVolumeDeverseParExtinction(this.VOLUME_DEVERSE_PAR_EXTINCTION);
		this.setTempsVidage(this.TEMPS_VIDAGE);

		try {
			this.setVitesse(vitesse);
		} catch (DataFormatException e) {
			System.err.println(e);
		}

	}

	public void setVitesse(double vitesse) throws DataFormatException {

		if (vitesse < 0 || vitesse > this.VITESSE_MAX) {
			throw new DataFormatException("Vitesse de robot à chenilles invalide :" + vitesse);
		}
		super.setVitesse(vitesse);
	}

	@Override
	public double getVitesse(NatureTerrain nat) {

		if (nat == NatureTerrain.FORET)
			return this.getVitesse() * 0.5;

		if (nat == NatureTerrain.EAU || nat == NatureTerrain.ROCHE)
			return 0;// ne peut pas se déplacer sur l'eau et la roche

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
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		if (nature == NatureTerrain.EAU | nature == NatureTerrain.ROCHE)
			return false;
		return true;
	}

	@Override
	public boolean estBienPlacePourRemplissage(Case caseRobot, Carte carte) {
		for (Direction dir : Direction.values()) {
			if (carte.getVoisin(caseRobot, dir).getNature() == NatureTerrain.EAU)
				return true;
		}
		return false;
	}

}
