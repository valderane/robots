package robots;

import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.Incendie;
import carte.NatureTerrain;
import evenements.Simulateur;
import exceptions.exceptions_chemins.AucunCheminPossible;
import exceptions.exceptions_deplacement.ProchaineCaseMauvaiseNature;
import exceptions.exceptions_deplacement.RobotSorsCarte;
import io.DonneesSimulation;

public abstract class Robot {

	/**
	 * TODO
	 */
	protected Case position;

	/* qqté d'eau max que peut tranqporter le robot */
	/* 9000 pour test */
	/**
	 * TODO
	 */
	protected int reservoirEau = 0;

	// capacité vider_litre en capacité vider_ms
	/**
	 * TODO
	 */
	protected int capaciteViderLitre;

	/**
	 * TODO
	 */
	protected int capaciteViderSec;

	// temps qu'il faut pour remplie tout le reservoir

	/**
	 * TODO
	 */
	protected int capaciteRemplirSec;

	// nombre de litre maximum du reservoir
	/**
	 * TODO
	 */
	protected int capaciteRemplirLitre;

	/**
	 * TODO
	 */
	protected double vitesse;

	/* la taille des case doit etre > 1 */
	/**
	 * TODO
	 */
	private int positionDansCase = 0;

	private boolean libre;

	private DeplacementRobot gestionnaireDeplacement;

	private DeverserRobot gestionnaireVidage;

	/**
	 * @param c
	 */
	public Robot(Case c) {
		this.position = c;
		this.setLibre(true);
	}

	public void initialiserGestionnaireDeplacement(Simulateur simulateur, double pasSimulation, Carte carte) {
		this.gestionnaireDeplacement = new DeplacementRobot(this, simulateur, pasSimulation, carte);
	}

	public void initialiserGestionnaireVidage(Simulateur simulateur, long pasSimulation) {
		this.gestionnaireVidage = new DeverserRobot(this, simulateur, pasSimulation);
	}

	/**
	 * @return
	 */
	public Case getPosition() {
		return this.position;
	}

	/**
	 * @param c
	 */
	public void setPosition(Case c) {
		this.position = c;
		// TODO
	}

	// TODO: on vérifie que le robot peut aller sur le terrain "nature".
	// chaque robot à ses terrains
	public abstract boolean appartientTerrainRobot(NatureTerrain nature);
	// Pour vérifier, doit être en connaissance de la carte.

	// deplace le robot si il a le droit. Sinon ne fait rien.
	public void deplacer(Direction direction, Carte carte) throws RobotSorsCarte, ProchaineCaseMauvaiseNature {
		// Verification qu'on peut le déplacer (case existe + bonne nature)
		if (carte.voisinExiste(this.getPosition(), direction)) {

			if (this.appartientTerrainRobot(carte.getVoisin(this.getPosition(), direction).getNature())) {
				this.setPosition(carte.getVoisin(this.position, direction));

			} else {
				throw new ProchaineCaseMauvaiseNature(this + ": Mauvaise nature terrain pour prochaine case");
			}
		}

		else {
			throw new RobotSorsCarte("Robot " + this + " sort de la carte. Case initiale : " + this.getPosition()
					+ ", direction : " + direction);
		}
	}

	public void deplacer(Case c) throws AucunCheminPossible {
		this.gestionnaireDeplacement.deplacer_robot(c);
	}

	/**
	 * @param incendie Incendie à éteindre
	 * @param data     Données de simulation contenant l'incendie à éteindre
	 */
	public void eteindreIncendie(Incendie incendie, DonneesSimulation data) throws AucunCheminPossible {
		long tempsFinEvenement;
		Case caseDestination = incendie.getPosition();

		tempsFinEvenement = this.gestionnaireDeplacement.deplacer_robot(caseDestination);
		this.gestionnaireVidage.deverserEau(incendie, tempsFinEvenement, data);

	}

	/**
	 * Indique si la case est accessible par le robot. Attention, cela calcule le
	 * plus court chemin et peut être couteux!
	 * 
	 * @param caseDestination
	 * @return
	 */
	public boolean caseEstAccessible(Case caseDestination) {
		return this.gestionnaireDeplacement.caseEstAccessible(caseDestination);
	}

	public int getPositionDansCase() {
		return positionDansCase;
	}

	public void setPositionDansCase(int nouvellePositionDansCase, int tailleCase) {
		if (nouvellePositionDansCase < tailleCase & nouvellePositionDansCase >= 0)
			this.positionDansCase = nouvellePositionDansCase;
		else
			throw new IllegalArgumentException(
					"nouvelle_position incorrect : " + nouvellePositionDansCase + "< 0 ou > taille_case");
	}

	public int getCapaciteViderLitre() {
		return capaciteViderLitre;
	}

	public int getCapaciteViderSec() {
		return capaciteViderSec;
	}

	public int getCapaciteRemplirLitre() {
		return capaciteViderLitre;
	}

	public int getCapaciteRemplirSec() {
		return capaciteRemplirSec;
	}

	public abstract double getVitesse(NatureTerrain nat);

	/**
	 * Retourne le temps de parcours de deux cases adjacentes avec chacune une
	 * nature donnée.
	 * 
	 * @param nat1 Nature du terrain de la première case
	 * @param nat2 Nature du terrain de la seconde case
	 * @return
	 */
	public double getTempsParcours(NatureTerrain nat1, NatureTerrain nat2, int tailleCase) throws AucunCheminPossible {
		if (this.getVitesse(nat1) == 0 || this.getVitesse(nat2) == 0) {
			throw new AucunCheminPossible("Chemin impossible");
		}

		double vitesseMoyenne = (this.getVitesse(nat1) + this.getVitesse(nat2)) / 2;
		return (2 * tailleCase / (vitesseMoyenne / 3.6));
	}

	/**
	 * Renvoie la vitesse maximale pouvant être atteinte par le robot
	 * 
	 * @return vitesse maximale pouvant être atteinte par le robot
	 */
	public double getVitesseMaximale() {
		return this.vitesse;
	}

	/**
	 * Libère le robot ou le marque comme occupé
	 * 
	 * @param b true si le robot est libre, false sinon
	 */
	public void setLibre(boolean b) {
		this.libre = b;
	}

	/**
	 * Indique si le robot est libre ou pas
	 * 
	 * @return true si le robot est libre, false sinon
	 */
	public boolean isLibre() {
		return this.libre;
	}

	/**
	 * remplir le reservoir d'un robot de vol si reservoir + vol > reservoir_max,
	 * mettre cpacite a capacite_max
	 * 
	 * genere une execption si le robot est deja plein
	 * 
	 * @param vol
	 */
	public abstract void remplirReservoir(int vol);

	public abstract void setVitesse(double vitesse) throws DataFormatException;

	public int getReservoirEau() {
		return this.reservoirEau;
	}

	public void setReservoirEau(int reservoirEau) {
		this.reservoirEau = reservoirEau;
	}

}
