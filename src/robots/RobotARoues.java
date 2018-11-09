package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;

public class RobotARoues extends Robot {

	private final double VITESSE_PAR_DEFAUT = 80.0;

	private final int TEMPS_REMPLISSAGE = 60 * 10;
	private final int VOLUME_REMPLISSAGE = 500000;
	private final int TEMPS_VIDAGE = 5;
	private final int VOLUME_DEVERSE_PAR_EXTINCTION = 100;

	public RobotARoues(Case c) {
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

	@Override
	public void setVitesse(double vitesse) throws DataFormatException {
		if (vitesse < 0) {
			throw new DataFormatException("Vitesse de robot à roues invalide :" + vitesse);
		}
		super.setVitesse(vitesse);
	}

	@Override
	public double getVitesse(NatureTerrain nat) {
		if (nat == NatureTerrain.TERRAIN_LIBRE || nat == NatureTerrain.HABITAT)
			return this.getVitesse();
		return 0;// vitesse nulle afin d'empêcher les déplacements sur d'autres natures de
					// terrain.
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
		if (nature == NatureTerrain.TERRAIN_LIBRE | nature == NatureTerrain.HABITAT) {
			return true;
		}
		return false;
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
