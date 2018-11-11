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

/**
 * Robot pouvant se déplacer sur la carte, remplir son réservoir et éteindre des
 * incendies.
 * 
 * @author
 *
 */
public abstract class Robot {

	private Case position;

	/**
	 * Volume d'eau que contient le réservoir.
	 */
	private int reservoirEau;

	/**
	 * Volume d'eau déversée en une extinction.
	 */
	private int volumeDeverseParExtinction;

	private int tempsVidage;

	private int tempsRemplissage;

	private int volumeRemplissage;

	private double vitesse;

	/**
	 * Position du robot dans la case. Utile pour calculer les déplacements.
	 */
	private int positionDansCase;

	/**
	 * Indique si le robot est libre pour aller éteindre un incendie
	 */
	private boolean libre;

	/**
	 * Classe gérant les déplacements du robot.
	 */
	private GestionnaireDeplacementRobot gestionnaireDeplacement;

	/**
	 * Classe gérant le réservoir du robot.
	 */
	private GestionnaireReservoirRobot gestionnaireReservoir;

	/**
	 * Constructeur de robot
	 * 
	 * @param c Case où est situé le robot
	 */
	public Robot(Case c) {
		this.position = c;
		this.setLibre(true);
		this.positionDansCase = 0;
	}

	/**
	 * @param simulateur
	 * @param pasSimulation
	 * @param carte
	 */
	public void initialiserGestionnaireDeplacement(Simulateur simulateur, double pasSimulation, Carte carte) {
		this.gestionnaireDeplacement = new GestionnaireDeplacementRobot(this, simulateur, pasSimulation, carte);
	}

	/**
	 * Initialise le gestionnaire de réservoir du robot
	 * 
	 * @param simulateur    Simulateur du plateau en cours
	 * @param pasSimulation Pas de simulation du plateau
	 */
	public void initialiserGestionnaireReservoir(Simulateur simulateur, long pasSimulation) {
		this.gestionnaireReservoir = new GestionnaireReservoirRobot(this, simulateur, pasSimulation);
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
	}

	/**
	 * Indique si le robot peut se déplacer sur une certaine nature de terrain
	 * 
	 * @param Nature nature du terrain en question
	 * @return true si le robot peut se déplacer, false sinon
	 */
	public abstract boolean appartientTerrainRobot(NatureTerrain nature);

	/**
	 * Déplace un robot d'une case dans une direction donnée
	 * 
	 * @param direction Direction à emprunter
	 * @param carte     Carte sur laquelle le robot se déplace
	 * @throws RobotSorsCarte              Si le robot sort de la carte
	 * @throws ProchaineCaseMauvaiseNature Si le robot ne peut pas se déplacer sur
	 *                                     la case suivante
	 */
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

	public void deplacer(Case c, long dateDebutDeplacement) throws AucunCheminPossible {
		this.gestionnaireDeplacement.deplacerRobot(c, dateDebutDeplacement);
	}

	/**
	 * @param incendie Incendie à éteindre
	 * @param data     Données de simulation contenant l'incendie à éteindre
	 */
	public void eteindreIncendie(Incendie incendie, DonneesSimulation data, long dateDebutEvenement)
			throws AucunCheminPossible {
		long tempsFinEvenement;
		Case caseDestination = incendie.getPosition();

		tempsFinEvenement = this.gestionnaireDeplacement.deplacerRobot(caseDestination, dateDebutEvenement);
		tempsFinEvenement = this.gestionnaireReservoir.deverserEau(incendie, tempsFinEvenement, data);

		int nombreExtinctionsNecessaires = (int) Math
				.ceil(((double) incendie.getIntensite()) / this.getVolumeDeverseParExtinction());
		if (nombreExtinctionsNecessaires * this.getVolumeDeverseParExtinction() >= this.getReservoirEau()) {
			try {
				tempsFinEvenement = this.gestionnaireDeplacement.deplacerRobotVersPointDEau(incendie.getPosition(),
						tempsFinEvenement);
				this.gestionnaireReservoir.remplirReservoir(tempsFinEvenement);
			} catch (AucunCheminPossible e) {
				System.out.println("Le robot " + this + " n'a pas d'accès à un point d'eau");
			}
		}

	}

	/**
	 * Indique si la case est accessible par le robot. Attention, cela calcule le
	 * plus court chemin et peut être couteux!
	 * 
	 * @param caseDestination
	 * @return true si la case est accessible
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

	public int getVolumeDeverseParExtinction() {
		return volumeDeverseParExtinction;
	}

	public void setVolumeDeverseParExtinction(int volume) {
		this.volumeDeverseParExtinction = volume;
	}

	public int getTempsVidage() {
		return tempsVidage;
	}

	public void setTempsVidage(int temps) {
		this.tempsVidage = temps;
	}

	public int getVolumeRemplissage() {
		return this.volumeRemplissage;
	}

	public void setVolumeRemplissage(int volume) {
		this.volumeRemplissage = volume;
	}

	public int getTempsRemplissage() {
		return tempsRemplissage;
	}

	public void setTempsRemplissage(int temps) {
		this.tempsRemplissage = temps;
	}

	public double getVitesse() {
		return this.vitesse;
	}

	public abstract double getVitesse(NatureTerrain nat);

	/**
	 * Retourne le temps de parcours de deux cases adjacentes avec chacune une
	 * nature donnée.
	 * 
	 * @param nat1 Nature du terrain de la première case
	 * @param nat2 Nature du terrain de la seconde case
	 * @return le temps de parcours
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

	public void setVitesse(double vitesse) throws DataFormatException {
		if (vitesse < 0)
			throw (new DataFormatException("Vitesse négative"));
		this.vitesse = vitesse;
	}

	public int getReservoirEau() {
		return this.reservoirEau;
	}

	public void setReservoirEau(int reservoirEau) {
		this.reservoirEau = reservoirEau;
	}

	/**
	 * Vérifie si le robot a accès à un point d'eau lorsqu'il se situe sur une case
	 * 
	 * @param caseRobot case où le robot se situerait
	 * @param carte     Carte où le robot se situe
	 * @return true si le robot est bien placée depuis la caseRobot pour remplir son
	 *         réservoir
	 */
	public abstract boolean estBienPlacePourRemplissage(Case caseRobot, Carte carte);

}
