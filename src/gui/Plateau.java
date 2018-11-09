package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import carte.Carte;
import carte.Case;
import carte.Incendie;
import evenements.Evenement;
import evenements.Simulateur;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Simulable;
import io.DonneesSimulation;
import io.LecteurDonnees;
import robots.Robot;
import robots.Drone;
import robots.RobotAChenilles;
import robots.RobotAPattes;
import robots.RobotARoues;
import stratégies.ChefPompier;

/**
 * Représente le plateau de jeu
 * 
 * @author
 *
 */

public class Plateau implements Simulable {

	/** L'interface graphique associée */
	private GUISimulator gui;

	private DonneesSimulation donneesSimu;

	private Simulateur simulateur;

	private ChefPompier chefPompier;

	/** Constantes **/

	private int tailleCasePlateau;

	private final Color COULEUR_ERREUR = Color.PINK;

//	private final Color COULEUR_DRONE = Color.decode("#99ff99");
//	private final Color COULEUR_ROBOT_A_ROUES = Color.decode("#cc66ff");
//	private final Color COULEUR_ROBOT_A_CHENILLES = Color.decode("#33cccc");
//	private final Color COULEUR_ROBOT_A_PATTES = Color.decode("#ccc000");
//
//	private final Color COULEUR_TERRAIN_LIBRE = Color.decode("#ffffff");
//	private final Color COULEUR_HABITAT = Color.decode("#ffff99");
//	private final Color COULEUR_ROCHE = Color.decode("#996633");
//	private final Color COULEUR_FORET = Color.decode("#009933");
//	private final Color COULEUR_EAU = Color.decode("#0099ff");
//	private final Color COULEUR_BORDURE = Color.BLACK;
	
	/**Nature Cases**/
	private final String IMAGE_EAU = "images/bleu.jpeg";
	private final String IMAGE_FORET = "images/tree.jpg";
	private final String IMAGE_ROCHE = "images/rocher.png";
	private final String IMAGE_TERRAIN_LIBRE = "images/beige.jpg";
	private final String IMAGE_HABITAT = "images/habitat.jpg";
	 
	/**ROBOTS:**/
	private final String IMAGE_DRONE = "images/drone.jpg";
	private final String IMAGE_CHENILLE = "images/robot_chenille.png";
	private final String IMAGE_PATTE = "images/robot_a_pattes.png";
	private final String IMAGE_ROUE = "images/robot_roues.jpg";
			

	private final Color COULEUR_INCENDIE = Color.decode("#ff0000");

	private long pasSimulationEnSecondes;
	
	private String nomCarte;

	/**
	 * Crée un Invader et le dessine.
	 * 
	 * @param gui   l'interface graphique associée, dans laquelle se fera le dessin
	 *              et qui enverra les messages via les méthodes héritées de
	 *              Simulable.
	 * @param color la couleur de l'invader
	 */
	public Plateau(GUISimulator gui, DonneesSimulation donneesSimu, String nomCarte) {
		this.gui = gui;
		gui.setSimulable(this); // association a la gui!
		this.donneesSimu = donneesSimu;
		this.simulateur = new Simulateur();
		this.initialiserPasSimulation();
		this.initialiserTailleCasePlateau();
		this.nomCarte = nomCarte;
		
		draw();


		this.donneesSimu.initialiserGestionnairesDeplacementsRobots(this.simulateur, this.pasSimulationEnSecondes);
		this.donneesSimu.initialiserGestionnairesVidagesRobots(this.simulateur, this.pasSimulationEnSecondes);

		this.chefPompier = new ChefPompier(this.donneesSimu, this.simulateur);

	}

	/**
	 * Initialise le pas de simulation tel que le robot le plus rapide fasse un
	 * déplacement à chaque pas
	 */
	private void initialiserPasSimulation() {

		double vitesseMaxRobot = -1;
		int tailleCaseEnMetres = this.donneesSimu.getCarte().getTailleCases();

		for (Robot robot : this.donneesSimu.getRobots())
			if (robot.getVitesseMaximale() > vitesseMaxRobot)
				vitesseMaxRobot = robot.getVitesseMaximale();

		/* transformation de la vitesse en m/s avec la division par 3.6 */
		this.pasSimulationEnSecondes = (int) (tailleCaseEnMetres / (vitesseMaxRobot / 3.6));
		System.out.println("taille case en metres : "+tailleCaseEnMetres+", vitesse max robot : "+vitesseMaxRobot);
		System.out.println("Pas simulation : "+pasSimulationEnSecondes);
	}

	/**
	 * Initialise la taille d'une case afin que tout le plateau rentre
	 */
	private void initialiserTailleCasePlateau() {
		if (this.gui.getPanelHeight() < this.gui.getPanelWidth())
			this.tailleCasePlateau = this.gui.getPanelHeight() / this.donneesSimu.getCarte().getNbLignes();
		else
			this.tailleCasePlateau = this.gui.getPanelWidth() / this.donneesSimu.getCarte().getNbLignes();

	}

	@Override
	public void next() {
		// System.out.println("DICO EVT: "+ this.simulateur.getEvenements());
		// voir comment optimiser
		// supprimer les clefs à chaque fois pour ne pas tout reparcourir?

		this.chefPompier.itererPasSimu();

		Long dateCourante = this.simulateur.getDateSimulation();
		Long prochaineDate = this.simulateur.getDateSimulation() + this.pasSimulationEnSecondes;

		for (Long ddate : this.simulateur.getEvenements().keySet()) {
			if (ddate >= dateCourante) {
				if (ddate < prochaineDate) {
					ArrayList<Evenement> evtAExecuter = this.simulateur.getEvenements().get(ddate);
					for (Evenement evt : evtAExecuter) {
						evt.execute();
					}

				} // on a dépassé date courante.
				else {
					break;
				}
			}
		}

		draw();
		this.simulateur.setDateSimulation(prochaineDate);
	}

	// TODO
	@Override
	public void restart() {
		try {
			this.donneesSimu =  LecteurDonnees.creeDonnees(this.nomCarte);
		} catch (FileNotFoundException | DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.simulateur = new Simulateur();
		this.initialiserPasSimulation();
		this.initialiserTailleCasePlateau();
		
		draw();


		this.donneesSimu.initialiserGestionnairesDeplacementsRobots(this.simulateur, this.pasSimulationEnSecondes);
		this.donneesSimu.initialiserGestionnairesVidagesRobots(this.simulateur, this.pasSimulationEnSecondes);

		this.chefPompier = new ChefPompier(this.donneesSimu, this.simulateur);
	}

	/**
	 * Dessine le plateau
	 */
	private void draw() {
		gui.reset(); // clear the window

		Carte carte = this.donneesSimu.getCarte();

		this.dessinerCases(carte);
		this.dessinerRobots(this.donneesSimu.getRobots());
		this.dessinerIncendies(this.donneesSimu.getIncendies());

	}

	private void dessinerCases(Carte carte) {
		int nbLigne = carte.getNbLignes();
		int nbCol = carte.getNbColonnes();
		int x, y;
		
		ImageElement image;		

		Color color;
		Case c;

		for (int lig = 0; lig < nbLigne; lig++) {
			y = lig * this.tailleCasePlateau;

			for (int col = 0; col < nbCol; col++) {
				x = col * this.tailleCasePlateau ;
				c = carte.getCase(lig, col);

				switch (c.getNature()) {
				case EAU:
					image = new ImageElement(x, y, IMAGE_EAU, this.tailleCasePlateau,
							this.tailleCasePlateau, null);					
					break;
				case FORET:
					image = new ImageElement(x, y, IMAGE_FORET, this.tailleCasePlateau,
							this.tailleCasePlateau, null);		
					break;
				case ROCHE:
					image = new ImageElement(x, y, IMAGE_ROCHE, this.tailleCasePlateau,
							this.tailleCasePlateau, null);			
					break;
				case TERRAIN_LIBRE:
					image = new ImageElement(x, y, IMAGE_TERRAIN_LIBRE, this.tailleCasePlateau,
							this.tailleCasePlateau, null);			
					break;
				case HABITAT:
					image = new ImageElement(x, y, IMAGE_HABITAT, this.tailleCasePlateau,
							this.tailleCasePlateau, null);								break;

				default:
					image = null;
					break;
				}

				// x et y sont le centre du rectangle
				gui.addGraphicalElement(image);
			}
		}
	}

	private void dessinerRobots(Robot[] robots) {

		ImageElement image;
		int x, y;

		/*
		 * Ici, nous n'utilisons pas une méthode robot.getCouleur, car le robot n'a pas
		 * connaissance d'un plateau de jeu. On utilise de ce fait instanceof
		 */
		for (robots.Robot robot : robots) {
			x = robot.getPosition().getColonne() * this.tailleCasePlateau;
			y = robot.getPosition().getLigne() * this.tailleCasePlateau;

			image = null;

			if (robot instanceof Drone)
				image = new ImageElement(x, y, IMAGE_DRONE, this.tailleCasePlateau, this.tailleCasePlateau,
						null);
			else if (robot instanceof RobotAChenilles)
				image = new ImageElement(x, y, IMAGE_CHENILLE, this.tailleCasePlateau,
						this.tailleCasePlateau, null);
			else if (robot instanceof RobotAPattes)
				image = new ImageElement(x, y, IMAGE_PATTE, this.tailleCasePlateau,
						this.tailleCasePlateau, null);
			else if (robot instanceof RobotARoues)
				image = new ImageElement(x, y, IMAGE_ROUE, this.tailleCasePlateau, this.tailleCasePlateau,
						null);
			else
				System.err.println("robot non identifié, et donc aucun affichage disponible");

			if (image != null)
				gui.addGraphicalElement(image);

		}
	}

	private void dessinerIncendies(Incendie[] incendies) {

		int x, y;

		for (Incendie incendie : incendies) {

			x = incendie.getPosition().getColonne() * this.tailleCasePlateau + this.tailleCasePlateau / 2;
			y = incendie.getPosition().getLigne() * this.tailleCasePlateau + this.tailleCasePlateau / 2;

			gui.addGraphicalElement(
					new Oval(x, y, this.COULEUR_INCENDIE, this.COULEUR_INCENDIE, this.tailleCasePlateau / 4));
			gui.addGraphicalElement(new Text(x,y,Color.BLACK, incendie.getIntensite()+""));
		}
		
	}

	public Simulateur getSimulateur() {
		return simulateur;
	}

	public long getPasSimulation() {
		return this.pasSimulationEnSecondes;
	}
}
