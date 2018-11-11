package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;

/**
 * Est ralenti sur la roche, ne peut pas se déplacer sur l'eau. A un réservoir à
 * poudre (infini).
 * 
 * @author Equipe 23
 *
 */
public class RobotAPattes extends Robot {

	private final double VITESSE_PAR_DEFAUT = 30.0;
	private final double VITESSE_SUR_ROCHE = 10.0;

	private final int TEMPS_REMPLISSAGE = -1;
	private final int VOLUME_REMPLISSAGE = Integer.MAX_VALUE;
	private final int TEMPS_VIDAGE = 1;
	private final int VOLUME_DEVERSE_PAR_EXTINCTION = 10;

	/**
	 * Constructeur du robot
	 * 
	 * @param c Position du robot
	 */
	public RobotAPattes(Case c) {
		super(c);
		this.setReservoirEau(VOLUME_REMPLISSAGE);
		this.setTempsRemplissage(this.TEMPS_REMPLISSAGE);
		this.setVolumeRemplissage(this.VOLUME_REMPLISSAGE);
		this.setVolumeDeverseParExtinction(this.VOLUME_DEVERSE_PAR_EXTINCTION);
		this.setTempsVidage(this.TEMPS_VIDAGE);
	}

	@Override
	public double getVitesse(NatureTerrain nat) {

		if (nat == NatureTerrain.ROCHE)
			return this.VITESSE_SUR_ROCHE;

		if (nat == NatureTerrain.EAU)
			return 0;// ne peut pas se déplacer sur l'eau et la roche

		return this.VITESSE_PAR_DEFAUT;
	}

	@Override
	public void remplirReservoir(int vol) {
		System.out.println(" Robot a patte, je ne me remplis pas ^^");
	}

	@Override
	public void setVitesse(double vitesse) throws DataFormatException {
	};

	@Override
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		if (nature == NatureTerrain.EAU)
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
