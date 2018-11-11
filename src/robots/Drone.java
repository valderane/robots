package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.NatureTerrain;

/**
 * Se déplace sur tout type de terrain, et vide son réservoir en une extinction
 * 
 * @author Equipe 23
 *
 */
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
	 * Constructeur
	 * 
	 * @param c Position du drone
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

	/**
	 * Constructeur
	 * 
	 * @param c       Position du drone
	 * @param vitesse vitesse du drone
	 * 
	 * @throws DataFormatException Renvoyée si la vitesse ne respecte pas les
	 *                             contraintes
	 */
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
	public double getVitesse(NatureTerrain nat) {
		// Vitesse constante
		return this.getVitesse();
	}

	/* (non-Javadoc)
	 * @see robots.Robot#remplirReservoir(int)
	 */
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

	/* (non-Javadoc)
	 * @see robots.Robot#appartientTerrainRobot(carte.NatureTerrain)
	 */
	@Override
	public boolean appartientTerrainRobot(NatureTerrain nature) {
		// le drone peut aller partout
		return true;
	}

	/* (non-Javadoc)
	 * @see robots.Robot#estBienPlacePourRemplissage(carte.Case, carte.Carte)
	 */
	@Override
	public boolean estBienPlacePourRemplissage(Case caseRobot, Carte carte) {
		//se remplit sur une case d'eau
		return (caseRobot.getNature() == NatureTerrain.EAU);
	}

}
